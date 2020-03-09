package org.appsec.securityrat.mapper;

import java.util.Collections;
import org.appsec.securityrat.api.dto.CollectionCategory;
import org.springframework.stereotype.Service;

@Service
public class CollectionCategoryMapper extends AbstractMapperBase<
        org.appsec.securityrat.domain.CollectionCategory,
        org.appsec.securityrat.api.dto.CollectionCategory> {

    @Override
    public CollectionCategory toDto(
            org.appsec.securityrat.domain.CollectionCategory entity) {
        if (entity == null) {
            return null;
        }
        
        CollectionCategory dto = new CollectionCategory();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setActive(entity.isActive());
        
        return dto;
    }

    @Override
    public org.appsec.securityrat.domain.CollectionCategory toEntity(
            CollectionCategory dto) {
        if (dto == null) {
            return null;
        }
        
        org.appsec.securityrat.domain.CollectionCategory entity =
                new org.appsec.securityrat.domain.CollectionCategory();
        
        entity.setId(dto.getId().orElse(null));
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setShowOrder(dto.getShowOrder());
        entity.setActive(dto.getActive());
        entity.setCollectionInstances(Collections.EMPTY_SET);
        
        return entity;
    }
}
