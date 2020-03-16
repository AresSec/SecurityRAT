package org.appsec.securityrat.mapper;

import java.util.Collections;
import org.appsec.securityrat.api.dto.StatusColumn;
import org.springframework.stereotype.Service;

@Service
public class StatusColumnMapper extends AbstractMapperBase<
        org.appsec.securityrat.domain.StatusColumn,
        org.appsec.securityrat.api.dto.StatusColumn> {

    @Override
    public StatusColumn toDto(
            org.appsec.securityrat.domain.StatusColumn entity) {
        if (entity == null) {
            return null;
        }
        
        StatusColumn dto = new StatusColumn();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setIsEnum(entity.getIsEnum());
        dto.setShowOrder(entity.getShowOrder());
        dto.setActive(entity.getActive());
        
        return dto;
    }

    @Override
    public org.appsec.securityrat.domain.StatusColumn toEntity(
            StatusColumn dto) {
        if (dto == null) {
            return null;
        }
        
        org.appsec.securityrat.domain.StatusColumn entity =
                new org.appsec.securityrat.domain.StatusColumn();
        
        entity.setId(dto.getId().orElse(null));
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setIsEnum(dto.getIsEnum());
        entity.setShowOrder(dto.getShowOrder());
        entity.setActive(dto.getActive());
        entity.setStatusColumnValues(Collections.EMPTY_SET);
        entity.setProjectTypes(Collections.EMPTY_SET);
        
        return entity;
    }
}
