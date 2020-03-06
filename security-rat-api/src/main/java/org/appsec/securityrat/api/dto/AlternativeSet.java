package org.appsec.securityrat.api.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativeSet {
    private Long id;
    private String name;
    private String description;
    private Integer showOrder;
    private Boolean active;
    private OptColumn optColumn;
    private Set<AlternativeInstance> alternativeInstances;
}
