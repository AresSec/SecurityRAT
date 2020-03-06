package org.appsec.securityrat.api.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Training {
    private Long id;
    private String name;
    private String description;
    private Boolean allRequirementsSelected;
    private Set<OptColumn> optColumns;
    private Set<CollectionInstance> collections;
    private Set<ProjectType> projectTypes;
}
