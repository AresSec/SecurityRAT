package org.appsec.securityrat.provider;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.appsec.securityrat.api.IdentifiableDtoProvider;
import org.appsec.securityrat.api.dto.IdentifiableDto;
import org.appsec.securityrat.mapper.AbstractMapperBase;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractProviderImplementation<
        TIdType,
        TDto extends IdentifiableDto<TIdType>,
        TEntity>
        implements IdentifiableDtoProvider<TIdType, TDto> {
    
    protected abstract JpaRepository<TEntity, TIdType> getRepo();
    protected abstract ElasticsearchRepository<TEntity, TIdType>
        getSearchRepo();
        
    protected abstract AbstractMapperBase<TEntity, TDto> getMapper();
    
    // NOTE: All methods that map entities to DTOs or the other way around need
    //       to be marked with the @Transactional annotation. Otherwise, the
    //       mapper will run into exceptions when attempting to load data from
    //       references/nested objects.

    @Override
    @Transactional
    public List<TDto> findAll() {
        return this.getMapper().toDtoList(this.getRepo().findAll());
    }

    @Override
    @Transactional
    public Optional<TDto> findById(TIdType id) {
        return this.getRepo()
                .findById(id)
                .map(entity -> this.getMapper().toDto(entity));
    }

    @Override
    @Transactional
    public TDto save(TDto dto) {
        TEntity entity = this.getMapper().toEntity(dto);
        
        TEntity resultEntity = this.getRepo().save(entity);
        this.getSearchRepo().save(entity);
        
        return this.getMapper().toDto(resultEntity);
    }

    @Override
    @Transactional
    public boolean delete(TIdType id) {
        return this.getRepo()
                .findById(id)
                .map(entity -> {
                    this.getRepo().deleteById(id);
                    this.getSearchRepo().deleteById(id);
                    return true;
                })
                .orElse(Boolean.FALSE);
    }

    @Override
    @Transactional
    public List<TDto> search(String query) {
        Stream<TEntity> stream = StreamSupport.stream(
                this.getSearchRepo().search(
                        QueryBuilders.queryStringQuery(query)).spliterator(),
                false);
        
        return stream.map(entity -> this.getMapper().toDto(entity))
                .collect(Collectors.toList());
    }
}
