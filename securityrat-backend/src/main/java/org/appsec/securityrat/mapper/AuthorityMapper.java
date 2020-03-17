package org.appsec.securityrat.mapper;

import org.appsec.securityrat.api.dto.Authority;
import org.springframework.stereotype.Service;

@Service
public class AuthorityMapper extends AbstractMapperBase<
        org.appsec.securityrat.domain.Authority,
        org.appsec.securityrat.api.dto.Authority> {

    @Override
    public Authority toDto(org.appsec.securityrat.domain.Authority entity) {
        if (entity == null) {
            return null;
        }
        
        Authority dto = new Authority();
        dto.setName(entity.getName());
        return dto;
    }

    @Override
    public org.appsec.securityrat.domain.Authority toEntity(Authority dto) {
        if (dto == null) {
            return null;
        }
        
        org.appsec.securityrat.domain.Authority entity =
                new org.appsec.securityrat.domain.Authority();
        
        entity.setName(dto.getName());
        
        return entity;
    }
}
