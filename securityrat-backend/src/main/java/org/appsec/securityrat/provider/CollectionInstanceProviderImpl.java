package org.appsec.securityrat.provider;

import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.CollectionInstanceProvider;
import org.appsec.securityrat.api.dto.CollectionInstance;
import org.appsec.securityrat.mapper.CollectionInstanceMapper;
import org.appsec.securityrat.repository.CollectionInstanceRepository;
import org.appsec.securityrat.repository.search.CollectionInstanceSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class CollectionInstanceProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.CollectionInstance,
            org.appsec.securityrat.domain.CollectionInstance>
        implements CollectionInstanceProvider {
    
    @Getter
    @Inject
    private CollectionInstanceRepository repo;
    
    @Getter
    @Inject
    private CollectionInstanceSearchRepository searchRepo;
    
    @Getter
    @Inject
    private CollectionInstanceMapper mapper;
}
