package org.appsec.securityrat.api.dto.rest;

import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.appsec.securityrat.api.dto.IdentifiableDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingRequirementNode implements IdentifiableDto<Long> {
    @Getter(AccessLevel.NONE)
    private Long id;
    private TrainingTreeNode node;
    private RequirementSkeleton requirementSkeleton;

    @Override
    public Optional<Long> getId() {
        return Optional.ofNullable(this.id);
    }
}
