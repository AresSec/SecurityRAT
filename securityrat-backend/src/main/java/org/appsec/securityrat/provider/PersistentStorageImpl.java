package org.appsec.securityrat.provider;

import com.google.common.base.Preconditions;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.inject.Inject;
import org.appsec.securityrat.api.dto.SimpleDto;
import org.appsec.securityrat.api.dto.rest.ConfigConstantDto;
import org.appsec.securityrat.api.dto.rest.ReqCategoryDto;
import org.appsec.securityrat.api.provider.PersistentStorage;
import org.appsec.securityrat.domain.ConfigConstant;
import org.appsec.securityrat.domain.ReqCategory;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.appsec.securityrat.provider.mapper.IdentifiableMapper;

@Service
public class PersistentStorageImpl implements PersistentStorage {
    private static final ConcurrentMap<Class<? extends SimpleDto<?>>, Class<?>> MAPPINGS;
    private static final ConcurrentMap<Class<? extends SimpleDto<?>>, Class<?>> IDENTIFIER_CACHE;
    
    static {
        MAPPINGS = new ConcurrentHashMap<>();
        MAPPINGS.put(ReqCategoryDto.class, ReqCategory.class);
        MAPPINGS.put(ConfigConstantDto.class, ConfigConstant.class);
        
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
    private final ConcurrentMap<Class<?>, ElasticsearchRepository<?, ?>> searchRepositoryCache;
    private final ConcurrentMap<Class<?>, IdentifiableMapper<?, ?, ?>> mapperCache;
    
    @Inject
    private ApplicationContext context;
    
    public PersistentStorageImpl() {
        this.repositoryCache = new ConcurrentHashMap<>();
        this.searchRepositoryCache = new ConcurrentHashMap<>();
        this.mapperCache = new ConcurrentHashMap<>();
    }
    
    @Override
    public <TId, TSimpleDto extends SimpleDto<TId>> boolean create(
            TSimpleDto dto) {
        Preconditions.checkNotNull(dto);
        
        if (dto.getId() != null) {
            return false;
        }
        
        return this.prepare(dto.getClass(), (repo, mapper) -> {
            if (repo.existsById(dto.getId())) {
                return false;
            }
            
            repo.save(mapper.toEntity(dto));
            return true;
        });
    }

    @Override
    public <TId, TSimpleDto extends SimpleDto<TId>> boolean update(
            TSimpleDto dto) {
        Preconditions.checkNotNull(dto);
        
        if (dto.getId() == null) {
            return false;
        }
        
        return this.prepare(dto.getClass(), (repo, mapper) -> {
            if (!repo.existsById(dto.getId())) {
                return false;
            }
            
            repo.save(mapper.toEntity(dto));
            return true;
        });
    }

    @Override
    public <TId, TSimpleDto extends SimpleDto<TId>> boolean delete(
            TId id,
            Class<TSimpleDto> simpleDtoClass) {
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(simpleDtoClass);
        
        return this.prepare(simpleDtoClass, (repo, mapper) -> {
            if (!repo.existsById(id)) {
                return false;
            }
            
            repo.deleteById(id);
            return true;
        });
    }

    @Override
    public <TId, TSimpleDto extends SimpleDto<TId>> TSimpleDto find(
            TId id,
            Class<TSimpleDto> simpleDtoClass) {
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(simpleDtoClass);
        
        return this.prepare(simpleDtoClass, (repo, mapper) -> {
            Optional<Object> optionalDto = repo.findById(id);
            
            if (optionalDto.isEmpty()) {
                return null;
            }
            
            return (TSimpleDto) mapper.toDto(optionalDto.get());
        });
    }

    @Override
    public <TId, TSimpleDto extends SimpleDto<TId>> Set<TSimpleDto> findAll(
            Class<TSimpleDto> simpleDtoClass) {
        Preconditions.checkNotNull(simpleDtoClass);
        
        return this.prepare(
                simpleDtoClass,
                (repo, mapper) -> repo.findAll()
                        .stream()
                        .map(e -> (TSimpleDto) mapper.toDto(e))
                        .collect(Collectors.toSet()));
    }

    @Override
    public <TId, TSimpleDto extends SimpleDto<TId>> List<TSimpleDto> search(
            String query,
            Class<TSimpleDto> simpleDtoClass) {
        Preconditions.checkNotNull(query);
        Preconditions.checkNotNull(simpleDtoClass);
        
        // NOTE: Due to the fact that this is the only method that requires the
        //       ElasticsearchRepository instance, there is no dedicated prepare
        //       method.
        
        return this.doSearch(query, simpleDtoClass);
    }
    
    private <TId, TEntity, TSimpleDto extends SimpleDto<TId>> List<TSimpleDto> doSearch(
            String query,
            Class<TSimpleDto> simpleDtoClass) {
        // NOTE: The separation of the 'search' and the 'doSearch' method is
        //       required to satisfy the needs of Java's generics. (Otherwise
        //       there is no good solution for adding the 'TEntity' generic type
        //       parameter.)
        
        Class<TEntity> entityClass =
                (Class<TEntity>) MAPPINGS.get(simpleDtoClass);
        
        if (entityClass == null) {
            throw new IllegalArgumentException("Unknown DTO type");
        }
        
        Class<TId> identifierClass = getDtoIdentifier(simpleDtoClass);
        
        // Resolving the required implementations
        
        ElasticsearchRepository<TEntity, TId> repo =
                this.getSearchRepository(entityClass, identifierClass);
        
        IdentifiableMapper<TId, TEntity, TSimpleDto> mapper =
                this.getMapper(identifierClass, entityClass, simpleDtoClass);
        
        // Performing the search
        
        return StreamSupport.stream(
                repo.search(QueryBuilders.queryStringQuery(query))
                        .spliterator(),
                false)
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
    
    private <TResult, TId, TEntity, TSimpleDto extends SimpleDto<TId>> TResult prepare(
            Class<TSimpleDto> dtoClass,
            BiFunction<JpaRepository<TEntity, TId>, IdentifiableMapper, TResult> callback) {
        // NOTE: The IdentifiableMapper<TId, TEntity, TDto> argument of the
        //       callback parameter is specified without generic type
        //       parameters.
        //       => This simplifies constructing the callback method a lot.
        
        Class<TEntity> entityClass = (Class<TEntity>) MAPPINGS.get(dtoClass);
        
        if (entityClass == null) {
            throw new IllegalArgumentException("Unknown DTO type");
        }
        
        // For retrieving the JpaRepository, we need the get the identifier type
        // of the DTO (and the entity)
        
        Class<TId> identifierClass = getDtoIdentifier(dtoClass);
        
        // Retrieving the JpaRepository and the SimpleMapper
        
        JpaRepository<TEntity, TId> repo =
                this.getRepository(entityClass, identifierClass);
        
        IdentifiableMapper<TId, TEntity, TSimpleDto> mapper =
                this.getMapper(identifierClass, entityClass, dtoClass);
        
        return callback.apply(repo, mapper);
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
    
    private <TEntity, TId> ElasticsearchRepository<TEntity, TId> getSearchRepository(
            Class<TEntity> entityClass,
            Class<TId> idClass) {
        
        ElasticsearchRepository<TEntity, TId> searchRepository =
                (ElasticsearchRepository<TEntity, TId>) this.searchRepositoryCache.get(
                        entityClass);
        
        if (searchRepository != null) {
            return searchRepository;
        }
        
        searchRepository = (ElasticsearchRepository<TEntity, TId>) this.context.getBeanProvider(
                ResolvableType.forClassWithGenerics(
                        ElasticsearchRepository.class,
                        entityClass,
                        idClass))
                .getObject();
        
        this.searchRepositoryCache.put(entityClass, searchRepository);
        return searchRepository;
    }
    
    private <TId, TEntity, TDto extends SimpleDto<TId>> IdentifiableMapper<TId, TEntity, TDto> getMapper(
            Class<TId> identifierClass,
            Class<TEntity> entityClass,
            Class<TDto> dtoClass) {
        
        IdentifiableMapper<TId, TEntity, TDto> mapper =
                (IdentifiableMapper<TId, TEntity, TDto>) this.mapperCache.get(entityClass);
        
        if (mapper != null) {
            return mapper;
        }
        
        mapper = (IdentifiableMapper<TId, TEntity, TDto>) this.context.getBeanProvider(ResolvableType.forClassWithGenerics(IdentifiableMapper.class,
                        identifierClass,
                        entityClass,
                        dtoClass))
                .getObject();
        
        this.mapperCache.put(entityClass, mapper);
        return mapper;
    }
}
