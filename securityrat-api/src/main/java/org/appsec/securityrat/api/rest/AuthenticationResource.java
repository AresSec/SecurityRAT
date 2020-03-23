package org.appsec.securityrat.api.rest;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.AuthenticationProvider;
import org.appsec.securityrat.api.dto.rest.AuthenticationConfiguration;
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
    private AuthenticationProvider provider;
    
    @GetMapping("/authentication_config")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationConfiguration getAuthenticationConfig() {
        return this.provider.getConfiguration();
    }
    
    @GetMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public String get(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }
}
