package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.ReqCategoryProvider;
import org.appsec.securityrat.api.dto.ReqCategory;
import org.appsec.securityrat.repository.ReqCategoryRepository;
import org.appsec.securityrat.repository.search.ReqCategorySearchRepository;
import org.springframework.stereotype.Service;

@Service
public class ReqCategoryProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.ReqCategory,
            org.appsec.securityrat.domain.ReqCategory>
        implements ReqCategoryProvider {
    
    @Getter
    @Inject
    private ReqCategoryRepository repository;
    
    @Getter
    @Inject
    private ReqCategorySearchRepository searchRepository;

    @Override
    protected ReqCategory createDto(
            org.appsec.securityrat.domain.ReqCategory entity) {
        ReqCategory dto = new ReqCategory();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setShortcut(entity.getShortcut());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setActive(entity.getActive());
        
        return dto;
    }

    @Override
    protected org.appsec.securityrat.domain.ReqCategory createOrUpdateEntity(
            ReqCategory dto,
            org.appsec.securityrat.domain.ReqCategory target) {
        if (target == null) {
            target = new org.appsec.securityrat.domain.ReqCategory();
        }
        
        target.setName(dto.getName());
        target.setShortcut(dto.getShortcut());
        target.setDescription(dto.getDescription());
        target.setShowOrder(dto.getShowOrder());
        target.setActive(dto.getActive());
        
        return target;
    }

    @Override
    protected Long getId(org.appsec.securityrat.domain.ReqCategory entity) {
        return entity.getId();
    }
}
