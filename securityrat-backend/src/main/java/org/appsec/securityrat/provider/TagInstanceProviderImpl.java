package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.dto.rest.TagInstance;
import org.appsec.securityrat.repository.TagInstanceRepository;
import org.appsec.securityrat.repository.search.TagInstanceSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class TagInstanceProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.rest.TagInstance,
            org.appsec.securityrat.domain.TagInstance> {
    
    @Getter
    @Inject
    private TagInstanceRepository repository;
    
    @Getter
    @Inject
    private TagInstanceSearchRepository searchRepository;
    
    @Inject
    private TagCategoryProviderImpl tagCategories;

    @Override
    protected TagInstance createDto(
            org.appsec.securityrat.domain.TagInstance entity) {
        TagInstance dto = new TagInstance();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setActive(entity.getActive());
        dto.setTagCategory(this.tagCategories.createDtoChecked(
                entity.getTagCategory()));
        
        return dto;
    }

    @Override
    protected org.appsec.securityrat.domain.TagInstance createOrUpdateEntity(
            TagInstance dto,
            org.appsec.securityrat.domain.TagInstance target) {
        if (target == null) {
            target = new org.appsec.securityrat.domain.TagInstance();
        }
        
        target.setName(dto.getName());
        target.setDescription(dto.getDescription());
        target.setShowOrder(dto.getShowOrder());
        target.setActive(dto.getActive());
        target.setTagCategory(this.tagCategories.findByDto(
                dto.getTagCategory()).get());
        
        return target;
    }

    @Override
    protected Long getId(org.appsec.securityrat.domain.TagInstance entity) {
        return entity.getId();
    }
}
