package org.appsec.securityrat.provider;

import java.util.stream.Collectors;
import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.dto.frontend.OptionColumn;
import org.appsec.securityrat.api.dto.frontend.StatusColumn;
import org.appsec.securityrat.api.dto.frontend.StatusColumnValue;
import org.appsec.securityrat.domain.ProjectType;
import org.appsec.securityrat.repository.ProjectTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class FrontendProjectTypeProviderImpl
        extends AbstractFrontendProviderImplementation<
            org.appsec.securityrat.api.dto.frontend.ProjectType,
            org.appsec.securityrat.domain.ProjectType> {
    
    @Getter
    @Inject
    private ProjectTypeRepository repository;
    
    @Override
    protected org.appsec.securityrat.api.dto.frontend.ProjectType createDto(
            ProjectType entity) {
        org.appsec.securityrat.api.dto.frontend.ProjectType dto =
                new org.appsec.securityrat.api.dto.frontend.ProjectType();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        
        // NOTE: There are no dedicated providers for OptionColumn, StatusColumn
        //       and StatusColumnValue.
        
        dto.setOptionColumns(entity.getOptColumns()
                .stream()
                .map(e -> {
                    OptionColumn col = new OptionColumn();
                    
                    col.setId(e.getId());
                    col.setName(e.getName());
                    col.setDescription(e.getDescription());
                    col.setShowOrder(e.getShowOrder());
                    col.setType(e.getOptColumnType().getName());
                    col.setVisibleByDefault(e.getIsVisibleByDefault());
                    
                    return col;
                })
                .collect(Collectors.toSet()));
        
        dto.setStatusColumns(entity.getStatusColumns()
                .stream()
                .map(e -> {
                    StatusColumn col = new StatusColumn();
                    
                    col.setId(e.getId());
                    col.setName(e.getName());
                    col.setDescription(e.getDescription());
                    col.setShowOrder(e.getShowOrder());
                    col.setIsEnum(e.getIsEnum());
                    col.setValues(e.getStatusColumnValues()
                            .stream()
                            .map(f -> {
                                StatusColumnValue val = new StatusColumnValue();
                                
                                val.setId(f.getId());
                                val.setName(f.getName());
                                val.setDescription(f.getDescription());
                                val.setShowOrder(f.getShowOrder());
                                
                                return val;
                            })
                            .collect(Collectors.toSet()));
                    
                    return col;
                })
                .collect(Collectors.toSet()));
        
        return dto;
    }
}
