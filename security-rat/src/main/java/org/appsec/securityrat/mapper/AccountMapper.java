package org.appsec.securityrat.mapper;

import java.util.stream.Collectors;
import org.appsec.securityrat.api.dto.Account;
import org.appsec.securityrat.domain.User;
import org.springframework.stereotype.Service;

@Service
public class AccountMapper extends AbstractFrontendMapperBase<User, Account> {
    @Override
    public Account toDto(User entity) {
        if (entity == null) {
            return null;
        }
        
        Account dto = new Account();
        
        dto.setLogin(entity.getLogin());
        dto.setPassword(null);
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setLangKey(entity.getLangKey());
        dto.setRoles(entity.getAuthorities()
                .stream()
                .map(e -> e.toString())
                .collect(Collectors.toSet()));
        
        return dto;
    }
    
    // TODO: DTO -> Entity is also required.
}
