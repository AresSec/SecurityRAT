package org.appsec.securityrat.api.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptColumnType {
    private Long id;
    private String name;
    private String description;
    private Set<OptColumn> optColumns;
}
