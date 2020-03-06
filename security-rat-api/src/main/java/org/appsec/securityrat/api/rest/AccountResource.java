package org.appsec.securityrat.api.rest;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.appsec.securityrat.api.MailServiceProvider;
import org.appsec.securityrat.api.PersistentTokenRepositoryProvider;
import org.appsec.securityrat.api.UserRepositoryProvider;
import org.appsec.securityrat.api.UserServiceProvider;
import org.appsec.securityrat.api.dto.AuthenticationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api")
@Slf4j
public class AccountResource {
    @Inject
    private UserRepositoryProvider userRepository;
    
    @Inject
    private UserServiceProvider userService;
    
    @Inject
    private PersistentTokenRepositoryProvider persistentTokenRepository;
    
    @Inject
    private MailServiceProvider mailService;
    
    @GetMapping("/authentication_config")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationConfiguration getAuthenticationConfiguration() {
        return this.userService.getAuthenticationConfiguration();
    }
}
