package org.appsec.securityrat.api.dto.rest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetKeyAndPassword {
    @NotBlank
    private String key;
    
    @ToString.Exclude
    @Pattern(regexp = Account.PASSWORD_REGEX)
    @Size(min = Account.PASSWORD_MIN_LENGTH, max = Account.PASSWORD_MAX_LENGTH)
    @NotBlank
    private String newPassword;
}
