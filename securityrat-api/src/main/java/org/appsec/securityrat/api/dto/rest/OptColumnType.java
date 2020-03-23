package org.appsec.securityrat.api.dto.rest;

import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.appsec.securityrat.api.dto.IdentifiableDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptColumnType implements IdentifiableDto<Long> {
    @Getter(AccessLevel.NONE)
    private Long id;
    private String name;
    private String description;

    @Override
    public Optional<Long> getId() {
        return Optional.ofNullable(this.id);
    }
}
