package org.appsec.securityrat.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingRequirementNode {
    private Long id;
    private TrainingTreeNode node;
    private RequirementSkeleton requirementSkeleton;
}
