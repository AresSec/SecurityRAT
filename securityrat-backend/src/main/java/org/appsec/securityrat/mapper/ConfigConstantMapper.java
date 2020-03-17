package org.appsec.securityrat.mapper;

import org.appsec.securityrat.api.dto.ConfigConstant;
import org.springframework.stereotype.Service;

@Service
public class ConfigConstantMapper extends AbstractMapperBase<
        org.appsec.securityrat.domain.ConfigConstant,
        org.appsec.securityrat.api.dto.ConfigConstant> {

    @Override
    public ConfigConstant toDto(
            org.appsec.securityrat.domain.ConfigConstant entity) {
        
        if (entity == null) {
            return null;
        }
        
        ConfigConstant dto = new ConfigConstant();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setValue(entity.getValue());
        dto.setDescription(entity.getDescription());
        
        return dto;
    }

    @Override
    public org.appsec.securityrat.domain.ConfigConstant toEntity(
            ConfigConstant dto) {
        
        if (dto == null) {
            return null;
        }
        
        org.appsec.securityrat.domain.ConfigConstant entity =
                new org.appsec.securityrat.domain.ConfigConstant();
        
        entity.setId(dto.getId().orElse(null));
        entity.setName(dto.getName());
        entity.setValue(dto.getValue());
        entity.setDescription(dto.getDescription());
        
        return entity;
    }
}
