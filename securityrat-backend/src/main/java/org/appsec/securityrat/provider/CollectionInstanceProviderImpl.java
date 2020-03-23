package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.dto.rest.CollectionInstance;
import org.appsec.securityrat.repository.CollectionInstanceRepository;
import org.appsec.securityrat.repository.search.CollectionInstanceSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class CollectionInstanceProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.rest.CollectionInstance,
            org.appsec.securityrat.domain.CollectionInstance> {
    
    @Getter
    @Inject
    private CollectionInstanceRepository repository;
    
    @Getter
    @Inject
    private CollectionInstanceSearchRepository searchRepository;
    
    @Inject
    private CollectionCategoryProviderImpl collectionCategories;
    
    @Override
    protected CollectionInstance createDto(
            org.appsec.securityrat.domain.CollectionInstance entity) {
        CollectionInstance dto = new CollectionInstance();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setActive(entity.getActive());
        
        dto.setCollectionCategory(this.collectionCategories.createDto(
                entity.getCollectionCategory()));
        
        return dto;
    }

    @Override
    protected org.appsec.securityrat.domain.CollectionInstance createOrUpdateEntity(
            CollectionInstance dto,
            org.appsec.securityrat.domain.CollectionInstance target) {
        if (target == null) {
            target = new org.appsec.securityrat.domain.CollectionInstance();
        }
        
        target.setName(dto.getName());
        target.setDescription(dto.getDescription());
        target.setShowOrder(dto.getShowOrder());
        target.setActive(dto.getActive());
        
        target.setCollectionCategory(this.collectionCategories.findByDto(
                dto.getCollectionCategory()).get());
        
        return target;
    }

    @Override
    protected Long getId(
            org.appsec.securityrat.domain.CollectionInstance entity) {
        return entity.getId();
    }
}
