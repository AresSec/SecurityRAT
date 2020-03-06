package org.appsec.securityrat.api.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptColumn {
    private Long id;
    private String name;
    private String description;
    private Integer showOrder;
    private Boolean active;
    private Boolean isVisibleByDefault;
    private OptColumnType optColumnType;
    private Set<AlternativeSet> alternativeSets;
    private Set<OptColumnContent> optColumnContents;
    private Set<ProjectType> projectTypes;
}
