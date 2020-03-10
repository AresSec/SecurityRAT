package org.appsec.securityrat.mapper;

import java.util.Collections;
import org.appsec.securityrat.api.dto.OptColumnType;
import org.springframework.stereotype.Service;

@Service
public class OptColumnTypeMapper extends AbstractMapperBase<
        org.appsec.securityrat.domain.OptColumnType,
        org.appsec.securityrat.api.dto.OptColumnType> {
    
    @Override
    public OptColumnType toDto(
            org.appsec.securityrat.domain.OptColumnType entity) {
        if (entity == null) {
            return null;
        }
        
        OptColumnType dto = new OptColumnType();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        
        return dto;
    }

    @Override
    public org.appsec.securityrat.domain.OptColumnType toEntity(
            OptColumnType dto) {
        if (dto == null) {
            return null;
        }
        
        org.appsec.securityrat.domain.OptColumnType entity =
                new org.appsec.securityrat.domain.OptColumnType();
        
        entity.setId(dto.getId().orElse(null));
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setOptColumns(Collections.EMPTY_SET);
        
        return entity;
    }
}
