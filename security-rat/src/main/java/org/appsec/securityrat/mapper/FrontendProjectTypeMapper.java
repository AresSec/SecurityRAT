package org.appsec.securityrat.mapper;

import javax.inject.Inject;
import org.appsec.securityrat.api.dto.frontend.ProjectType;
import org.springframework.stereotype.Service;

@Service
public class FrontendProjectTypeMapper extends AbstractFrontendMapperBase<
        org.appsec.securityrat.domain.ProjectType,
        org.appsec.securityrat.api.dto.frontend.ProjectType> {
    
    @Inject
    private FrontendOptionColumnMapper frontendOptionColumnMapper;
    
    @Inject
    private FrontendStatusColumnMapper frontendStatusColumnMapper;
    
    @Override
    public ProjectType toDto(org.appsec.securityrat.domain.ProjectType entity) {
        if (entity == null) {
            return null;
        }
        
        ProjectType dto = new ProjectType();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setOptionColumns(this.frontendOptionColumnMapper.toDtoSet(
                entity.getOptColumns()));
        
        dto.setStatusColumns(this.frontendStatusColumnMapper.toDtoSet(
                entity.getStatusColumns()));
        
        return dto;
    }
}
