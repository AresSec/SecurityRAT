package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.dto.TagCategory;
import org.appsec.securityrat.repository.TagCategoryRepository;
import org.appsec.securityrat.repository.search.TagCategorySearchRepository;
import org.springframework.stereotype.Service;

@Service
public class TagCategoryProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.TagCategory,
            org.appsec.securityrat.domain.TagCategory> {
    
    @Inject
    @Getter
    private TagCategoryRepository repository;
    
    @Inject
    @Getter
    private TagCategorySearchRepository searchRepository;

    @Override
    protected TagCategory createDto(
            org.appsec.securityrat.domain.TagCategory entity) {
        TagCategory dto = new TagCategory();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setActive(entity.getActive());
        
        return dto;
    }

    @Override
    protected org.appsec.securityrat.domain.TagCategory createOrUpdateEntity(
            TagCategory dto,
            org.appsec.securityrat.domain.TagCategory target) {
        if (target == null) {
            target = new org.appsec.securityrat.domain.TagCategory();
        }
        
        target.setName(dto.getName());
        target.setDescription(dto.getDescription());
        target.setShowOrder(dto.getShowOrder());
        target.setActive(dto.getActive());
        
        return target;
    }

    @Override
    protected Long getId(org.appsec.securityrat.domain.TagCategory entity) {
        return entity.getId();
    }
}
