package org.appsec.securityrat.provider;

import java.util.stream.Collectors;
import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.dto.AlternativeSet;
import org.appsec.securityrat.repository.AlternativeSetRepository;
import org.appsec.securityrat.repository.search.AlternativeSetSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class AlternativeSetProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.AlternativeSet,
            org.appsec.securityrat.domain.AlternativeSet> {
    
    @Inject
    @Getter
    private AlternativeSetRepository repository;
    
    @Inject
    @Getter
    private AlternativeSetSearchRepository searchRepository;
    
    @Inject
    private OptColumnProviderImpl optColumns;
    
    @Inject
    private AlternativeInstanceProviderImpl alternativeInstances;

    @Override
    protected AlternativeSet createDto(
            org.appsec.securityrat.domain.AlternativeSet entity) {
        AlternativeSet dto = new AlternativeSet();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setActive(entity.getActive());
        dto.setOptColumn(this.optColumns.createDto(entity.getOptColumn()));
        
        dto.setAlternativeInstances(entity.getAlternativeInstances()
                .stream()
                .map(e -> this.alternativeInstances.createDto(e))
                .collect(Collectors.toSet()));
        
        return dto;
    }

    @Override
    protected org.appsec.securityrat.domain.AlternativeSet createOrUpdateEntity(
            AlternativeSet dto,
            org.appsec.securityrat.domain.AlternativeSet target) {
        if (target == null) {
            target = new org.appsec.securityrat.domain.AlternativeSet();
        }
        
        target.setName(dto.getName());
        target.setDescription(dto.getDescription());
        target.setShowOrder(dto.getShowOrder());
        target.setActive(dto.getActive());
        
        target.setOptColumn(this.optColumns.findByDto(
                dto.getOptColumn()).get());
        
        target.setAlternativeInstances(this.alternativeInstances.findSetByDto(
                dto.getAlternativeInstances()));
        
        return target;
    }

    @Override
    protected Long getId(org.appsec.securityrat.domain.AlternativeSet entity) {
        return entity.getId();
    }
}
