package org.appsec.securityrat.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersistentToken {
    private String formattedTokenDate;
    private String ipAddress;
    private String series;
    private String userAgent;
}
