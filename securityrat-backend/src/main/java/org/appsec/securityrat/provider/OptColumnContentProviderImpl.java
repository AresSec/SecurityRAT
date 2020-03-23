package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.dto.rest.OptColumnContent;
import org.appsec.securityrat.repository.OptColumnContentRepository;
import org.appsec.securityrat.repository.search.OptColumnContentSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class OptColumnContentProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.rest.OptColumnContent,
            org.appsec.securityrat.domain.OptColumnContent> {
    
    @Getter
    @Inject
    private OptColumnContentRepository repository;
    
    @Getter
    @Inject
    private OptColumnContentSearchRepository searchRepository;
    
    @Inject
    private OptColumnProviderImpl optColumns;
    
    @Inject
    private RequirementSkeletonProviderImpl requirementSkeletons;

    @Override
    protected OptColumnContent createDto(
            org.appsec.securityrat.domain.OptColumnContent entity) {
        OptColumnContent dto = new OptColumnContent();
        
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setOptColumn(this.optColumns.createDto(entity.getOptColumn()));
        
        dto.setRequirementSkeleton(this.requirementSkeletons.createDto(
                entity.getRequirementSkeleton()));
        
        return dto;
    }

    @Override
    protected org.appsec.securityrat.domain.OptColumnContent createOrUpdateEntity(
            OptColumnContent dto,
            org.appsec.securityrat.domain.OptColumnContent target) {
        if (target == null) {
            target = new org.appsec.securityrat.domain.OptColumnContent();
        }
        
        target.setContent(dto.getContent());
        
        target.setOptColumn(this.optColumns.findByDto(
                dto.getOptColumn()).get());
        
        target.setRequirementSkeleton(this.requirementSkeletons.findByDto(
                dto.getRequirementSkeleton()).get());
        
        return target;
    }

    @Override
    protected Long getId(
            org.appsec.securityrat.domain.OptColumnContent entity) {
        return entity.getId();
    }
}
