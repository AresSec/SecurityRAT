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

public abstract class AbstractProviderImplementation<
        TIdType,
        TDto extends IdentifiableDto<TIdType>,
        TEntity>
        implements IdentifiableDtoProvider<TIdType, TDto> {
    
    protected abstract JpaRepository<TEntity, TIdType> getRepo();
    protected abstract ElasticsearchRepository<TEntity, TIdType>
        getSearchRepo();
        
    protected abstract AbstractMapperBase<TEntity, TDto> getMapper();

    @Override
    public List<TDto> findAll() {
        return this.getMapper().toDtoList(this.getRepo().findAll());
    }

    @Override
    public Optional<TDto> findById(TIdType id) {
        return this.getRepo()
                .findById(id)
                .map(entity -> this.getMapper().toDto(entity));
    }

    @Override
    public TDto save(TDto dto) {
        TEntity entity = this.getMapper().toEntity(dto);
        
        TEntity resultEntity = this.getRepo().save(entity);
        this.getSearchRepo().save(entity);
        
        return this.getMapper().toDto(resultEntity);
    }

    @Override
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
    public List<TDto> search(String query) {
        Stream<TEntity> stream = StreamSupport.stream(
                this.getSearchRepo().search(
                        QueryBuilders.queryStringQuery(query)).spliterator(),
                false);
        
        return stream.map(entity -> this.getMapper().toDto(entity))
                .collect(Collectors.toList());
    }
}
