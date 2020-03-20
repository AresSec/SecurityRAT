package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.dto.StatusColumn;
import org.appsec.securityrat.repository.StatusColumnRepository;
import org.appsec.securityrat.repository.search.StatusColumnSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class StatusColumnProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.StatusColumn,
            org.appsec.securityrat.domain.StatusColumn> {
    
    @Getter
    @Inject
    private StatusColumnRepository repository;
    
    @Getter
    @Inject
    private StatusColumnSearchRepository searchRepository;

    @Override
    protected StatusColumn createDto(
            org.appsec.securityrat.domain.StatusColumn entity) {
        StatusColumn dto = new StatusColumn();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setIsEnum(entity.getIsEnum());
        dto.setShowOrder(entity.getShowOrder());
        dto.setActive(entity.getActive());
        
        return dto;
    }

    @Override
    protected org.appsec.securityrat.domain.StatusColumn createOrUpdateEntity(
            StatusColumn dto,
            org.appsec.securityrat.domain.StatusColumn target) {
        if (target == null) {
            target = new org.appsec.securityrat.domain.StatusColumn();
        }
        
        target.setName(dto.getName());
        target.setDescription(dto.getDescription());
        target.setIsEnum(dto.getIsEnum());
        target.setShowOrder(dto.getShowOrder());
        target.setActive(dto.getActive());
        
        return target;
    }

    @Override
    protected Long getId(org.appsec.securityrat.domain.StatusColumn entity) {
        return entity.getId();
    }
}
