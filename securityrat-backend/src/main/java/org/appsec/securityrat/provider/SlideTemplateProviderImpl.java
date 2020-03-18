package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.SlideTemplateProvider;
import org.appsec.securityrat.api.dto.SlideTemplate;
import org.appsec.securityrat.repository.SlideTemplateRepository;
import org.appsec.securityrat.repository.search.SlideTemplateSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class SlideTemplateProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.SlideTemplate,
            org.appsec.securityrat.domain.SlideTemplate>
        implements SlideTemplateProvider {
    
    @Inject
    @Getter
    private SlideTemplateRepository repository;
    
    @Inject
    @Getter
    private SlideTemplateSearchRepository searchRepository;

    @Override
    protected SlideTemplate createDto(
            org.appsec.securityrat.domain.SlideTemplate entity) {
        SlideTemplate dto = new SlideTemplate();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        
        return dto;
    }

    @Override
    protected org.appsec.securityrat.domain.SlideTemplate createOrUpdateEntity(
            SlideTemplate dto,
            org.appsec.securityrat.domain.SlideTemplate target) {
        if (target == null) {
            target = new org.appsec.securityrat.domain.SlideTemplate();
        }
        
        target.setName(dto.getName());
        target.setDescription(dto.getDescription());
        target.setContent(dto.getContent());
        
        return target;
    }

    @Override
    protected Long getId(org.appsec.securityrat.domain.SlideTemplate entity) {
        return entity.getId();
    }
}
