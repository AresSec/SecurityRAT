package org.appsec.securityrat.mapper;

import org.appsec.securityrat.api.dto.frontend.OptionColumnContent;
import org.appsec.securityrat.domain.OptColumnContent;
import org.springframework.stereotype.Service;

@Service
public class FrontendOptionColumnContentMapper
        extends AbstractFrontendMapperBase<
            org.appsec.securityrat.domain.OptColumnContent,
        org.appsec.securityrat.api.dto.frontend.OptionColumnContent> {

    @Override
    public OptionColumnContent toDto(OptColumnContent entity) {
        if (entity == null) {
            return null;
        }
        
        OptionColumnContent dto = new OptionColumnContent();
        
        dto.setId(entity.getId());
        dto.setOptionColumnId(entity.getOptColumn().getId());
        dto.setContent(entity.getContent());
        dto.setOptionColumnName(entity.getOptColumn().getName());
        
        return dto;
    }
}
