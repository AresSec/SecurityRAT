package org.appsec.securityrat.api.dto;

import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingGeneratedSlideNode implements IdentifiableDto<Long> {
    @Getter(AccessLevel.NONE)
    private Long id;
    private TrainingTreeNode node;
    private OptColumn optColumn;

    @Override
    public Optional<Long> getId() {
        return Optional.ofNullable(this.id);
    }
}
