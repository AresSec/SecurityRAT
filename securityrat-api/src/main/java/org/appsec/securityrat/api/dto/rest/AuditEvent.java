package org.appsec.securityrat.api.dto.rest;

import java.time.Instant;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditEvent {
    private Map<String, Object> data;
    private String principal;
    private Instant timestamp;
    private String type;
}
