package org.appsec.securityrat.provider;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.FrontendAlternativeInstanceProvider;
import org.appsec.securityrat.api.dto.frontend.AlternativeInstance;
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
    private AlternativeInstanceRepository repository;

    @Override
    @Transactional
    public List<AlternativeInstance> getActiveAlternativeInstancesForAlternativeSet(
            Long alternativeSetId) {
        // TODO: Preformance is improvable.
        
        return this.repository.findAll()
                .stream()
                .filter(e -> Objects.equals(
                        e.getAlternativeSet().getId(),
                        alternativeSetId))
                .map(this::createDto)
                .collect(Collectors.toList());
    }

    @Override
    protected AlternativeInstance createDto(
            org.appsec.securityrat.domain.AlternativeInstance entity) {
        AlternativeInstance dto = new AlternativeInstance();
        
        dto.setId(entity.getId());
        dto.setRequirementId(entity.getRequirementSkeleton().getId());
        dto.setContent(entity.getContent());
        
        return dto;
    }
}
