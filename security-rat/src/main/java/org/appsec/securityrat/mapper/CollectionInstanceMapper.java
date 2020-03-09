package org.appsec.securityrat.mapper;

import java.util.Collections;
import javax.inject.Inject;
import org.appsec.securityrat.api.dto.CollectionInstance;
import org.springframework.stereotype.Service;

@Service
public class CollectionInstanceMapper extends AbstractMapperBase<
        org.appsec.securityrat.domain.CollectionInstance,
        org.appsec.securityrat.api.dto.CollectionInstance> {
    
    @Inject
    private CollectionCategoryMapper collectionCategoryMapper;

    @Override
    public CollectionInstance toDto(
            org.appsec.securityrat.domain.CollectionInstance entity) {
        if (entity == null) {
            return null;
        }
        
        CollectionInstance dto = new CollectionInstance();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setActive(entity.isActive());
        dto.setCollectionCategory(this.collectionCategoryMapper.toDto(
                entity.getCollectionCategory()));
        
        return dto;
    }

    @Override
    public org.appsec.securityrat.domain.CollectionInstance toEntity(
            CollectionInstance dto) {
        if (dto == null) {
            return null;
        }
        
        org.appsec.securityrat.domain.CollectionInstance entity =
                new org.appsec.securityrat.domain.CollectionInstance();
        
        entity.setId(dto.getId().orElse(null));
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setShowOrder(dto.getShowOrder());
        entity.setActive(dto.getActive());
        entity.setCollectionCategory(this.collectionCategoryMapper.toEntity(
                dto.getCollectionCategory()));
        
        entity.setRequirementSkeletons(Collections.EMPTY_SET);
        
        return entity;
    }
}
