package org.appsec.securityrat.web.dto.importer;

import java.util.Set;
import lombok.Data;
import org.appsec.securityrat.api.dto.Dto;

@Data
public class FrontendObjectDto implements Dto {
    private String identifier;
    private String typeIdentifier;
    private Set<FrontendAttributeValueDto> attributes;
    private FrontendReplaceRule replaceRule;
}
