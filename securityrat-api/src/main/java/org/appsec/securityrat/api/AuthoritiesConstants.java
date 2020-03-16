package org.appsec.securityrat.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthoritiesConstants {
    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    
    public static final String FRONTEND_USER = "ROLE_FRONTEND_USER";
    
    public static final String TRAINER = "ROLE_TRAINER";
}
