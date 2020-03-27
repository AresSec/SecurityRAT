package org.appsec.securityrat.api.dto.user;

import java.time.Instant;
import java.util.Set;
import lombok.Data;
import org.appsec.securityrat.api.dto.AuthorityDto;
import org.appsec.securityrat.api.dto.IdentifiableDto;

@Data
public class UserDto implements IdentifiableDto<Long> {
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private boolean activated;
    private String langKey;
    private String resetKey;
    private Instant resetDate;
    private Set<AuthorityDto> authorities;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long identifier) {
        this.id = identifier;
    }

    @Override
    public Class<Long> getIdentifierClass() {
        return Long.class;
    }
}
