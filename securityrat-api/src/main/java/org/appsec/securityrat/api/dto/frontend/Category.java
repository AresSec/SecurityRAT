package org.appsec.securityrat.api.dto.frontend;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private Long id;
    private String name;
    private String description;
    private String shortcut;
    private Integer showOrder;
    private Set<Requirement> requirements;
}
