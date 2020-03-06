package org.appsec.securityrat.web.rest.vm;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * View Model object for storing the user's key and password.
 */
@Data
@NoArgsConstructor
public class KeyAndPasswordVM {
    // NOTE: Excluding the fields key and newPassword from the toString()
    //       implementation because otherwise sensitive information may be
    //       visible in stack traces or log files.
    
    @ToString.Exclude
    private String key;

    @ToString.Exclude
    private String newPassword;
}
