package org.appsec.securityrat.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingCategoryNode {
    private Long id;
    private String name;
    private TrainingTreeNode node;
    private ReqCategory category;
}
