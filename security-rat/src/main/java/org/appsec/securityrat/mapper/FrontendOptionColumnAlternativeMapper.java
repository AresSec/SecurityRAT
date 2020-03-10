package org.appsec.securityrat.mapper;

import javax.inject.Inject;
import org.appsec.securityrat.api.dto.frontend.OptionColumnAlternative;
import org.appsec.securityrat.domain.OptColumn;
import org.springframework.stereotype.Service;

@Service
public class FrontendOptionColumnAlternativeMapper
        extends AbstractFrontendMapperBase<
            org.appsec.securityrat.domain.OptColumn,
            org.appsec.securityrat.api.dto.frontend.OptionColumnAlternative> {
    
    @Inject
    private FrontendAlternativeSetMapper frontendAlternativeSetMapper;

    @Override
    public OptionColumnAlternative toDto(OptColumn entity) {
        if (entity == null) {
            return null;
        }
        
        OptionColumnAlternative dto = new OptionColumnAlternative();
        
        dto.setId(entity.getId());
        dto.setAlternativeSets(this.frontendAlternativeSetMapper.toDtoSet(
                entity.getAlternativeSets()));
        
        return dto;
    }
}
