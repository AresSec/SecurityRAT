package org.appsec.securityrat.mapper;

import java.util.Collections;
import javax.inject.Inject;
import org.appsec.securityrat.api.dto.RequirementSkeleton;
import org.springframework.stereotype.Service;

@Service
public class RequirementSkeletonMapper extends AbstractMapperBase<
        org.appsec.securityrat.domain.RequirementSkeleton,
        org.appsec.securityrat.api.dto.RequirementSkeleton> {
    
    @Inject
    private ReqCategoryMapper reqCategoryMapper;
    
    @Inject
    private TagInstanceMapper tagInstanceMapper;
    
    @Inject
    private CollectionInstanceMapper collectionInstanceMapper;
    
    @Inject
    private ProjectTypeMapper projectTypeMapper;

    @Override
    public RequirementSkeleton toDto(
            org.appsec.securityrat.domain.RequirementSkeleton entity) {
        if (entity == null) {
            return null;
        }
        
        RequirementSkeleton dto = new RequirementSkeleton();
        
        dto.setId(entity.getId());
        dto.setUniversalId(entity.getUniversalId());
        dto.setShortName(entity.getShortName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setActive(entity.isActive());
        dto.setReqCategory(this.reqCategoryMapper.toDto(
                entity.getReqCategory()));
        
        dto.setTagInstances(this.tagInstanceMapper.toDtoSet(
                entity.getTagInstances()));
        
        dto.setCollectionInstances(this.collectionInstanceMapper.toDtoSet(
                entity.getCollectionInstances()));
        
        dto.setProjectTypes(this.projectTypeMapper.toDtoSet(
                entity.getProjectTypes()));
        
        return dto;
    }

    @Override
    public org.appsec.securityrat.domain.RequirementSkeleton toEntity(
            RequirementSkeleton dto) {
        if (dto == null) {
            return null;
        }
        
        org.appsec.securityrat.domain.RequirementSkeleton entity =
                new org.appsec.securityrat.domain.RequirementSkeleton();
        
        entity.setId(dto.getId().orElse(null));
        entity.setUniversalId(dto.getUniversalId());
        entity.setShortName(dto.getShortName());
        entity.setDescription(dto.getDescription());
        entity.setShowOrder(dto.getShowOrder());
        entity.setActive(dto.getActive());
        entity.setOptColumnContents(Collections.EMPTY_SET);
        entity.setAlternativeInstances(Collections.EMPTY_SET);
        entity.setReqCategory(this.reqCategoryMapper.toEntity(
                dto.getReqCategory()));
        
        entity.setTagInstances(this.tagInstanceMapper.toEntitySet(
                dto.getTagInstances()));
        
        entity.setCollectionInstances(
                this.collectionInstanceMapper.toEntitySet(
                        dto.getCollectionInstances()));
        
        entity.setProjectTypes(this.projectTypeMapper.toEntitySet(
                dto.getProjectTypes()));
        
        return entity;
    }
}
