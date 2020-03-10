package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.FrontendCollectionCategoryProvider;
import org.appsec.securityrat.mapper.FrontendCollectionCategoryMapper;
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
    private CollectionCategoryRepository repo;
    
    @Getter
    @Inject
    private FrontendCollectionCategoryMapper mapper;
}
