package org.appsec.securityrat.web.dto.importer;

import lombok.Data;
import org.appsec.securityrat.api.dto.Dto;

@Data
public class FrontendAttributeValueDto implements Dto {
    private String attributeIdentifier;
    private boolean reference;
    private String value;
    private boolean keyComponent;
}
