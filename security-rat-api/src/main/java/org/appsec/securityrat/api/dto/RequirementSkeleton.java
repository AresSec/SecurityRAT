package org.appsec.securityrat.api.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequirementSkeleton {
    private Long id;
    private String universalId;
    private String shortName;
    private String description;
    private Integer showOrder;
    private Boolean active;
    private ReqCategory reqCategory;
    private Set<TagInstance> tagInstances;
    private Set<CollectionInstance> collectionInstances;
    private Set<ProjectType> projectTypes;
}
