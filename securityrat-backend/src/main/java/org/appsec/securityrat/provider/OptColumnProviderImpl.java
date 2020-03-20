package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.dto.OptColumn;
import org.appsec.securityrat.repository.OptColumnRepository;
import org.appsec.securityrat.repository.search.OptColumnSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class OptColumnProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.OptColumn,
            org.appsec.securityrat.domain.OptColumn> {
    
    @Inject
    @Getter
    private OptColumnRepository repository;
    
    @Inject
    @Getter
    private OptColumnSearchRepository searchRepository;
    
    @Inject
    private OptColumnTypeProviderImpl optColumnTypes;

    @Override
    protected OptColumn createDto(
            org.appsec.securityrat.domain.OptColumn entity) {
        OptColumn dto = new OptColumn();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShowOrder(entity.getShowOrder());
        dto.setActive(entity.getActive());
        dto.setIsVisibleByDefault(entity.getIsVisibleByDefault());
        dto.setOptColumnType(this.optColumnTypes.createDto(
                entity.getOptColumnType()));
        
        return dto;
    }

    @Override
    protected org.appsec.securityrat.domain.OptColumn createOrUpdateEntity(
            OptColumn dto,
            org.appsec.securityrat.domain.OptColumn target) {
        if (target == null) {
            target = new org.appsec.securityrat.domain.OptColumn();
        }
        
        target.setName(dto.getName());
        target.setDescription(dto.getDescription());
        target.setShowOrder(dto.getShowOrder());
        target.setActive(dto.getActive());
        target.setIsVisibleByDefault(dto.getIsVisibleByDefault());
        target.setOptColumnType(this.optColumnTypes.findByDto(
                dto.getOptColumnType()).get());
        
        return target;
    }

    @Override
    protected Long getId(org.appsec.securityrat.domain.OptColumn entity) {
        return entity.getId();
    }
}
