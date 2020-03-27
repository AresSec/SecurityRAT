package org.appsec.securityrat.provider.mapper;

import org.appsec.securityrat.api.dto.IdentifiableDto;

public interface SimpleMapper<TId, TEntity, TDto extends IdentifiableDto<TId>> {
    TDto toDto(TEntity entity);
    
    TEntity toEntity(TDto dto);
}
