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
public class StatusColumnValue implements IdentifiableDto<Long> {
    @Getter(AccessLevel.NONE)
    private Long id;
    private String name;
    private String description;
    private Integer showOrder;
    private Boolean active;
    private StatusColumn statusColumn;

    @Override
    public Optional<Long> getId() {
        return Optional.ofNullable(this.id);
    }
}
