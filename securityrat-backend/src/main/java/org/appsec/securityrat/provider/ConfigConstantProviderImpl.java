package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.ConfigConstantProvider;
import org.appsec.securityrat.mapper.ConfigConstantMapper;
import org.appsec.securityrat.repository.ConfigConstantRepository;
import org.appsec.securityrat.repository.search.ConfigConstantSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class ConfigConstantProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.ConfigConstant,
            org.appsec.securityrat.domain.ConfigConstant>
        implements ConfigConstantProvider {
    
    @Inject
    @Getter
    private ConfigConstantRepository repo;
    
    @Inject
    @Getter
    private ConfigConstantSearchRepository searchRepo;
    
    @Inject
    @Getter
    private ConfigConstantMapper mapper;
}
