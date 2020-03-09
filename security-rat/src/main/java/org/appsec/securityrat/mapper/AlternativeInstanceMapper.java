package org.appsec.securityrat.mapper;

import javax.inject.Inject;
import org.appsec.securityrat.api.dto.AlternativeInstance;
import org.springframework.stereotype.Service;

@Service
public class AlternativeInstanceMapper extends AbstractMapperBase<
        org.appsec.securityrat.domain.AlternativeInstance,
        org.appsec.securityrat.api.dto.AlternativeInstance> {
    
    @Inject
    private AlternativeSetMapper alternativeSetMapper;
    
    @Inject
    private RequirementSkeletonMapper requirementSkeletonMapper;

    @Override
    public AlternativeInstance toDto(
            org.appsec.securityrat.domain.AlternativeInstance entity) {
        if (entity == null) {
            return null;
        }
        
        AlternativeInstance dto = new AlternativeInstance();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setAlternativeSet(this.alternativeSetMapper.toDto(
                entity.getAlternativeSet()));
        
        dto.setRequirementSkeleton(this.requirementSkeletonMapper.toDto(
                entity.getRequirementSkeleton()));
        
        return dto;
    }

    @Override
    public org.appsec.securityrat.domain.AlternativeInstance toEntity(
            AlternativeInstance dto) {
        if (dto == null) {
            return null;
        }
        
        org.appsec.securityrat.domain.AlternativeInstance entity =
                new org.appsec.securityrat.domain.AlternativeInstance();
        
        entity.setId(dto.getId().orElse(null));
        entity.setContent(dto.getContent());
        entity.setAlternativeSet(this.alternativeSetMapper.toEntity(
                dto.getAlternativeSet()));
        
        entity.setRequirementSkeleton(this.requirementSkeletonMapper.toEntity(
                dto.getRequirementSkeleton()));
        
        return entity;
    }
}
