package org.appsec.securityrat.api;

import org.appsec.securityrat.api.dto.AuthenticationConfiguration;

public interface UserServiceProvider {
    AuthenticationConfiguration getAuthenticationConfiguration();
}
