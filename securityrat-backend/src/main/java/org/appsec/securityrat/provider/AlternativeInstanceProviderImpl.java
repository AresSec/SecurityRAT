package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.dto.rest.AlternativeInstance;
import org.appsec.securityrat.repository.AlternativeInstanceRepository;
import org.appsec.securityrat.repository.search.AlternativeInstanceSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class AlternativeInstanceProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.rest.AlternativeInstance,
            org.appsec.securityrat.domain.AlternativeInstance> {
    
    @Inject
    @Getter
    private AlternativeInstanceRepository repository;
    
    @Inject
    @Getter
    private AlternativeInstanceSearchRepository searchRepository;
    
    @Inject
    private AlternativeSetProviderImpl alternativeSets;
    
    @Inject
    private RequirementSkeletonProviderImpl requirementSkeletons;

    @Override
    protected AlternativeInstance createDto(
            org.appsec.securityrat.domain.AlternativeInstance entity) {
        AlternativeInstance dto = new AlternativeInstance();
        
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setAlternativeSet(this.alternativeSets.createDtoChecked(
                entity.getAlternativeSet()));
        
        dto.setRequirementSkeleton(this.requirementSkeletons.createDtoChecked(
                entity.getRequirementSkeleton()));
        
        return dto;
    }

    @Override
    protected org.appsec.securityrat.domain.AlternativeInstance createOrUpdateEntity(
            AlternativeInstance dto,
            org.appsec.securityrat.domain.AlternativeInstance target) {
        if (target == null) {
            target = new org.appsec.securityrat.domain.AlternativeInstance();
        }
        
        target.setContent(dto.getContent());
        target.setAlternativeSet(this.alternativeSets.findByDto(
                dto.getAlternativeSet()).get());
        
        target.setRequirementSkeleton(this.requirementSkeletons.findByDto(
                dto.getRequirementSkeleton()).get());
        
        return target;
    }

    @Override
    protected Long getId(
            org.appsec.securityrat.domain.AlternativeInstance entity) {
        return entity.getId();
    }
}
