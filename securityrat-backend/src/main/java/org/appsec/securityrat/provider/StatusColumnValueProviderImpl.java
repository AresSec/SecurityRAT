package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.StatusColumnValueProvider;
import org.appsec.securityrat.mapper.StatusColumnValueMapper;
import org.appsec.securityrat.repository.StatusColumnValueRepository;
import org.appsec.securityrat.repository.search.StatusColumnValueSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class StatusColumnValueProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.StatusColumnValue,
            org.appsec.securityrat.domain.StatusColumnValue>
        implements StatusColumnValueProvider {
    @Inject
    @Getter
    private StatusColumnValueRepository repo;
    
    @Inject
    @Getter
    private StatusColumnValueSearchRepository searchRepo;
    
    @Inject
    @Getter
    private StatusColumnValueMapper mapper;
}
