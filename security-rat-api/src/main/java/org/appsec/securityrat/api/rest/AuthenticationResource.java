package org.appsec.securityrat.api.rest;

import lombok.extern.slf4j.Slf4j;
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
    @GetMapping("/authentication_config")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationConfiguration getAuthenticationConfig() {
        log.warn("Not implemented");
        return null;
    }
    
    @GetMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public String get() {
        log.warn("Not implemented");
        return "";
    }
}
