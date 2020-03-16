package org.appsec.securityrat.mapper;

import java.util.Collections;
import javax.inject.Inject;
import org.appsec.securityrat.api.dto.TagInstance;
import org.springframework.stereotype.Service;

@Service
public class TagInstanceMapper extends AbstractMapperBase<
        org.appsec.securityrat.domain.TagInstance,
        org.appsec.securityrat.api.dto.TagInstance> {
    
    @Inject
    private TagCategoryMapper tagCategoryMapper;

    @Override
    public TagInstance toDto(
            org.appsec.securityrat.domain.TagInstance entity) {
        if (entity == null) {
            return null;
        }
        
        TagInstance dto = new TagInstance();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setActive(entity.getActive());
        dto.setTagCategory(this.tagCategoryMapper.toDto(
                entity.getTagCategory()));
        
        return dto;
    }

    @Override
    public org.appsec.securityrat.domain.TagInstance toEntity(
            TagInstance dto) {
        if (dto == null) {
            return null;
        }
        
        org.appsec.securityrat.domain.TagInstance entity =
                new org.appsec.securityrat.domain.TagInstance();
        
        entity.setId(dto.getId().orElse(null));
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setShowOrder(dto.getShowOrder());
        entity.setActive(dto.getActive());
        entity.setTagCategory(this.tagCategoryMapper.toEntity(
                dto.getTagCategory()));
        
        entity.setRequirementSkeletons(Collections.EMPTY_SET);
        
        return entity;
    }
}
