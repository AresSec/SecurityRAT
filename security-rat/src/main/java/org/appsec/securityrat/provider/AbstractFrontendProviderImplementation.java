package org.appsec.securityrat.provider;

import java.util.List;
import org.appsec.securityrat.api.FrontendDtoProvider;
import org.appsec.securityrat.mapper.AbstractMapperBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractFrontendProviderImplementation<TId, TDto, TEntity>
        implements FrontendDtoProvider<TDto> {
    
    protected abstract JpaRepository<TEntity, TId> getRepo();
    protected abstract AbstractMapperBase<TEntity, TDto> getMapper();

    @Override
    @Transactional
    public List<TDto> findAll() {
        return this.getMapper().toDtoList(this.getRepo().findAll());
    }
}
