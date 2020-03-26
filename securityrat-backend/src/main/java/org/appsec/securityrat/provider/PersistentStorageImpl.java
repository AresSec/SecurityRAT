package org.appsec.securityrat.provider;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.appsec.securityrat.api.dto.SimpleDto;
import org.appsec.securityrat.api.dto.rest.ReqCategoryDto;
import org.appsec.securityrat.api.provider.PersistentStorage;
import org.appsec.securityrat.domain.ReqCategory;
import org.appsec.securityrat.provider.mapper.SimpleMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PersistentStorageImpl implements PersistentStorage {
    private static final ConcurrentMap<Class<? extends SimpleDto<?>>, Class<?>> MAPPINGS;
    private static final ConcurrentMap<Class<? extends SimpleDto<?>>, Class<?>> IDENTIFIER_CACHE;
    
    static {
        MAPPINGS = new ConcurrentHashMap<>();
        MAPPINGS.put(ReqCategoryDto.class, ReqCategory.class);
        
        IDENTIFIER_CACHE = new ConcurrentHashMap<>();
    }
    
    private static <TId, TDto extends SimpleDto<TId>> Class<TId> getDtoIdentifier(
            Class<TDto> dtoClass) {
        Class<TId> identifierClass =
                (Class<TId>) IDENTIFIER_CACHE.get(dtoClass);
        
        if (identifierClass != null) {
            return identifierClass;
        }
        
        try {
            identifierClass = dtoClass.getConstructor()
                    .newInstance()
                    .getIdentifierClass();
        } catch (NoSuchMethodException |
                InstantiationException |
                IllegalAccessException |
                InvocationTargetException ex) {
            throw new IllegalStateException(
                    "Cannot instantiate DTO class with default constructor",
                    ex);
        }
        
        IDENTIFIER_CACHE.put(dtoClass, identifierClass);
        return identifierClass;
    }
    
    private final ConcurrentMap<Class<?>, JpaRepository<?, ?>> repositoryCache;
    private final ConcurrentMap<Class<?>, SimpleMapper<?, ?>> mapperCache;
    
    @Inject
    private ApplicationContext context;
    
    public PersistentStorageImpl() {
        this.repositoryCache = new ConcurrentHashMap<>();
        this.mapperCache = new ConcurrentHashMap<>();
    }
    
    @Override
    public <TId, TSimpleDto extends SimpleDto<TId>> boolean create(TSimpleDto dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <TId, TSimpleDto extends SimpleDto<TId>> boolean update(TSimpleDto dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <TId, TSimpleDto extends SimpleDto<TId>> boolean delete(TId id, Class<TSimpleDto> simpleDtoClass) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <TId, TSimpleDto extends SimpleDto<TId>> TSimpleDto find(TId id, Class<TSimpleDto> simpleDtoClass) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <TId, TSimpleDto extends SimpleDto<TId>> Set<TSimpleDto> findAll(
            Class<TSimpleDto> simpleDtoClass) {
        return this.doFindAll(simpleDtoClass);
    }
    
    public <TId, TEntity, TSimpleDto extends SimpleDto<TId>> Set<TSimpleDto> doFindAll(
            Class<TSimpleDto> simpleDtoClass) {
        Class<TEntity> entityClass = (Class<TEntity>) MAPPINGS.get(simpleDtoClass);
        
        if (entityClass == null) {
            throw new IllegalArgumentException("Unknown DTO type");
        }
        
        // For retrieving the JpaRepository, we need the get the identifier type
        // of the DTO (and the entity)
        
        Class<TId> identifierClass = getDtoIdentifier(simpleDtoClass);
        
        // Retrieving the JpaRepository and the SimpleMapper
        
        JpaRepository<TEntity, TId> repo =
                this.getRepository(entityClass, identifierClass);
        
        SimpleMapper<TEntity, TSimpleDto> mapper =
                this.getMapper(entityClass, simpleDtoClass);
        
        // Querying and mapping the result
        
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public <TId, TSimpleDto extends SimpleDto<TId>> List<TSimpleDto> search(String query, Class<TSimpleDto> simpleDtoClass) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private <TEntity, TId> JpaRepository<TEntity, TId> getRepository(
            Class<TEntity> entityClass,
            Class<TId> idClass) {
        
        JpaRepository<TEntity, TId> repository =
                (JpaRepository<TEntity, TId>) this.repositoryCache.get(
                        entityClass);
        
        if (repository != null) {
            return repository;
        }
        
        repository =
                (JpaRepository<TEntity, TId>) this.context.getBeanProvider(
                        ResolvableType.forClassWithGenerics(
                                JpaRepository.class,
                                entityClass,
                                idClass))
                        .getObject();
        
        this.repositoryCache.put(entityClass, repository);
        return repository;
    }
    
    private <TId, TEntity, TDto extends SimpleDto<TId>> SimpleMapper<TEntity, TDto> getMapper(
            Class<TEntity> entityClass,
            Class<TDto> dtoClass) {
        
        SimpleMapper<TEntity, TDto> mapper =
                (SimpleMapper<TEntity, TDto>) this.mapperCache.get(entityClass);
        
        if (mapper != null) {
            return mapper;
        }
        
        mapper = (SimpleMapper<TEntity, TDto>) this.context.getBeanProvider(
                ResolvableType.forClassWithGenerics(
                        SimpleMapper.class,
                        entityClass,
                        dtoClass)
                        .resolve())
                .getObject();
        
        this.mapperCache.put(entityClass, mapper);
        return mapper;
    }
}
