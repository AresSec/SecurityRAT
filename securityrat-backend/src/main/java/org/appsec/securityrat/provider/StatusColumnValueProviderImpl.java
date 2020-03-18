package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.StatusColumnValueProvider;
import org.appsec.securityrat.api.dto.StatusColumnValue;
import org.appsec.securityrat.repository.StatusColumnValueRepository;
import org.appsec.securityrat.repository.search.StatusColumnValueSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class StatusColumnValueProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.StatusColumnValue,
            org.appsec.securityrat.domain.StatusColumnValue>
        implements StatusColumnValueProvider {
    @Inject
    @Getter
    private StatusColumnValueRepository repository;
    
    @Inject
    @Getter
    private StatusColumnValueSearchRepository searchRepository;
    
    @Inject
    private StatusColumnProviderImpl statusColumns;

    @Override
    protected StatusColumnValue createDto(
            org.appsec.securityrat.domain.StatusColumnValue entity) {
        StatusColumnValue dto = new StatusColumnValue();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setActive(entity.getActive());
        dto.setStatusColumn(this.statusColumns.createDtoChecked(
                entity.getStatusColumn()));
        
        return dto;
    }

    @Override
    protected org.appsec.securityrat.domain.StatusColumnValue createOrUpdateEntity(
            StatusColumnValue dto,
            org.appsec.securityrat.domain.StatusColumnValue target) {
        if (target == null) {
            target = new org.appsec.securityrat.domain.StatusColumnValue();
        }
        
        target.setName(dto.getName());
        target.setDescription(dto.getDescription());
        target.setShowOrder(dto.getShowOrder());
        target.setActive(dto.getActive());
        target.setStatusColumn(this.statusColumns.findByDto(
                dto.getStatusColumn()).get());
        
        return target;
    }

    @Override
    protected Long getId(
            org.appsec.securityrat.domain.StatusColumnValue entity) {
        return entity.getId();
    }
}
