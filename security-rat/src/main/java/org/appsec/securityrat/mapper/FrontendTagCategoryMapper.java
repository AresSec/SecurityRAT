package org.appsec.securityrat.mapper;

import javax.inject.Inject;
import org.appsec.securityrat.api.dto.frontend.TagCategory;
import org.springframework.stereotype.Service;

@Service
public class FrontendTagCategoryMapper extends AbstractFrontendMapperBase<
        org.appsec.securityrat.domain.TagCategory,
        org.appsec.securityrat.api.dto.frontend.TagCategory> {
    
    @Inject
    private FrontendTagInstanceMapper frontendTagInstanceMapper;
    
    @Override
    public TagCategory toDto(org.appsec.securityrat.domain.TagCategory entity) {
        if (entity == null) {
            return null;
        }
        
        TagCategory dto = new TagCategory();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setTagInstances(this.frontendTagInstanceMapper.toDtoSet(
                entity.getTagInstances()));
        
        return dto;
    }
}
