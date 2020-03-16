package org.appsec.securityrat.mapper;

import org.appsec.securityrat.api.dto.frontend.OptionColumn;
import org.appsec.securityrat.domain.OptColumn;
import org.springframework.stereotype.Service;

@Service
public class FrontendOptionColumnMapper extends AbstractFrontendMapperBase<
        org.appsec.securityrat.domain.OptColumn,
        org.appsec.securityrat.api.dto.frontend.OptionColumn> {

    @Override
    public OptionColumn toDto(OptColumn entity) {
        if (entity == null) {
            return null;
        }
        
        OptionColumn dto = new OptionColumn();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setType(entity.getOptColumnType().getName());
        dto.setVisibleByDefault(entity.getIsVisibleByDefault());
        
        return dto;
    }
}
