package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.FrontendProjectTypeProvider;
import org.appsec.securityrat.mapper.FrontendProjectTypeMapper;
import org.appsec.securityrat.repository.ProjectTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class FrontendProjectTypeProviderImpl
        extends AbstractFrontendProviderImplementation<
            org.appsec.securityrat.api.dto.frontend.ProjectType,
            org.appsec.securityrat.domain.ProjectType>
        implements FrontendProjectTypeProvider {
    
    @Getter
    @Inject
    private ProjectTypeRepository repo;
    
    @Getter
    @Inject
    private FrontendProjectTypeMapper mapper;
}
