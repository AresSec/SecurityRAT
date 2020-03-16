package org.appsec.securityrat.mapper;

import javax.inject.Inject;
import org.appsec.securityrat.api.dto.frontend.StatusColumn;
import org.springframework.stereotype.Service;

@Service
public class FrontendStatusColumnMapper extends AbstractFrontendMapperBase<
        org.appsec.securityrat.domain.StatusColumn,
        org.appsec.securityrat.api.dto.frontend.StatusColumn> {
    
    @Inject
    private FrontendStatusColumnValueMapper frontendStatusColumnValueMapper;

    @Override
    public StatusColumn toDto(
            org.appsec.securityrat.domain.StatusColumn entity) {
        if (entity == null) {
            return null;
        }
        
        StatusColumn dto = new StatusColumn();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setIsEnum(entity.getIsEnum());
        dto.setValues(this.frontendStatusColumnValueMapper.toDtoSet(
                entity.getStatusColumnValues()));
        
        return dto;
    }
}
