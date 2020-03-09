package org.appsec.securityrat.mapper;

import java.util.Collections;
import javax.inject.Inject;
import org.appsec.securityrat.api.dto.ProjectType;
import org.springframework.stereotype.Service;

@Service
public class ProjectTypeMapper extends AbstractMapperBase<
        org.appsec.securityrat.domain.ProjectType,
        org.appsec.securityrat.api.dto.ProjectType> {
    
    @Inject
    private StatusColumnMapper statusColumnMapper;
    
    @Inject
    private OptColumnMapper optColumnMapper;

    @Override
    public ProjectType toDto(
            org.appsec.securityrat.domain.ProjectType entity) {
        if (entity == null) {
            return null;
        }
        
        ProjectType dto = new ProjectType();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setActive(entity.isActive());
        dto.setStatusColumns(this.statusColumnMapper.toDtoSet(
                entity.getStatusColumns()));
        
        dto.setOptColumns(this.optColumnMapper.toDtoSet(
                entity.getOptColumns()));
        
        return dto;
    }

    @Override
    public org.appsec.securityrat.domain.ProjectType toEntity(
            ProjectType dto) {
        if (dto == null) {
            return null;
        }
        
        org.appsec.securityrat.domain.ProjectType entity =
                new org.appsec.securityrat.domain.ProjectType();
        
        entity.setId(dto.getId().orElse(null));
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setShowOrder(dto.getShowOrder());
        entity.setActive(dto.getActive());
        entity.setStatusColumns(this.statusColumnMapper.toEntitySet(
                dto.getStatusColumns()));
        
        entity.setOptColumns(this.optColumnMapper.toEntitySet(
                dto.getOptColumns()));
        
        entity.setRequirementSkeletons(Collections.EMPTY_SET);
        
        return entity;
    }
}
