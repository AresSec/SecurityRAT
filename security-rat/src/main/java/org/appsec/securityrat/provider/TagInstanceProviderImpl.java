package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.TagInstanceProvider;
import org.appsec.securityrat.mapper.TagInstanceMapper;
import org.appsec.securityrat.repository.TagInstanceRepository;
import org.appsec.securityrat.repository.search.TagInstanceSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class TagInstanceProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.TagInstance,
            org.appsec.securityrat.domain.TagInstance>
        implements TagInstanceProvider {
    
    @Getter
    @Inject
    private TagInstanceRepository repo;
    
    @Getter
    @Inject
    private TagInstanceSearchRepository searchRepo;
    
    @Getter
    @Inject
    private TagInstanceMapper mapper;
}
