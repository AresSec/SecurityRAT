package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.AlternativeSetProvider;
import org.appsec.securityrat.mapper.AlternativeSetMapper;
import org.appsec.securityrat.repository.AlternativeSetRepository;
import org.appsec.securityrat.repository.search.AlternativeSetSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class AlternativeSetProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.AlternativeSet,
            org.appsec.securityrat.domain.AlternativeSet>
        implements AlternativeSetProvider {
    @Inject
    @Getter
    private AlternativeSetRepository repo;
    
    @Inject
    @Getter
    private AlternativeSetSearchRepository searchRepo;

    @Inject
    @Getter
    private AlternativeSetMapper mapper;
}
