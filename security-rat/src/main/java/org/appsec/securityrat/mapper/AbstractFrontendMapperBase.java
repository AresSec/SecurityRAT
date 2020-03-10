package org.appsec.securityrat.mapper;

public abstract class AbstractFrontendMapperBase<TEntity, TDto>
        extends AbstractMapperBase<TEntity, TDto> {
    // NOTE: Frontend DTOs are readonly, thus no 'toEntity' method is required.
    
    @Override
    public final TEntity toEntity(TDto dto) {
        throw new UnsupportedOperationException();
    }
}
