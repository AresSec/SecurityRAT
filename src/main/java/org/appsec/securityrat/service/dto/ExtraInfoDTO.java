package org.appsec.securityrat.service.dto;

import java.net.URL;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.appsec.securityrat.config.AuthenticationType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtraInfoDTO {
    private AuthenticationType type;
    private boolean registration;
    private URL casLogout;
}
