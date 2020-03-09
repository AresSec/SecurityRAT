package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.TagCategoryProvider;
import org.appsec.securityrat.mapper.TagCategoryMapper;
import org.appsec.securityrat.repository.TagCategoryRepository;
import org.appsec.securityrat.repository.search.TagCategorySearchRepository;
import org.springframework.stereotype.Service;

@Service
public class TagCategoryProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.TagCategory,
            org.appsec.securityrat.domain.TagCategory>
        implements TagCategoryProvider {
    
    @Inject
    @Getter
    private TagCategoryRepository repo;
    
    @Inject
    @Getter
    private TagCategorySearchRepository searchRepo;
    
    @Inject
    @Getter
    private TagCategoryMapper mapper;
}
