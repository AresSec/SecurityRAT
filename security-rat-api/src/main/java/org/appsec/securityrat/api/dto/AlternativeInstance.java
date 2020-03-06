package org.appsec.securityrat.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlternativeInstance {
    private Long id;
    private String content;
    private AlternativeSet alternativeSet;
    private RequirementSkeleton requirementSkeleton;
}
