package org.appsec.securityrat.provider;

import java.util.stream.Collectors;
import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.dto.frontend.AlternativeSet;
import org.appsec.securityrat.api.dto.frontend.OptionColumnAlternative;
import org.appsec.securityrat.domain.OptColumn;
import org.appsec.securityrat.repository.OptColumnRepository;
import org.springframework.stereotype.Service;

@Service
public class FrontendOptionColumnAlternativeProviderImpl
        extends AbstractFrontendProviderImplementation<
            org.appsec.securityrat.api.dto.frontend.OptionColumnAlternative,
            org.appsec.securityrat.domain.OptColumn> {
    
    @Inject
    @Getter
    private OptColumnRepository repository;

    @Override
    protected OptionColumnAlternative createDto(OptColumn entity) {
        OptionColumnAlternative dto = new OptionColumnAlternative();
        
        dto.setId(entity.getId());
        
        // NOTE: There is no dedicated provider for AlternativeSet.
        
        dto.setAlternativeSets(entity.getAlternativeSets()
                .stream()
                .map(e -> {
                    AlternativeSet set = new AlternativeSet();
                    
                    set.setId(e.getId());
                    set.setName(e.getName());
                    set.setDescription(e.getDescription());
                    set.setShowOrder(e.getShowOrder());
                    
                    return set;
                })
                .collect(Collectors.toSet()));
        
        return dto;
    }
}
