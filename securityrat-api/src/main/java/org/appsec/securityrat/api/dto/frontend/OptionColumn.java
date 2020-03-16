package org.appsec.securityrat.api.dto.frontend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionColumn {
    private Long id;
    private String name;
    private String description;
    private Integer showOrder;
    private String type;
    private boolean isVisibleByDefault;
}
