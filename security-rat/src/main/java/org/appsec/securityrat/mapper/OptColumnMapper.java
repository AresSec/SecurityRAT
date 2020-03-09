package org.appsec.securityrat.mapper;

import javax.inject.Inject;
import org.appsec.securityrat.api.dto.OptColumn;
import org.springframework.stereotype.Service;

@Service
public class OptColumnMapper extends AbstractMapperBase<
        org.appsec.securityrat.domain.OptColumn,
        org.appsec.securityrat.api.dto.OptColumn> {
    
    @Inject
    private OptColumnTypeMapper optColumnTypeMapper;
    
    @Inject
    private AlternativeSetMapper alternativeSetMapper;
    
    @Inject
    private OptColumnContentMapper optColumnContentMapper;
    
    @Inject
    private ProjectTypeMapper projectTypeMapper;

    @Override
    public OptColumn toDto(org.appsec.securityrat.domain.OptColumn entity) {
        if (entity == null) {
            return null;
        }
        
        OptColumn dto = new OptColumn();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setActive(entity.isActive());
        dto.setIsVisibleByDefault(entity.isIsVisibleByDefault());
        dto.setOptColumnType(this.optColumnTypeMapper.toDto(
                entity.getOptColumnType()));
        
        dto.setAlternativeSets(this.alternativeSetMapper.toDtoSet(
                entity.getAlternativeSets()));
        
        dto.setOptColumnContents(this.optColumnContentMapper.toDtoSet(
                entity.getOptColumnContents()));
        
        dto.setProjectTypes(this.projectTypeMapper.toDtoSet(
                entity.getProjectTypes()));
        
        return dto;
    }

    @Override
    public org.appsec.securityrat.domain.OptColumn toEntity(OptColumn dto) {
        if (dto == null) {
            return null;
        }
        
        org.appsec.securityrat.domain.OptColumn entity =
                new org.appsec.securityrat.domain.OptColumn();
        
        entity.setId(dto.getId().orElse(null));
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setShowOrder(dto.getShowOrder());
        entity.setActive(dto.getActive());
        entity.setIsVisibleByDefault(dto.getIsVisibleByDefault());
        entity.setOptColumnType(this.optColumnTypeMapper.toEntity(
                dto.getOptColumnType()));
        
        entity.setAlternativeSets(this.alternativeSetMapper.toEntitySet(
                dto.getAlternativeSets()));
        
        entity.setOptColumnContents(this.optColumnContentMapper.toEntitySet(
                dto.getOptColumnContents()));
        
        entity.setProjectTypes(this.projectTypeMapper.toEntitySet(
                dto.getProjectTypes()));
        
        return entity;
    }
}
