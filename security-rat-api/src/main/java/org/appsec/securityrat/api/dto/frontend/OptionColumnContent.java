package org.appsec.securityrat.api.dto.frontend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionColumnContent {
    private Long id;
    private Long optionColumnId;
    private String content;
    private String optionColumnName;
}
