package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.FrontendOptionColumnAlternativeProvider;
import org.appsec.securityrat.mapper.FrontendOptionColumnAlternativeMapper;
import org.appsec.securityrat.repository.OptColumnRepository;
import org.springframework.stereotype.Service;

@Service
public class FrontendOptionColumnAlternativeProviderImpl
        extends AbstractFrontendProviderImplementation<
            org.appsec.securityrat.api.dto.frontend.OptionColumnAlternative,
            org.appsec.securityrat.domain.OptColumn>
        implements FrontendOptionColumnAlternativeProvider {
    
    @Inject
    @Getter
    private OptColumnRepository repo;
    
    @Inject
    @Getter
    private FrontendOptionColumnAlternativeMapper mapper;
}
