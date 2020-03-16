package org.appsec.securityrat.mapper;

import org.appsec.securityrat.api.dto.frontend.AlternativeSet;
import org.springframework.stereotype.Service;

@Service
public class FrontendAlternativeSetMapper extends AbstractFrontendMapperBase<
        org.appsec.securityrat.domain.AlternativeSet,
        org.appsec.securityrat.api.dto.frontend.AlternativeSet> {

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
        
        return dto;
    }
}
