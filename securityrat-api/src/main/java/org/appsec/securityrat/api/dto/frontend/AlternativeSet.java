package org.appsec.securityrat.api.dto.frontend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlternativeSet {
    private Long id;
    private String name;
    private String description;
    private Integer showOrder;
}
