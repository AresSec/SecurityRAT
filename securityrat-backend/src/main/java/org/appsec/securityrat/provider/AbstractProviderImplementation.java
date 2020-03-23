package org.appsec.securityrat.provider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.appsec.securityrat.api.IdentifiableDtoProvider;
import org.appsec.securityrat.api.dto.IdentifiableDto;
import org.appsec.securityrat.api.exception.ApiException;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractProviderImplementation<
            TId,
            TDto extends IdentifiableDto<TId>,
            TEntity>
        extends AbstractIdentifiableDtoMapper<TId, TDto, TEntity>
        implements IdentifiableDtoProvider<TId, TDto> {
    
    /**
     * Returns the repository that will be used for loading and storing
     * persistent versions of <code>TEntity</code>.
     * 
     * @return The persistent repository.
     */
    protected abstract JpaRepository<TEntity, TId> getRepository();
    
    /**
     * Returns the repository that will be used for saving and querying entities
     * by using Elasticsearch.
     * 
     * @return The Elasticsearch repository.
     */
    protected abstract ElasticsearchRepository<TEntity, TId>
        getSearchRepository();
    
    /**
     * Returns an instance of <code>TEntity</code> that is resolved by the
     * identifier that the specified <code>dto</code> provides.
     * 
     * @param dto The data transfer object whose identifier will be used for
     *            resolving the returned instance of <code>TEntity</code>.
     * 
     * @return A container object that contains the matching instance of
     *         <code>TEntity</code>, or <code>null</code>, if either the passed
     *         <code>dto</code> is <code>null</code> or there is no matching
     *         <code>TEntity</code> instance.
     */
    public Optional<TEntity> findByDto(TDto dto) {
        if (dto == null) {
            return Optional.empty();
        }
        
        if (dto.getId().isEmpty()) {
            throw new IllegalArgumentException("dto does not provide an id!");
        }
        
        return this.getRepository().findById(dto.getId().get());
    }
    
    public List<TEntity> findListByDto(List<TDto> dtos) {
        List<TId> dtoIds = dtos.stream()
                .map(dto -> dto.getId()
                        .orElseThrow(() -> new IllegalArgumentException(
                                "dto does not provide an id!")))
                .collect(Collectors.toList());
        
        List<TEntity> entities = this.getRepository().findAllById(dtoIds);
        
        if (entities.size() != dtos.size()) {
            throw new IllegalArgumentException("Cannot resolve all DTOs!");
        }
        
        return entities;
    }
    
    public Set<TEntity> findSetByDto(Set<TDto> dtos) {
        return new HashSet<>(this.findListByDto(new ArrayList<>(dtos)));
    }
    
    @Override
    @Transactional
    public List<TDto> findAll() {
        return this.getRepository()
                .findAll()
                .stream()
                .map(entity -> this.createDtoChecked(entity))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<TDto> findById(TId id) {
        return this.getRepository()
                .findById(id)
                .map(entity -> this.createDto(entity));
    }

    @Override
    @Transactional
    public TDto save(TDto dto) throws ApiException {
        TEntity entity = null;
        boolean created = false;
        
        if (dto.getId().isPresent()) {
            entity = this.getRepository()
                    .findById(dto.getId().get())
                    .orElse(null);
            
            created = true;
        }
        
        entity = this.createOrUpdateEntityChecked(dto, entity);
        
        // TODO [luis.felger@bosch.com]: Catch saving failures.
        
        if (created) {
            this.doCreate(entity);
        } else {
            this.doUpdate(entity);
        }
        
        return this.createDtoChecked(entity);
    }

    @Override
    @Transactional
    public boolean delete(TId id) throws ApiException {
        Optional<TEntity> nullableEntity = this.getRepository().findById(id);
        
        if (nullableEntity.isEmpty()) {
            return false;
        }
        
        TEntity entity = nullableEntity.get();
        
        // TODO [luis.felger@bosch.com]: Catch deletion failures.
        
        this.doDelete(entity);
        
        return true;
    }

    @Override
    @Transactional
    public List<TDto> search(String query) {
        Stream<TEntity> stream = StreamSupport.stream(
                this.getSearchRepository().search(
                        QueryBuilders.queryStringQuery(query)).spliterator(),
                false);
        
        return stream.map(entity -> this.createDto(entity))
                .collect(Collectors.toList());
    }
    
    protected void onCreated(TEntity entity) {
        // Child classes may override this method to handle entity creations.
    }
    
    protected void onDeleted(TEntity entity) {
        // Child classes may override this method to handle entity deletions.
    }
    
    protected final void doCreate(TEntity entity) throws ApiException {
        this.getRepository().save(entity);
        this.getSearchRepository().save(entity);
        this.onCreated(entity);
    }
    
    protected final void doUpdate(TEntity entity) throws ApiException {
        this.getRepository().save(entity);
        this.getSearchRepository().save(entity);
        
        // NOTE: Without deep modifications of the overall setup, we cannot
        //       provide a good implementation of an "onUpdate" method that
        //       tells the implementer about the fields that have been updated.
        //
        //       That's because we would need to detach the entities from their
        //       Hibernate session before updating them. This may have a big
        //       impact on the application's performance.
    }
    
    protected final void doDelete(TEntity entity) throws ApiException {
        this.getRepository().delete(entity);
        this.getSearchRepository().delete(entity);
        this.onDeleted(entity);
    }
}
