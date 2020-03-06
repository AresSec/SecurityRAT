package org.appsec.securityrat.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusColumnValue {
    private Long id;
    private String name;
    private String description;
    private Integer showOrder;
    private Boolean active;
    private StatusColumn statusColumn;
}
