package org.appsec.securityrat.mapper;

import javax.inject.Inject;
import org.appsec.securityrat.api.dto.frontend.Category;
import org.appsec.securityrat.domain.ReqCategory;
import org.springframework.stereotype.Service;

@Service
public class FrontendCategoryMapper extends AbstractFrontendMapperBase<
        org.appsec.securityrat.domain.ReqCategory,
        org.appsec.securityrat.api.dto.frontend.Category> {
    
    @Inject
    private FrontendRequirementMapper frontendRequirementMapper;
    
    @Override
    public Category toDto(ReqCategory entity) {
        if (entity == null) {
            return null;
        }
        
        Category dto = new Category();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShortcut(entity.getShortcut());
        dto.setShowOrder(entity.getShowOrder());
        dto.setRequirements(this.frontendRequirementMapper.toDtoSet(
                entity.getRequirementSkeletons()));
        
        return dto;
    }
}
