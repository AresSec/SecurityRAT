package org.appsec.securityrat.mapper;

import javax.inject.Inject;
import org.appsec.securityrat.api.dto.frontend.CollectionCategory;
import org.springframework.stereotype.Service;

@Service
public class FrontendCollectionCategoryMapper
        extends AbstractFrontendMapperBase<
            org.appsec.securityrat.domain.CollectionCategory,
        org.appsec.securityrat.api.dto.frontend.CollectionCategory> {
    
    @Inject
    private FrontendCollectionInstanceMapper frontendCollectionInstanceMapper;
    
    @Override
    public CollectionCategory toDto(
            org.appsec.securityrat.domain.CollectionCategory entity) {
        if (entity == null) {
            return null;
        }
        
        CollectionCategory dto = new CollectionCategory();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setCollectionInstances(
                this.frontendCollectionInstanceMapper.toDtoSet(
                        entity.getCollectionInstances()));
        
        return dto;
    }
}
