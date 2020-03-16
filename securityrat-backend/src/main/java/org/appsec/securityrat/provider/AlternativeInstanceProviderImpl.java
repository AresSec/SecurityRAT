package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.AlternativeInstanceProvider;
import org.appsec.securityrat.mapper.AlternativeInstanceMapper;
import org.appsec.securityrat.repository.AlternativeInstanceRepository;
import org.appsec.securityrat.repository.search.AlternativeInstanceSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class AlternativeInstanceProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.AlternativeInstance,
            org.appsec.securityrat.domain.AlternativeInstance>
        implements AlternativeInstanceProvider {
    
    @Inject
    @Getter
    private AlternativeInstanceRepository repo;
    
    @Inject
    @Getter
    private AlternativeInstanceSearchRepository searchRepo;
    
    @Inject
    @Getter
    private AlternativeInstanceMapper mapper;
}
