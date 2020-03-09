package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.OptColumnTypeProvider;
import org.appsec.securityrat.mapper.OptColumnTypeMapper;
import org.appsec.securityrat.repository.OptColumnTypeRepository;
import org.appsec.securityrat.repository.search.OptColumnTypeSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class OptColumnTypeProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.OptColumnType,
            org.appsec.securityrat.domain.OptColumnType>
        implements OptColumnTypeProvider {
    
    @Inject
    @Getter
    private OptColumnTypeRepository repo;
    
    @Inject
    @Getter
    private OptColumnTypeSearchRepository searchRepo;
    
    @Inject
    @Getter
    private OptColumnTypeMapper mapper;
}
