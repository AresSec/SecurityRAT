package org.appsec.securityrat.mapper;

import org.appsec.securityrat.api.dto.frontend.TagInstance;
import org.springframework.stereotype.Service;

@Service
public class FrontendTagInstanceMapper extends AbstractFrontendMapperBase<
        org.appsec.securityrat.domain.TagInstance,
        org.appsec.securityrat.api.dto.frontend.TagInstance> {

    @Override
    public TagInstance toDto(org.appsec.securityrat.domain.TagInstance entity) {
        if (entity == null) {
            return null;
        }
        
        TagInstance dto = new TagInstance();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        
        return dto;
    }
}
