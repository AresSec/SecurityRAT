package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.ConfigConstantProvider;
import org.appsec.securityrat.api.dto.ConfigConstant;
import org.appsec.securityrat.repository.ConfigConstantRepository;
import org.appsec.securityrat.repository.search.ConfigConstantSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class ConfigConstantProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.ConfigConstant,
            org.appsec.securityrat.domain.ConfigConstant>
        implements ConfigConstantProvider {
    
    @Inject
    @Getter
    private ConfigConstantRepository repository;
    
    @Inject
    @Getter
    private ConfigConstantSearchRepository searchRepository;

    @Override
    protected ConfigConstant createDto(
            org.appsec.securityrat.domain.ConfigConstant entity) {
        ConfigConstant dto = new ConfigConstant();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setValue(entity.getValue());
        dto.setDescription(entity.getDescription());
        
        return dto;
    }

    @Override
    protected org.appsec.securityrat.domain.ConfigConstant createOrUpdateEntity(
            ConfigConstant dto,
            org.appsec.securityrat.domain.ConfigConstant target) {
        if (target == null) {
            target = new org.appsec.securityrat.domain.ConfigConstant();
        }
        
        target.setName(dto.getName());
        target.setValue(dto.getValue());
        target.setDescription(dto.getDescription());
        
        return target;
    }

    @Override
    protected Long getId(org.appsec.securityrat.domain.ConfigConstant entity) {
        return entity.getId();
    }
}
