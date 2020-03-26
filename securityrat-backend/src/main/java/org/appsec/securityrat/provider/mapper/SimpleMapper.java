package org.appsec.securityrat.provider.mapper;

import org.appsec.securityrat.api.dto.IdentifiableDto;

public interface SimpleMapper<TEntity, TDto extends IdentifiableDto<?>> {
    TDto toDto(TEntity entity);
    
    TEntity toEntity(TDto dto);
}
