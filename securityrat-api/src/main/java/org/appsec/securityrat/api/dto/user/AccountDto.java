package org.appsec.securityrat.api.dto.user;

import java.util.Set;
import lombok.Data;
import org.appsec.securityrat.api.dto.Dto;

@Data
public class AccountDto implements Dto {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String langKey;
    private Set<String> roles;
}
