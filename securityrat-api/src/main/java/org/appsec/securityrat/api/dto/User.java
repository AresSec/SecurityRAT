package org.appsec.securityrat.api.dto;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements IdentifiableDto<Long> {
    @Getter(AccessLevel.NONE)
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private boolean activated;
    private String langKey;
    private String resetKey;
    private Instant resetDate;
    private Set<Authority> authorities;
    private Set<PersistentToken> persistentTokens;

    @Override
    public Optional<Long> getId() {
        return Optional.ofNullable(this.id);
    }
}
