package org.appsec.securityrat.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigConstant {
    private Long id;
    private String name;
    private String value;
    private String description;
}
