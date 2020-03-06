package org.appsec.securityrat.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqCategory {
    private Long id;
    private String name;
    private String shortcut;
    private String description;
    private Integer showOrder;
    private Boolean active;
}
