package org.appsec.securityrat.api.dto;

import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptColumn implements IdentifiableDto<Long> {
    @Getter(AccessLevel.NONE)
    private Long id;
    private String name;
    private String description;
    private Integer showOrder;
    private Boolean active;
    private Boolean isVisibleByDefault;
    private OptColumnType optColumnType;

    @Override
    public Optional<Long> getId() {
        return Optional.ofNullable(this.id);
    }
}
