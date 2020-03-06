package org.appsec.securityrat.api.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectType {
    private Long id;
    private String name;
    private String description;
    private Integer showOrder;
    private Boolean active;
    private Set<StatusColumn> statusColumns;
    private Set<OptColumn> optColumns;
}
