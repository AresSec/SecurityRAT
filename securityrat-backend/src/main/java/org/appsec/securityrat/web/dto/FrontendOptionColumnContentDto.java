package org.appsec.securityrat.web.dto;

import lombok.Data;
import org.appsec.securityrat.api.dto.Dto;

@Data
public class FrontendOptionColumnContentDto implements Dto {
    private Long id;
    private Long optionColumnId;
    private String content;
    private String optionColumnName;
}
