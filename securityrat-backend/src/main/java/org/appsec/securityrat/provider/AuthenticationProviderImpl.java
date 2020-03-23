package org.appsec.securityrat.provider;

import javax.inject.Inject;
import org.appsec.securityrat.api.AuthenticationProvider;
import org.appsec.securityrat.api.dto.rest.AuthenticationConfiguration;
import org.appsec.securityrat.api.dto.rest.AuthenticationType;
import org.appsec.securityrat.config.ApplicationProperties;
import org.appsec.securityrat.security.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationProviderImpl implements AuthenticationProvider {
    @Inject
    private ApplicationProperties appSettings;
    
    @Override
    public AuthenticationConfiguration getConfiguration() {
        return new AuthenticationConfiguration(
                AuthenticationType.valueOf(
                        this.appSettings.getAuthentication().getType().name()),
                this.appSettings.getAuthentication().isRegistration(),
                this.appSettings.getCas().getLogoutUrl().toExternalForm());
    }

    @Override
    public String getCurrentUser() {
        return SecurityUtils.getCurrentUserLogin()
                .orElse(null);
    }
}
