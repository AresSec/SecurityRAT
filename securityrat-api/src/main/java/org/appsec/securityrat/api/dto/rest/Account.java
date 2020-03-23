package org.appsec.securityrat.api.dto.rest;

import java.util.Optional;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.appsec.securityrat.api.dto.IdentifiableDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account implements IdentifiableDto<String> {
    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MAX_LENGTH = 100;
    public static final String PASSWORD_REGEX =
            "^(?=[\\w\\p{Punct}]*[a-z])(?=[\\w\\p{Punct}]*[A-Z])"
            + "(?=[\\w\\p{Punct}]*[\\d])(?=[\\w\\p{Punct}]*[\\p{Punct}])"
            + "[\\w\\p{Punct}]+$";
    
    @Pattern(regexp = "^[a-z0-9]*$")
    @NotNull
    @Size(min = 1, max = 50)
    private String login;
    
    // NOTE: 'password' may be 'null'
    
    @Pattern(regexp = PASSWORD_REGEX)
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;
    
    @Size(max = 50)
    private String firstName;
    
    @Size(max = 50)
    private String lastName;
    
    @Email
    @Size(min = 5, max = 100)
    private String email;
    
    @Size(min = 2, max = 5)
    private String langKey;

    private Set<String> roles;

    @Override
    public Optional<String> getId() {
        return Optional.ofNullable(this.login);
    }
}
