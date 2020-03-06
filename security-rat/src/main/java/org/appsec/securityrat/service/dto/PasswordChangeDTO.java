package org.appsec.securityrat.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * A DTO representing a password change required data - current and new password.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeDTO {
    // NOTE: We exclude the fields currentPassword and newPassword from the
    //       toString() method because otherwise sensitive data may be visible
    //       in stack traces or the log file.
    
    @ToString.Exclude
    private String currentPassword;
    
    @ToString.Exclude
    private String newPassword;
}
