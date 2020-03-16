package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.RequirementSkeletonProvider;
import org.appsec.securityrat.mapper.RequirementSkeletonMapper;
import org.appsec.securityrat.repository.RequirementSkeletonRepository;
import org.appsec.securityrat.repository.search.RequirementSkeletonSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class RequirementSkeletonProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.RequirementSkeleton,
            org.appsec.securityrat.domain.RequirementSkeleton>
        implements RequirementSkeletonProvider {
    
    @Inject
    @Getter
    private RequirementSkeletonRepository repo;
    
    @Inject
    @Getter
    private RequirementSkeletonSearchRepository searchRepo;
    
    @Inject
    @Getter
    private RequirementSkeletonMapper mapper;
}
