package org.appsec.securityrat.mapper;

import org.appsec.securityrat.api.dto.frontend.CollectionInstance;
import org.springframework.stereotype.Service;

@Service
public class FrontendCollectionInstanceMapper
        extends AbstractFrontendMapperBase<
            org.appsec.securityrat.domain.CollectionInstance,
            org.appsec.securityrat.api.dto.frontend.CollectionInstance> {

    @Override
    public CollectionInstance toDto(
            org.appsec.securityrat.domain.CollectionInstance entity) {
        if (entity == null) {
            return null;
        }
        
        CollectionInstance dto = new CollectionInstance();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        
        return dto;
    }
}
