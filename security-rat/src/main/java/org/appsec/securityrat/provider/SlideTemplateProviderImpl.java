package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.SlideTemplateProvider;
import org.appsec.securityrat.mapper.SlideTemplateMapper;
import org.appsec.securityrat.repository.SlideTemplateRepository;
import org.appsec.securityrat.repository.search.SlideTemplateSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class SlideTemplateProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.SlideTemplate,
            org.appsec.securityrat.domain.SlideTemplate>
        implements SlideTemplateProvider {
    
    @Inject
    @Getter
    private SlideTemplateRepository repo;
    
    @Inject
    @Getter
    private SlideTemplateSearchRepository searchRepo;
    
    @Inject
    @Getter
    private SlideTemplateMapper mapper;
}
