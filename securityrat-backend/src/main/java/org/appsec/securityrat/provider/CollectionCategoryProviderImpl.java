package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.dto.CollectionCategory;
import org.appsec.securityrat.repository.CollectionCategoryRepository;
import org.appsec.securityrat.repository.search.CollectionCategorySearchRepository;
import org.springframework.stereotype.Service;

@Service
public class CollectionCategoryProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.CollectionCategory,
            org.appsec.securityrat.domain.CollectionCategory> {
    
    @Inject
    @Getter
    private CollectionCategoryRepository repository;
    
    @Inject
    @Getter
    private CollectionCategorySearchRepository searchRepository;
    
    @Override
    protected CollectionCategory createDto(
            org.appsec.securityrat.domain.CollectionCategory entity) {
        CollectionCategory dto = new CollectionCategory();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setActive(entity.getActive());
        
        return dto;
    }

    @Override
    protected org.appsec.securityrat.domain.CollectionCategory createOrUpdateEntity(
            CollectionCategory dto,
            org.appsec.securityrat.domain.CollectionCategory target) {
        if (target == null) {
            target = new org.appsec.securityrat.domain.CollectionCategory();
        }
        
        target.setName(dto.getName());
        target.setDescription(dto.getDescription());
        target.setShowOrder(dto.getShowOrder());
        target.setActive(dto.getActive());
        
        return target;
    }

    @Override
    protected Long getId(
            org.appsec.securityrat.domain.CollectionCategory entity) {
        return entity.getId();
    }
}
