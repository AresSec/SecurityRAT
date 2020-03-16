package org.appsec.securityrat.mapper;

import java.util.Collections;
import org.appsec.securityrat.api.dto.TagCategory;
import org.springframework.stereotype.Service;

@Service
public class TagCategoryMapper extends AbstractMapperBase<
        org.appsec.securityrat.domain.TagCategory,
        org.appsec.securityrat.api.dto.TagCategory> {

    @Override
    public TagCategory toDto(
            org.appsec.securityrat.domain.TagCategory entity) {
        if (entity == null) {
            return null;
        }
        
        TagCategory dto = new TagCategory();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setActive(entity.getActive());
        
        return dto;
    }

    @Override
    public org.appsec.securityrat.domain.TagCategory toEntity(
            TagCategory dto) {
        if (dto == null) {
            return null;
        }
        
        org.appsec.securityrat.domain.TagCategory entity =
                new org.appsec.securityrat.domain.TagCategory();
        
        entity.setId(dto.getId().orElse(null));
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setShowOrder(dto.getShowOrder());
        entity.setActive(dto.getActive());
        entity.setTagInstances(Collections.EMPTY_SET);
        
        return entity;
    }
}
