package org.appsec.securityrat.mapper;

import javax.inject.Inject;
import org.appsec.securityrat.api.dto.StatusColumnValue;
import org.springframework.stereotype.Service;

@Service
public class StatusColumnValueMapper extends AbstractMapperBase<
        org.appsec.securityrat.domain.StatusColumnValue,
        org.appsec.securityrat.api.dto.StatusColumnValue> {
    
    @Inject
    private StatusColumnMapper statusColumnMapper;

    @Override
    public StatusColumnValue toDto(
            org.appsec.securityrat.domain.StatusColumnValue entity) {
        if (entity == null) {
            return null;
        }
        
        StatusColumnValue dto = new StatusColumnValue();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setActive(entity.getActive());
        dto.setStatusColumn(this.statusColumnMapper.toDto(
                entity.getStatusColumn()));
        
        return dto;
    }

    @Override
    public org.appsec.securityrat.domain.StatusColumnValue toEntity(
            StatusColumnValue dto) {
        if (dto == null) {
            return null;
        }
        
        org.appsec.securityrat.domain.StatusColumnValue entity =
                new org.appsec.securityrat.domain.StatusColumnValue();
        
        entity.setId(dto.getId().orElse(null));
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setShowOrder(dto.getShowOrder());
        entity.setActive(dto.getActive());
        entity.setStatusColumn(this.statusColumnMapper.toEntity(
                dto.getStatusColumn()));
        
        return entity;
    }
}
