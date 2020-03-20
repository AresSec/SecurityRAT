package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.dto.OptColumnType;
import org.appsec.securityrat.repository.OptColumnTypeRepository;
import org.appsec.securityrat.repository.search.OptColumnTypeSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class OptColumnTypeProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.OptColumnType,
            org.appsec.securityrat.domain.OptColumnType> {
    
    @Inject
    @Getter
    private OptColumnTypeRepository repository;
    
    @Inject
    @Getter
    private OptColumnTypeSearchRepository searchRepository;

    @Override
    protected OptColumnType createDto(
            org.appsec.securityrat.domain.OptColumnType entity) {
        OptColumnType dto = new OptColumnType();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        
        return dto;
    }

    @Override
    protected org.appsec.securityrat.domain.OptColumnType createOrUpdateEntity(
            OptColumnType dto,
            org.appsec.securityrat.domain.OptColumnType target) {
        if (target == null) {
            target = new org.appsec.securityrat.domain.OptColumnType();
        }
        
        target.setName(dto.getName());
        target.setDescription(dto.getDescription());
        
        return target;
    }

    @Override
    protected Long getId(org.appsec.securityrat.domain.OptColumnType entity) {
        return entity.getId();
    }
}
