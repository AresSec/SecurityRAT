package org.appsec.securityrat.mapper;

import java.util.stream.Collectors;
import javax.inject.Inject;
import org.appsec.securityrat.api.dto.Account;
import org.appsec.securityrat.domain.Authority;
import org.appsec.securityrat.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountMapper extends AbstractFrontendMapperBase<User, Account> {
    @Inject
    private PasswordEncoder passwordEncoder;
    
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
        
        // NOTE: There is no dedicated mapper (Authority -> String).
        
        dto.setRoles(entity.getAuthorities()
                .stream()
                .map(e -> e.getName())
                .collect(Collectors.toSet()));
        
        return dto;
    }
    
    public User toEntity(Account dto, User entity) {
        if (dto == null) {
            return null;
        }
        
        // NOTE: We do not support updating the username, also if it would be
        //       possible.
        
        if (dto.getPassword() != null) {
            // We only update the password, if it was specified.
            
            entity.setPassword(this.passwordEncoder.encode(dto.getPassword()));
        }
        
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setLangKey(dto.getLangKey());
        
        // NOTE: There is no dedicated mapper (String -> Authority).
        
        if (dto.getRoles() != null) {
            entity.setAuthorities(
                    dto.getRoles()
                            .stream()
                            .map(r -> {
                                Authority authority = new Authority();
                                authority.setName(r);
                                return authority;
                            })
                            .collect(Collectors.toSet()));
        }
        
        return entity;
    }
}
