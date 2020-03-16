package org.appsec.securityrat.api.dto.frontend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlternativeInstance {
    private Long id;
    private Long requirementId;
    private String content;
}
