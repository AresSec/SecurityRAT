package org.appsec.securityrat.api.dto.frontend;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Requirement {
    private Long id;
    private String shortName;
    private String universalId;
    private String description;
    private Integer showOrder;
    private Set<OptionColumnContent> optionColumnContents;
    private Set<Long> tagInstanceIds;
}
