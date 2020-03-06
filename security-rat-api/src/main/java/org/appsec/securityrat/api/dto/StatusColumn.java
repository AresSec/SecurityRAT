package org.appsec.securityrat.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusColumn {
    private Long id;
    private String name;
    private String description;
    private Boolean isEnum;
    private Integer showOrder;
    private Boolean active;
}
