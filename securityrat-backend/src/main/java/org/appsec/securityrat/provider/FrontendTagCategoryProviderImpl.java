package org.appsec.securityrat.provider;

import java.util.stream.Collectors;
import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.dto.frontend.TagCategory;
import org.appsec.securityrat.api.dto.frontend.TagInstance;
import org.appsec.securityrat.repository.TagCategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class FrontendTagCategoryProviderImpl
        extends AbstractFrontendProviderImplementation<
            org.appsec.securityrat.api.dto.frontend.TagCategory,
            org.appsec.securityrat.domain.TagCategory> {
    
    @Getter
    @Inject
    private TagCategoryRepository repository;

    @Override
    protected TagCategory createDto(
            org.appsec.securityrat.domain.TagCategory entity) {
        TagCategory dto = new TagCategory();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        
        // NOTE: There is no dedicated FrontendTagInstanceProvider
        
        dto.setTagInstances(entity.getTagInstances()
                .stream()
                .map(e -> {
                    TagInstance ti = new TagInstance();
                    
                    ti.setId(e.getId());
                    ti.setName(e.getName());
                    ti.setDescription(e.getDescription());
                    ti.setShowOrder(e.getShowOrder());
                    
                    return ti;
                })
                .collect(Collectors.toSet()));
        
        return dto;
    }
}
