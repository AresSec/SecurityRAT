package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.CollectionCategoryProvider;
import org.appsec.securityrat.mapper.CollectionCategoryMapper;
import org.appsec.securityrat.repository.CollectionCategoryRepository;
import org.appsec.securityrat.repository.search.CollectionCategorySearchRepository;
import org.springframework.stereotype.Service;

@Service
public class CollectionCategoryProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.CollectionCategory,
            org.appsec.securityrat.domain.CollectionCategory>
        implements CollectionCategoryProvider {
    
    @Inject
    @Getter
    private CollectionCategoryRepository repo;
    
    @Inject
    @Getter
    private CollectionCategorySearchRepository searchRepo;
    
    @Inject
    @Getter
    private CollectionCategoryMapper mapper;
}
