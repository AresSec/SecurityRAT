package org.appsec.securityrat.mapper;

import org.appsec.securityrat.api.dto.frontend.StatusColumnValue;
import org.springframework.stereotype.Service;

@Service
public class FrontendStatusColumnValueMapper
        extends AbstractFrontendMapperBase<
            org.appsec.securityrat.domain.StatusColumnValue,
            org.appsec.securityrat.api.dto.frontend.StatusColumnValue> {

    @Override
    public StatusColumnValue toDto(
            org.appsec.securityrat.domain.StatusColumnValue entity) {
        if (entity == null) {
            return null;
        }
        
        StatusColumnValue dto = new StatusColumnValue();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        
        return dto;
    }
}
