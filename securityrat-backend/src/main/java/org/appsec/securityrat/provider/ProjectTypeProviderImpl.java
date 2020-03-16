package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.ProjectTypeProvider;
import org.appsec.securityrat.mapper.ProjectTypeMapper;
import org.appsec.securityrat.repository.ProjectTypeRepository;
import org.appsec.securityrat.repository.search.ProjectTypeSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectTypeProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.ProjectType,
            org.appsec.securityrat.domain.ProjectType>
        implements ProjectTypeProvider {
    
    @Inject
    @Getter
    private ProjectTypeRepository repo;
    
    @Inject
    @Getter
    private ProjectTypeSearchRepository searchRepo;
    
    @Inject
    @Getter
    private ProjectTypeMapper mapper;
}
