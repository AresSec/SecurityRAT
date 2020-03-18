package org.appsec.securityrat.provider;

import java.util.stream.Collectors;
import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.RequirementSkeletonProvider;
import org.appsec.securityrat.api.dto.RequirementSkeleton;
import org.appsec.securityrat.repository.RequirementSkeletonRepository;
import org.appsec.securityrat.repository.search.RequirementSkeletonSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class RequirementSkeletonProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.RequirementSkeleton,
            org.appsec.securityrat.domain.RequirementSkeleton>
        implements RequirementSkeletonProvider {
    
    @Inject
    @Getter
    private RequirementSkeletonRepository repository;
    
    @Inject
    @Getter
    private RequirementSkeletonSearchRepository searchRepository;
    
    @Inject
    private ReqCategoryProviderImpl reqCategories;
    
    @Inject
    private TagInstanceProviderImpl tagInstances;
    
    @Inject
    private CollectionInstanceProviderImpl collectionInstances;
    
    @Inject
    private ProjectTypeProviderImpl projectTypes;

    @Override
    protected RequirementSkeleton createDto(
            org.appsec.securityrat.domain.RequirementSkeleton entity) {
        RequirementSkeleton dto = new RequirementSkeleton();
        
        dto.setId(entity.getId());
        dto.setUniversalId(entity.getUniversalId());
        dto.setShortName(entity.getShortName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setActive(entity.getActive());
        
        dto.setReqCategory(this.reqCategories.createDtoChecked(
                entity.getReqCategory()));
        
        dto.setTagInstances(entity.getTagInstances()
                .stream()
                .map(e -> this.tagInstances.createDtoChecked(e))
                .collect(Collectors.toSet()));
        
        dto.setCollectionInstances(entity.getCollectionInstances()
                .stream()
                .map(e -> this.collectionInstances.createDtoChecked(e))
                .collect(Collectors.toSet()));
        
        dto.setProjectTypes(entity.getProjectTypes()
                .stream()
                .map(e -> this.projectTypes.createDtoChecked(e))
                .collect(Collectors.toSet()));
        
        return dto;
    }

    @Override
    protected org.appsec.securityrat.domain.RequirementSkeleton createOrUpdateEntity(
            RequirementSkeleton dto,
            org.appsec.securityrat.domain.RequirementSkeleton target) {
        if (target == null) {
            target = new org.appsec.securityrat.domain.RequirementSkeleton();
        }
        
        target.setUniversalId(dto.getUniversalId());
        target.setShortName(dto.getShortName());
        target.setDescription(dto.getDescription());
        target.setShowOrder(dto.getShowOrder());
        target.setActive(dto.getActive());
        
        target.setReqCategory(this.reqCategories.findByDto(
                dto.getReqCategory()).get());
        
        target.setTagInstances(this.tagInstances.findSetByDto(
                dto.getTagInstances()));
        
        target.setCollectionInstances(this.collectionInstances.findSetByDto(
                dto.getCollectionInstances()));
        
        target.setProjectTypes(this.projectTypes.findSetByDto(
                dto.getProjectTypes()));
        
        return target;
    }

    @Override
    protected Long getId(
            org.appsec.securityrat.domain.RequirementSkeleton entity) {
        return entity.getId();
    }
}
