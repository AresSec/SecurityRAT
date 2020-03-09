package org.appsec.securityrat.mapper;

import javax.inject.Inject;
import org.appsec.securityrat.api.dto.OptColumnContent;
import org.springframework.stereotype.Service;

@Service
public class OptColumnContentMapper extends AbstractMapperBase<
        org.appsec.securityrat.domain.OptColumnContent,
        org.appsec.securityrat.api.dto.OptColumnContent> {
    
    @Inject
    private OptColumnMapper optColumnMapper;
    
    @Inject
    private RequirementSkeletonMapper requirementSkeletonMapper;

    @Override
    public OptColumnContent toDto(
            org.appsec.securityrat.domain.OptColumnContent entity) {
        if (entity == null) {
            return null;
        }
        
        OptColumnContent dto = new OptColumnContent();
        
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setOptColumn(this.optColumnMapper.toDto(entity.getOptColumn()));
        dto.setRequirementSkeleton(this.requirementSkeletonMapper.toDto(
                entity.getRequirementSkeleton()));
        
        return dto;
    }

    @Override
    public org.appsec.securityrat.domain.OptColumnContent toEntity(
            OptColumnContent dto) {
        if (dto == null) {
            return null;
        }
        
        org.appsec.securityrat.domain.OptColumnContent entity =
                new org.appsec.securityrat.domain.OptColumnContent();
        
        entity.setId(dto.getId().orElse(null));
        entity.setContent(dto.getContent());
        entity.setOptColumn(this.optColumnMapper.toEntity(dto.getOptColumn()));
        entity.setRequirementSkeleton(this.requirementSkeletonMapper.toEntity(
                dto.getRequirementSkeleton()));
        
        return entity;
    }
}
