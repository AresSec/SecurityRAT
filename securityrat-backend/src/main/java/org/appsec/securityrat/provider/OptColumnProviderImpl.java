package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.OptColumnProvider;
import org.appsec.securityrat.mapper.OptColumnMapper;
import org.appsec.securityrat.repository.OptColumnRepository;
import org.appsec.securityrat.repository.search.OptColumnSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class OptColumnProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.OptColumn,
            org.appsec.securityrat.domain.OptColumn>
        implements OptColumnProvider {
    
    @Inject
    @Getter
    private OptColumnRepository repo;
    
    @Inject
    @Getter
    private OptColumnSearchRepository searchRepo;
    
    @Inject
    @Getter
    private OptColumnMapper mapper;
}
