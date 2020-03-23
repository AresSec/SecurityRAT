package org.appsec.securityrat.api.dto.rest;

import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.appsec.securityrat.api.dto.IdentifiableDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativeSet implements IdentifiableDto<Long> {
    @Getter(AccessLevel.NONE)
    private Long id;
    private String name;
    private String description;
    private Integer showOrder;
    private Boolean active;
    private OptColumn optColumn;
    private Set<AlternativeInstance> alternativeInstances;

    @Override
    public Optional<Long> getId() {
        return Optional.ofNullable(this.id);
    }
}
