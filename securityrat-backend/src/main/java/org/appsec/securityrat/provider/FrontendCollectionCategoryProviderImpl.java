package org.appsec.securityrat.provider;

import java.util.stream.Collectors;
import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.FrontendCollectionCategoryProvider;
import org.appsec.securityrat.api.dto.frontend.CollectionCategory;
import org.appsec.securityrat.api.dto.frontend.CollectionInstance;
import org.appsec.securityrat.repository.CollectionCategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class FrontendCollectionCategoryProviderImpl
        extends AbstractFrontendProviderImplementation<
            org.appsec.securityrat.api.dto.frontend.CollectionCategory,
            org.appsec.securityrat.domain.CollectionCategory>
        implements FrontendCollectionCategoryProvider {
    
    @Getter
    @Inject
    private CollectionCategoryRepository repository;

    @Override
    protected CollectionCategory createDto(
            org.appsec.securityrat.domain.CollectionCategory entity) {
        CollectionCategory dto = new CollectionCategory();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        
        // NOTE: There is no dedicated provider for CollectionInstance.
        
        dto.setCollectionInstances(entity.getCollectionInstances()
                .stream()
                .map(e -> {
                    CollectionInstance inst = new CollectionInstance();
                    
                    inst.setId(e.getId());
                    inst.setName(e.getName());
                    inst.setDescription(e.getDescription());
                    inst.setShowOrder(e.getShowOrder());
                    
                    return inst;
                })
                .collect(Collectors.toSet()));
        
        return dto;
    }
}
