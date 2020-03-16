package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.StatusColumnProvider;
import org.appsec.securityrat.mapper.StatusColumnMapper;
import org.appsec.securityrat.repository.StatusColumnRepository;
import org.appsec.securityrat.repository.search.StatusColumnSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class StatusColumnProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.StatusColumn,
            org.appsec.securityrat.domain.StatusColumn>
        implements StatusColumnProvider {
    @Getter
    @Inject
    private StatusColumnRepository repo;
    
    @Getter
    @Inject
    private StatusColumnSearchRepository searchRepo;
    
    @Getter
    @Inject
    private StatusColumnMapper mapper;
}
