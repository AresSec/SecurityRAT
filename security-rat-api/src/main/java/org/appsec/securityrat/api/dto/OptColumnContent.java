package org.appsec.securityrat.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptColumnContent {
    private Long id;
    private String content;
    private OptColumn optColumn;
    private RequirementSkeleton requirementSkeleton;
}
