package org.appsec.securityrat.api.dto;

import java.time.Instant;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.appsec.securityrat.api.dto.Authority;
import org.appsec.securityrat.api.dto.PersistentToken;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
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
}
