package org.appsec.securityrat.provider;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.appsec.securityrat.api.dto.AuthenticationConfigDto;
import org.appsec.securityrat.api.dto.AuthenticationConfigDto.Type;
import org.appsec.securityrat.api.dto.AuthorityDto;
import org.appsec.securityrat.api.dto.LoggerDto;
import org.appsec.securityrat.api.provider.SystemInfo;
import org.appsec.securityrat.config.ApplicationProperties;
import org.appsec.securityrat.config.ApplicationProperties.Authentication;
import org.appsec.securityrat.config.ApplicationProperties.Cas;
import org.appsec.securityrat.provider.mapper.AuthorityMapper;
import org.appsec.securityrat.provider.mapper.LoggerMapper;
import org.appsec.securityrat.repository.AuthorityRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SystemInfoImpl implements SystemInfo {
    @Inject
    private ApplicationProperties appProps;
    
    // Repositories
    
    @Inject
    private AuthorityRepository authorityRepo;
    
    // Mappers
    
    @Inject
    private AuthorityMapper authorityMapper;
    
    @Inject
    private LoggerMapper loggerMapper;
    
    @Override
    public List<AuthorityDto> getAuthorities() {
        return this.authorityRepo.findAll()
                .stream()
                .map(this.authorityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthenticationConfigDto getAuthenticationConfig() {
        Authentication auth = this.appProps.getAuthentication();
        Cas cas = this.appProps.getCas();
        
        AuthenticationConfigDto result = new AuthenticationConfigDto();
        
        switch (auth.getType()) {
            case CAS:
                result.setType(Type.CAS);
                break;
                
            case FORM:
                result.setType(Type.FORM);
                break;
                
            default:
                throw new UnsupportedOperationException(
                        "Authentication type not implemented: "
                                + auth.getType());
        }
        
        result.setRegistration(auth.isRegistration());
        result.setCasLogout(cas.getLogoutUrl().toExternalForm());
        
        return result;
    }

    @Override
    public List<LoggerDto> getLoggers() {
        LoggerContext ctx = (LoggerContext) LoggerFactory.getILoggerFactory();
        
        return ctx.getLoggerList()
                .stream()
                .map(this.loggerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateLogger(LoggerDto logger) {
        LoggerContext ctx = (LoggerContext) LoggerFactory.getILoggerFactory();
        
        ctx.getLogger(logger.getName())
                .setLevel(Level.valueOf(logger.getLevel()));
    }
}
