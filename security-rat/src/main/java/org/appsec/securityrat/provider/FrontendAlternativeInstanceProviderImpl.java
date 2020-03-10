package org.appsec.securityrat.provider;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.FrontendAlternativeInstanceProvider;
import org.appsec.securityrat.api.dto.frontend.AlternativeInstance;
import org.appsec.securityrat.mapper.FrontendAlternativeInstanceMapper;
import org.appsec.securityrat.repository.AlternativeInstanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FrontendAlternativeInstanceProviderImpl
        extends AbstractFrontendProviderImplementation<
            org.appsec.securityrat.api.dto.frontend.AlternativeInstance,
            org.appsec.securityrat.domain.AlternativeInstance>
        implements FrontendAlternativeInstanceProvider {
    
    @Getter
    @Inject
    private AlternativeInstanceRepository repo;
    
    @Getter
    @Inject
    private FrontendAlternativeInstanceMapper mapper;

    @Override
    @Transactional
    public List<AlternativeInstance> getActiveAlternativeInstancesForAlternativeSet(
            Long alternativeSetId) {
        // TODO: Preformance is improvable.
        
        return this.mapper.toDtoList(
                this.repo.findAll()
                        .stream()
                        .filter(e -> Objects.equals(
                                e.getAlternativeSet().getId(),
                                alternativeSetId))
                        .collect(Collectors.toList()));
    }
}
