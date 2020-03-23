package org.appsec.securityrat.provider;

import java.util.stream.Collectors;
import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.dto.rest.ProjectType;
import org.appsec.securityrat.repository.ProjectTypeRepository;
import org.appsec.securityrat.repository.search.ProjectTypeSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectTypeProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.rest.ProjectType,
            org.appsec.securityrat.domain.ProjectType> {
    
    @Inject
    @Getter
    private ProjectTypeRepository repository;
    
    @Inject
    @Getter
    private ProjectTypeSearchRepository searchRepository;
    
    @Inject
    private StatusColumnProviderImpl statusColumns;
    
    @Inject
    private OptColumnProviderImpl optColumns;

    @Override
    protected ProjectType createDto(
            org.appsec.securityrat.domain.ProjectType entity) {
        ProjectType dto = new ProjectType();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setActive(entity.getActive());
        
        dto.setStatusColumns(entity.getStatusColumns()
                .stream()
                .map(e -> this.statusColumns.createDto(e))
                .collect(Collectors.toSet()));
        
        dto.setOptColumns(entity.getOptColumns()
                .stream()
                .map(e -> this.optColumns.createDto(e))
                .collect(Collectors.toSet()));
        
        return dto;
    }

    @Override
    protected org.appsec.securityrat.domain.ProjectType createOrUpdateEntity(
            ProjectType dto,
            org.appsec.securityrat.domain.ProjectType target) {
        if (target == null) {
            target = new org.appsec.securityrat.domain.ProjectType();
        }
        
        target.setName(dto.getName());
        target.setDescription(dto.getDescription());
        target.setShowOrder(dto.getShowOrder());
        target.setActive(dto.getActive());
        target.setStatusColumns(this.statusColumns.findSetByDto(
                dto.getStatusColumns()));
        
        target.setOptColumns(this.optColumns.findSetByDto(dto.getOptColumns()));
        
        return target;
    }

    @Override
    protected Long getId(org.appsec.securityrat.domain.ProjectType entity) {
        return entity.getId();
    }
}
