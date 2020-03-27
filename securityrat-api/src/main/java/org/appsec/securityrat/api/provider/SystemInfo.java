package org.appsec.securityrat.api.provider;

import java.util.List;
import org.appsec.securityrat.api.dto.AuthenticationConfigDto;
import org.appsec.securityrat.api.dto.AuthorityDto;

public interface SystemInfo {
    /**
     * Returns all authorities that exist and may be assigned to a user.
     * 
     * @return All authority instances
     */
    List<AuthorityDto> getAuthorities();
    
    /**
     * Returns the current authentication configuration.
     * 
     * @return The authentication configuration.
     */
    AuthenticationConfigDto getAuthenticationConfig();
}
