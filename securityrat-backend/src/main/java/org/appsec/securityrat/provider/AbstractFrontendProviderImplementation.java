package org.appsec.securityrat.provider;

import java.util.List;
import java.util.stream.Collectors;
import org.appsec.securityrat.api.FrontendDtoProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractFrontendProviderImplementation<TDto, TEntity>
        extends AbstractDtoMapper<TDto, TEntity>
        implements FrontendDtoProvider<TDto> {
    
    protected abstract JpaRepository<TEntity, ?> getRepository();

    @Override
    @Transactional
    public List<TDto> findAll() {
        return this.getRepository()
                .findAll()
                .stream()
                .map(e -> this.createDtoChecked(e))
                .collect(Collectors.toList());
    }
}
