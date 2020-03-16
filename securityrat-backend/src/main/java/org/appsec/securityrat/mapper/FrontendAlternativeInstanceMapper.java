package org.appsec.securityrat.mapper;

import org.appsec.securityrat.api.dto.frontend.AlternativeInstance;
import org.springframework.stereotype.Service;

@Service
public class FrontendAlternativeInstanceMapper
        extends AbstractFrontendMapperBase<
            org.appsec.securityrat.domain.AlternativeInstance,
            org.appsec.securityrat.api.dto.frontend.AlternativeInstance> {

    @Override
    public AlternativeInstance toDto(
            org.appsec.securityrat.domain.AlternativeInstance entity) {
        if (entity == null) {
            return null;
        }
        
        AlternativeInstance dto = new AlternativeInstance();
        
        dto.setId(entity.getId());
        dto.setRequirementId(entity.getRequirementSkeleton().getId());
        dto.setContent(entity.getContent());
        
        return dto;
    }
}
