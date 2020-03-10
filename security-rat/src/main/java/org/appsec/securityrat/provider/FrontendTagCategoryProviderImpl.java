package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.FrontendTagCategoryProvider;
import org.appsec.securityrat.mapper.FrontendTagCategoryMapper;
import org.appsec.securityrat.repository.TagCategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class FrontendTagCategoryProviderImpl
        extends AbstractFrontendProviderImplementation<
            org.appsec.securityrat.api.dto.frontend.TagCategory,
            org.appsec.securityrat.domain.TagCategory>
        implements FrontendTagCategoryProvider {
    @Getter
    @Inject
    private TagCategoryRepository repo;
    
    @Getter
    @Inject
    private FrontendTagCategoryMapper mapper;
}
