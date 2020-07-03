package org.appsec.securityrat.provider.frontend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.metamodel.EntityType;
import org.appsec.securityrat.domain.Authority;
import org.appsec.securityrat.domain.ConfigConstant;
import org.appsec.securityrat.domain.PersistentAuditEvent;
import org.appsec.securityrat.domain.PersistentToken;
import org.appsec.securityrat.domain.User;
import org.appsec.securityrat.web.dto.importer.FrontendAttributeDto;
import org.appsec.securityrat.web.dto.importer.FrontendAttributeValueDto;
import org.appsec.securityrat.web.dto.importer.FrontendAttributeValueType;
import org.appsec.securityrat.web.dto.importer.FrontendObjectDto;
import org.appsec.securityrat.web.dto.importer.FrontendReplaceRule;
import org.appsec.securityrat.web.dto.importer.FrontendTypeDto;
import org.appsec.securityrat.web.dto.importer.FrontendTypeReferenceDto;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImporterProviderImpl implements ImporterProvider {
    private static final Set<Class<?>> PRIMITIVE_CLASSES =
            new HashSet<>(Arrays.asList(
                    Byte.class, byte.class, Short.class, short.class,
                    Integer.class, int.class, Long.class, long.class,
                    Float.class, float.class, Double.class, double.class,
                    Character.class, char.class, String.class, Boolean.class,
                    boolean.class));
    
    private static final Set<Class<?>> IGNORED_CLASSES =
            new HashSet<>(Arrays.asList(
                    Authority.class, ConfigConstant.class,
                    PersistentAuditEvent.class, PersistentToken.class,
                    User.class));
    
    private static Set<FrontendTypeDto> fromClasses(Set<Class<?>> classes) {
        Set<FrontendTypeDto> result = new HashSet<>();
        
        for (Class<?> cls : classes) {
            // We only include those attributes that match the following rules:
            //
            //  - Not annotated with @JsonIgnore
            //  - Type needs to be either primitive (including strings) or
            //    another entity type
            
            Field[] declaredFields = cls.getDeclaredFields();
            Set<FrontendAttributeDto> attributes = new HashSet<>();
            
            for (Field field : declaredFields) {
                // Preconditions
                
                if (field.isAnnotationPresent(JsonIgnore.class)) {
                    continue;
                }
                
                Class<?> type = field.getType();
                
                if (!ImporterProviderImpl.PRIMITIVE_CLASSES.contains(type) &&
                        !classes.contains(type)) {
                    continue;
                }
                
                // Determining the type reference
                
                FrontendTypeReferenceDto typeRef =
                        new FrontendTypeReferenceDto();
                
                if (ImporterProviderImpl.PRIMITIVE_CLASSES.contains(type)) {
                    typeRef.setReference(false);
                    typeRef.setReferenceIdentifier(null);
                } else {
                    typeRef.setReference(true);
                    typeRef.setReferenceIdentifier(type.getName());
                }

                // Building the attribute instance
                
                FrontendAttributeDto attr = new FrontendAttributeDto();
                attr.setIdentifier(field.getName());
                attr.setDisplayName(field.getName());
                attr.setType(typeRef);
                
                attributes.add(attr);
            }
            
            // Building the type instance
            
            FrontendTypeDto typeDto = new FrontendTypeDto();
            typeDto.setIdentifier(cls.getName());
            typeDto.setDisplayName(cls.getSimpleName());
            typeDto.setAttributes(attributes);
            
            result.add(typeDto);
        }
        
        return result;
    }
    
    private static Object fromFrontendObject(
            FrontendObjectDto dto,
            Class<?> entityClass,
            Map<String, Object> objectPool,
            Object obj) {
        // Creating a new instance, if we do not modify an existing instance.
        
        if (obj == null) {
            try {
                obj = entityClass.getConstructor().newInstance();
            } catch (NoSuchMethodException |
                    InstantiationException |
                    IllegalAccessException |
                    InvocationTargetException ex) {
                throw new IllegalArgumentException(ex);
            }
        }
        
        // Inserting the attribute values
        
        for (FrontendAttributeValueDto attr : dto.getAttributes()) {
            // Resolving the field access
            
            Field field;
            
            try {
                field = entityClass.getDeclaredField(
                        attr.getAttributeIdentifier());
            } catch (NoSuchFieldException ex) {
                throw new IllegalArgumentException(ex);
            }
            
            field.setAccessible(true);
            
            // If the attribute contains a reference value, we need to resolve
            // that one and insert it.
            
            if (attr.getValueType() == FrontendAttributeValueType.PoolReference) { // TODO
                String refId = attr.getValue();
                
                if (refId == null) {
                    continue;
                }
                
                Object refObj = objectPool.get(refId);
                
                if (refObj == null) {
                    throw new IllegalArgumentException("Invalid reference!");
                }
                
                try {
                    field.set(obj, refObj);
                } catch (IllegalAccessException ex) {
                    throw new IllegalArgumentException(ex);
                }
                
                continue;
            }
            
            // Value types
            //
            // NOTE: Also if "null" is not valid for a value type, we ignore it
            //       silently to make things more robust.
            
            if (attr.getValue() == null) {
                continue;
            }
            
            try {
                field.set(obj, PrimitiveHelper.parsePrimitive(
                        attr.getValue(),
                        field.getType()));
            } catch (IllegalAccessException ex) {
                throw new IllegalArgumentException(ex);
            }
        }
        
        return obj;
    }
    
    private static FrontendObjectDto toFrontendObject(Object entityInstance) {
        // Iterating through the entity's attributes and copying those who are
        // public (have also a look at the fromClasses(Set<Class<?>>) method
        // above)
        
        Class<?> entityClass = entityInstance.getClass();
        Set<FrontendAttributeValueDto> attributes = new HashSet<>();
        Object identifier = null;
        
        for (Field field : entityClass.getDeclaredFields()) {
            // TODO [luis.felger@bosch.com]: Merge this and fix security issue
            //                               in fromFrontendObject method
            
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                
                try {
                    identifier = field.get(entityInstance);
                } catch (IllegalAccessException ex) {
                    // This should not occur, otherwise we cannot do anything
                    // besides throwing an exception.
                    
                    throw new IllegalStateException(ex);
                }
            }
            
            if (field.isAnnotationPresent(JsonIgnore.class)) {
                continue;
            }
            
            Class<?> type = field.getType();
            
            if (!ImporterProviderImpl.PRIMITIVE_CLASSES.contains(type)) {
                // TODO [luis.felger@bosch.com]: Also include other entity type
                //                               attributes
                continue;
            }
            
            // Creating the attribute instance
            
            field.setAccessible(true);
            
            FrontendAttributeValueDto attrDto = new FrontendAttributeValueDto();
            attrDto.setAttributeIdentifier(field.getName());
            
            try {
                attrDto.setValue(Objects.toString(field.get(entityInstance)));
            } catch (IllegalAccessException ex) {
                // This should not occur, otherwise we cannot do anything
                // besides throwing an exception.
                
                throw new IllegalStateException(ex);
            }
            
            attrDto.setValueType(FrontendAttributeValueType.Value);
            attrDto.setKeyComponent(false);
            
            attributes.add(attrDto);
        }
        
        if (identifier == null) {
            throw new IllegalStateException(
                    "Entity does not provide identifier");
        }
        
        // Finishing the DTO
        
        FrontendObjectDto dto = new FrontendObjectDto();
        
        dto.setIdentifier("s/" + entityClass.getName() + "/" + identifier);
        dto.setTypeIdentifier(entityClass.getName());
        dto.setAttributes(attributes);
        dto.setReplaceRule(FrontendReplaceRule.Ignore);
        
        return dto;
    }
    
    private static Object findDuplicate(
            FrontendObjectDto dto,
            Collection<Object> duplicatePool,
            Class<?> entityClass,
            Map<String, Object> objectPool) {
        // Since resolving fields with Java's reflection API is considered to be
        // slow, we do this only once at the beginning for all key component
        // attributes.
        // (The assigned value is the expected value.)
        
        Map<Field, Object> fields = new HashMap<>();
        
        for (FrontendAttributeValueDto attr : dto.getAttributes()) {
            if (!attr.isKeyComponent()) {
                continue;
            }
            
            Field field;
            
            try {
                field = entityClass.getDeclaredField(
                        attr.getAttributeIdentifier());
            } catch (NoSuchFieldException ex) {
                throw new IllegalArgumentException(ex);
            }
            
            field.setAccessible(true);
            
            // Resolving the reference or the primitive value
            
            String value = attr.getValue();
            Object fieldValue = null;
            
            if (attr.getValueType() == FrontendAttributeValueType.PoolReference && value != null) { // TODO
                fieldValue = objectPool.get(value);
                
                if (fieldValue == null) {
                    throw new IllegalArgumentException("Invalid reference!");
                }
            } else if (attr.getValueType() == FrontendAttributeValueType.Value && value != null) {
                fieldValue = PrimitiveHelper.parsePrimitive(
                        value,
                        field.getType());
            }
            
            // Putting the entry together
            
            fields.put(field, fieldValue);
        }
        
        // If there is no key component, we cannot perform a duplicate check
        
        if (fields.isEmpty()) {
            return null;
        }
        
        // Performing the lookup for duplicates
        
        return duplicatePool.stream()
                .filter(obj -> fields.entrySet()
                        .stream()
                        .allMatch(entry -> {
                            try {
                                return Objects.equals(
                                    entry.getKey().get(obj),
                                    entry.getValue());
                            } catch (IllegalAccessException ex) {
                                throw new IllegalArgumentException(ex);
                            }
                        }))
                .findAny()
                .orElse(null);
    }
    
    @Inject
    private EntityManagerFactory entityManagerFactory;
    
    @Inject
    private ApplicationContext appContext;
    private List<FrontendTypeDto> availableTypes;
    
    @Override
    public Set<FrontendTypeDto> getAvailableTypes() {
        return new HashSet<>(this.getAvailableTypesSorted());
    }

    @Override
    @Transactional
    public boolean applyObjects(Set<FrontendObjectDto> objects) {
        List<String> availableTypes = this.getAvailableTypesSorted()
                .stream()
                .map(e -> e.getIdentifier())
                .collect(Collectors.toList());
        
        // Separating the objects by their entity classes and discarding those
        // which are not part of the availableTypes set
        
        Map<String, Set<FrontendObjectDto>> objectsByEntity = new HashMap<>();
        
        for (String typeName : availableTypes) {
            Set<FrontendObjectDto> typeObjects =
                    objects.stream()
                            .filter(obj -> Objects.equals(
                                    typeName,
                                    obj.getTypeIdentifier()))
                            .collect(Collectors.toSet());
            
            if (typeObjects.isEmpty()) {
                continue;
            }
            
            objectsByEntity.put(typeName, typeObjects);
        }
        
        // The following map will store all instances of types that have been
        // created, linked with their instance identifier that may be referenced
        // by another instance.
        
        Map<String, Object> objectPool = new HashMap<>();
        
        // Since availableTypes is guaranteed to be in the right dependency
        // order, we simply iterate through the availableTypes and store the
        // new/modified objects in the database.
        //
        // NOTE: The reason that we do this in another loop is the readability
        //       of the code.
        
        for (String typeIdentifier : availableTypes) {
            // Skip this type, if there is no objectsByEntity entry for it
            
            if (!objectsByEntity.containsKey(typeIdentifier)) {
                continue;
            }
            
            // Resolving the entity type by its fully-qualified class name
            
            Class<?> entityClass;
            
            try {
                entityClass = Class.forName(typeIdentifier);
            } catch (ClassNotFoundException ex) {
                return false;
            }
            
            // We need the JPA repository to access the persistent storage of
            // the current entity type.
            
            JpaRepository repo = this.getRepo(entityClass);
            
            if (repo == null) {
                // No repository?!
                return false;
            }
            
            // For the duplicate check, we need to get all entities that have
            // been stored previously.
            
            List<Object> existingEntities = repo.findAll();
            
            // We need to create valid instances of the entityClass for each
            // FrontendObjectDto of this FrontendTypeDto.
            //
            // At the same time we perform the duplicate check.
            
            Map<String, Object> newEntities = new HashMap<>();
            
            for (FrontendObjectDto objDto :
                    objectsByEntity.get(typeIdentifier)) {
                Object duplicate = null;
                
                // Depending on the settings, we perform a duplicate check
                
                if (objDto.getReplaceRule() != FrontendReplaceRule.Duplicate) {
                    // First, we look in the objectPool
                    
                    duplicate = ImporterProviderImpl.findDuplicate(
                            objDto,
                            existingEntities,
                            entityClass,
                            objectPool);
                    
                    // If there is no duplicate entry in the objectPool, maybe
                    // there is one in the newEntities map.
                    //
                    // This may be the case, if the same object has been
                    // submitted multiple times with different instance
                    // identifiers.
                    
                    if (duplicate == null) {
                        duplicate = ImporterProviderImpl.findDuplicate(
                                objDto,
                                newEntities.values(),
                                entityClass,
                                objectPool);
                    }
                }
                
                // If we're preserving existing duplicates, we do not need to
                // construct a new instance, if the object exists already.
                
                if (objDto.getReplaceRule() == FrontendReplaceRule.Ignore &&
                        duplicate != null) {
                    objectPool.put(objDto.getIdentifier(), duplicate);
                    continue;
                }
                
                Object instance =
                        ImporterProviderImpl.fromFrontendObject(
                                objDto,
                                entityClass,
                                objectPool,
                                duplicate);
                
                newEntities.put(objDto.getIdentifier(), instance);
            }
            
            // Storing the result in the persistent storage and copying the new
            // entities to the pool.
            
            repo.saveAll(newEntities.values());
            objectPool.putAll(newEntities);
        }
        
        return true;
    }

    @Override
    @Transactional
    public Set<FrontendObjectDto> getExistingInstances(String typeIdentifier) {
        // Check, if the typeIdentifier is part of the availableTypes. If not,
        // it's not suported to access the instances of that type via this API.
        
        boolean supported = this.getAvailableTypesSorted()
                .stream()
                .anyMatch(type -> Objects.equals(
                        type.getIdentifier(),
                        typeIdentifier));
        
        if (!supported) {
            return null;
        }
        
        // Resolving the entity class via reflection
        
        Class<?> entityClass;
        
        try {
            entityClass = Class.forName(typeIdentifier);
        } catch (ClassNotFoundException ex) {
            return null;
        }
        
        // Resolving the entity's repository
        
        JpaRepository<?, ?> repo = this.getRepo(entityClass);
        
        if (repo == null) {
            return null;
        }
        
        // Retrieving all instances of the entityClass and mapping them to their
        // corresponding FrontedObject instance
        
        return repo.findAll()
                .stream()
                .map(ImporterProviderImpl::toFrontendObject)
                .collect(Collectors.toSet());
    }
    
    private List<FrontendTypeDto> getAvailableTypesSorted() {
        // If the availableTypes collection has been created already, we can
        // just return the cached instance.
        
        if (this.availableTypes != null) {
            return this.availableTypes;
        }
        
        // Otherwise, we need to get the entity classes using Hibernate.
        
        Set<Class<?>> entityClasses =
                this.entityManagerFactory.getMetamodel()
                        .getEntities()
                        .stream()
                        .map(EntityType::getJavaType)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());
        
        // Removing those classes that should not be mappable
        
        entityClasses.removeAll(ImporterProviderImpl.IGNORED_CLASSES);
        
        // Creating the entity's DTO representations and sorting them by their
        // references (smaller index -> less references to other entity classes,
        // greater index -> more references to other entity classes).
        //
        // NOTE: Circular references are not supported and will be ignored.
        
        Set<FrontendTypeDto> unorderedTypes =
                ImporterProviderImpl.fromClasses(entityClasses);
        
        this.availableTypes = new ArrayList<>();
        
        while (!unorderedTypes.isEmpty()) {
            for (FrontendTypeDto type : unorderedTypes) {
                boolean dependenciesSatisfied =
                        type.getAttributes()
                                .stream()
                                .map(FrontendAttributeDto::getType)
                                .filter(FrontendTypeReferenceDto::isReference)
                                .allMatch(t -> this.availableTypes.stream()
                                        .anyMatch(u -> Objects.equals(
                                                t.getReferenceIdentifier(),
                                                u.getIdentifier())));
                
                if (!dependenciesSatisfied) {
                    continue;
                }
                
                this.availableTypes.add(type);
            }
            
            if (!unorderedTypes.removeAll(this.availableTypes)) {
                // Since no entry of the unorderedTypes set has been moved to
                // the availableTypes list, there must be a circular reference.
                
                break;
            }
        }
        
        // Returning the newly created List as HashSet
        
        return this.availableTypes;
    }
    
    private JpaRepository getRepo(Class<?> entityClass) {
        // Before we are able to resolve the repository from the Spring
        // application context, we determine the type of the entity's
        // identifier.
        
        Class<?> idClass = Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .map(Field::getType)
                .findAny()
                .orElse(null);
        
        if (idClass == null) {
            return null;
        }
        
        return (JpaRepository) this.appContext.getBeanProvider(
                ResolvableType.forClassWithGenerics(
                        JpaRepository.class, entityClass, idClass)).getObject();
    }
}
