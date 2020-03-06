package org.appsec.securityrat.api.rest;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.UserServiceProvider;
import org.appsec.securityrat.api.dto.AuthenticationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class AuthenticationResource {
    @Inject
    private UserServiceProvider userService;
    
    @GetMapping("/authentication_config")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationConfiguration getAuthenticationConfig() {
        return this.userService.getAuthenticationConfiguration();
    }
    
    @GetMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public String get() {
        log.warn("Not implemented");
        return "";
    }
}
