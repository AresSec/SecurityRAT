package org.appsec.securityrat.api;

import org.appsec.securityrat.api.dto.rest.AuthenticationConfiguration;

public interface AuthenticationProvider {
    AuthenticationConfiguration getConfiguration();
    
    /**
     * Returns the name of the user that is authenticated at the moment.
     * 
     * @return Either the name of the authenticated user or <code>null</code>,
     *         if the current user was not authenticated.
     */
    String getCurrentUser();
}
