package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.ReqCategoryProvider;
import org.appsec.securityrat.mapper.ReqCategoryMapper;
import org.appsec.securityrat.repository.ReqCategoryRepository;
import org.appsec.securityrat.repository.search.ReqCategorySearchRepository;
import org.springframework.stereotype.Service;

@Service
public class ReqCategoryProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.ReqCategory,
            org.appsec.securityrat.domain.ReqCategory>
        implements ReqCategoryProvider {
    
    @Getter
    @Inject
    private ReqCategoryRepository repo;
    
    @Getter
    @Inject
    private ReqCategorySearchRepository searchRepo;
    
    @Getter
    @Inject
    private ReqCategoryMapper mapper;
}
