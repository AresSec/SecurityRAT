package org.appsec.securityrat.mapper;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.appsec.securityrat.api.dto.PersistentToken;
import org.springframework.stereotype.Service;

@Service
public class PersistentTokenMapper extends AbstractMapperBase<
        org.appsec.securityrat.domain.PersistentToken,
        org.appsec.securityrat.api.dto.PersistentToken> {
    // NOTE: Due to the reason that the frontend will directly display the date
    //       that is formatted with the DateTimeFormatter bellow, we should
    //       always use ENGLISH as the locale because otherwise the translation
    //       of the month's name will depend on the server's language
    //       configuration (which is not really that nice).
    
    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);
    

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
