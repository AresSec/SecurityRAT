package org.appsec.securityrat.mapper;

import javax.inject.Inject;
import org.appsec.securityrat.api.dto.AlternativeSet;
import org.springframework.stereotype.Service;

@Service
public class AlternativeSetMapper extends AbstractMapperBase<
        org.appsec.securityrat.domain.AlternativeSet,
        org.appsec.securityrat.api.dto.AlternativeSet> {
    
    @Inject
    private OptColumnMapper optColumnMapper;
    
    @Inject
    private AlternativeInstanceMapper alternativeInstanceMapper;

    @Override
    public AlternativeSet toDto(
            org.appsec.securityrat.domain.AlternativeSet entity) {
        if (entity == null) {
            return null;
        }
        
        AlternativeSet dto = new AlternativeSet();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setActive(entity.getActive());
        dto.setOptColumn(this.optColumnMapper.toDto(entity.getOptColumn()));
        dto.setAlternativeInstances(this.alternativeInstanceMapper.toDtoSet(
                entity.getAlternativeInstances()));
        
        return dto;
    }

    @Override
    public org.appsec.securityrat.domain.AlternativeSet toEntity(
            AlternativeSet dto) {
        if (dto == null) {
            return null;
        }
        
        org.appsec.securityrat.domain.AlternativeSet entity =
                new org.appsec.securityrat.domain.AlternativeSet();
        
        entity.setId(dto.getId().orElse(null));
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setShowOrder(dto.getShowOrder());
        entity.setActive(dto.getActive());
        entity.setOptColumn(this.optColumnMapper.toEntity(dto.getOptColumn()));
        entity.setAlternativeInstances(
                this.alternativeInstanceMapper.toEntitySet(
                        dto.getAlternativeInstances()));
        
        return entity;
    }
}
