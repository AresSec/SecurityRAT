package org.appsec.securityrat.api.dto;

import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigConstant implements IdentifiableDto<Long> {
    @Getter(AccessLevel.NONE)
    private Long id;
    private String name;
    private String value;
    private String description;

    @Override
    public Optional<Long> getId() {
        return Optional.ofNullable(this.id);
    }
}
