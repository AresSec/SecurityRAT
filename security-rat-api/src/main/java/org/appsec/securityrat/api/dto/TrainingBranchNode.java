package org.appsec.securityrat.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingBranchNode {
    private Long id;
    private String name;
    private Integer anchor;
    private TrainingTreeNode node;
}
