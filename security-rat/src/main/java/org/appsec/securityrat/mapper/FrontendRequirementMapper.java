package org.appsec.securityrat.mapper;

import java.util.stream.Collectors;
import javax.inject.Inject;
import org.appsec.securityrat.api.dto.frontend.Requirement;
import org.appsec.securityrat.domain.RequirementSkeleton;
import org.springframework.stereotype.Service;

@Service
public class FrontendRequirementMapper extends AbstractFrontendMapperBase<
        org.appsec.securityrat.domain.RequirementSkeleton,
        org.appsec.securityrat.api.dto.frontend.Requirement> {
    
    @Inject
    private FrontendOptionColumnContentMapper frontendOptionColumnContentMapper;

    @Override
    public Requirement toDto(RequirementSkeleton entity) {
        if (entity == null) {
            return null;
        }
        
        Requirement dto = new Requirement();
        
        dto.setId(entity.getId());
        dto.setShortName(entity.getShortName());
        dto.setUniversalId(entity.getUniversalId());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setOptionColumnContents(
                this.frontendOptionColumnContentMapper.toDtoSet(
                        entity.getOptColumnContents()));
        
        dto.setTagInstanceIds(
                entity.getTagInstances()
                        .stream()
                        .map(e -> e.getId())
                        .collect(Collectors.toSet()));
        
        return dto;
    }
}
