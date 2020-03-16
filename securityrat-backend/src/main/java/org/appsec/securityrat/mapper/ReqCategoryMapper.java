package org.appsec.securityrat.mapper;

import java.util.Collections;
import org.appsec.securityrat.api.dto.ReqCategory;
import org.springframework.stereotype.Service;

@Service
public class ReqCategoryMapper extends AbstractMapperBase<
        org.appsec.securityrat.domain.ReqCategory,
        org.appsec.securityrat.api.dto.ReqCategory> {

    @Override
    public ReqCategory toDto(
            org.appsec.securityrat.domain.ReqCategory entity) {
        if (entity == null) {
            return null;
        }
        
        ReqCategory dto = new ReqCategory();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setShortcut(entity.getShortcut());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setActive(entity.getActive());
        
        return dto;
    }

    @Override
    public org.appsec.securityrat.domain.ReqCategory toEntity(
            ReqCategory dto) {
        if (dto == null) {
            return null;
        }
        
        org.appsec.securityrat.domain.ReqCategory entity =
                new org.appsec.securityrat.domain.ReqCategory();
        
        entity.setId(dto.getId().orElse(null));
        entity.setName(dto.getName());
        entity.setShortcut(dto.getShortcut());
        entity.setDescription(dto.getDescription());
        entity.setShowOrder(dto.getShowOrder());
        entity.setActive(dto.getActive());
        entity.setRequirementSkeletons(Collections.EMPTY_SET);
        
        return entity;
    }
}
