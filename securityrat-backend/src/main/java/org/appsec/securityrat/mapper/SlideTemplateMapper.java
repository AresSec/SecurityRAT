package org.appsec.securityrat.mapper;

import org.appsec.securityrat.api.dto.SlideTemplate;
import org.springframework.stereotype.Service;

@Service
public class SlideTemplateMapper extends AbstractMapperBase<
        org.appsec.securityrat.domain.SlideTemplate,
        org.appsec.securityrat.api.dto.SlideTemplate> {

    @Override
    public SlideTemplate toDto(
            org.appsec.securityrat.domain.SlideTemplate entity) {
        if (entity == null) {
            return null;
        }
        
        SlideTemplate dto = new SlideTemplate();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        
        return dto;
    }

    @Override
    public org.appsec.securityrat.domain.SlideTemplate toEntity(
            SlideTemplate dto) {
        if (dto == null) {
            return null;
        }
        
        org.appsec.securityrat.domain.SlideTemplate entity =
                new org.appsec.securityrat.domain.SlideTemplate();
        
        entity.setId(dto.getId().orElse(null));
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        
        return entity;
    }
}
