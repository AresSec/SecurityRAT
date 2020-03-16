package org.appsec.securityrat.mapper;

import java.time.format.DateTimeFormatter;
import org.appsec.securityrat.api.dto.PersistentToken;
import org.springframework.stereotype.Service;

@Service
public class PersistentTokenMapper extends AbstractMapperBase<
        org.appsec.securityrat.domain.PersistentToken,
        org.appsec.securityrat.api.dto.PersistentToken> {
    private static DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("d MMMM yyyy");
    

    @Override
    public PersistentToken toDto(
            org.appsec.securityrat.domain.PersistentToken entity) {
        
        if (entity == null) {
            return null;
        }
        
        PersistentToken dto = new PersistentToken();
        
        dto.setFormattedTokenDate(entity.getTokenDate().format(
                PersistentTokenMapper.FORMAT));
        
        dto.setIpAddress(entity.getIpAddress());
        dto.setSeries(entity.getSeries());
        dto.setUserAgent(entity.getUserAgent());
        
        return dto;
    }

    @Override
    public org.appsec.securityrat.domain.PersistentToken toEntity(
            PersistentToken dto) {
        // NOTE: Since instances of the "PersistentToken" type are only created
        //       and saved at the login (Spring does that for us), it is NOT
        //       supported to convert PersistentToken DTOs back to entities.
        
        throw new UnsupportedOperationException();
    }
}
