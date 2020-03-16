package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.OptColumnContentProvider;
import org.appsec.securityrat.mapper.OptColumnContentMapper;
import org.appsec.securityrat.repository.OptColumnContentRepository;
import org.appsec.securityrat.repository.search.OptColumnContentSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class OptColumnContentProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.OptColumnContent,
            org.appsec.securityrat.domain.OptColumnContent>
        implements OptColumnContentProvider {
    
    @Getter
    @Inject
    private OptColumnContentRepository repo;
    
    @Getter
    @Inject
    private OptColumnContentSearchRepository searchRepo;
    
    @Getter
    @Inject
    private OptColumnContentMapper mapper;
}
