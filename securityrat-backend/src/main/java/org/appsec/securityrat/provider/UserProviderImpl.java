package org.appsec.securityrat.provider;

import io.github.jhipster.security.RandomUtil;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.AuthoritiesConstants;
import org.appsec.securityrat.api.UserProvider;
import org.appsec.securityrat.api.dto.User;
import org.appsec.securityrat.api.dto.rest.Authority;
import org.appsec.securityrat.api.dto.rest.PersistentToken;
import org.appsec.securityrat.api.exception.ApiException;
import org.appsec.securityrat.api.exception.EmailAlreadyInUseException;
import org.appsec.securityrat.api.exception.NotActivatedException;
import org.appsec.securityrat.api.exception.UnknownEmailException;
import org.appsec.securityrat.api.exception.UnknownIdentifierException;
import org.appsec.securityrat.api.exception.UsernameTakenException;
import org.appsec.securityrat.config.ApplicationProperties;
import org.appsec.securityrat.config.ApplicationProperties.AuthenticationType;
import org.appsec.securityrat.repository.UserRepository;
import org.appsec.securityrat.repository.search.UserSearchRepository;
import org.appsec.securityrat.service.MailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProviderImpl
        extends AbstractProviderImplementation<
            Long,
            org.appsec.securityrat.api.dto.User,
            org.appsec.securityrat.domain.User>
        implements UserProvider {
    
    // NOTE: Due to the reason that the frontend will directly display the date
    //       that is formatted with the DateTimeFormatter bellow, we should
    //       always use ENGLISH as the locale because otherwise the translation
    //       of the month's name will depend on the server's language
    //       configuration (which is not really that nice).
    
    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);
    
    // TODO [luis.felger@bosch.com]: Implement cronjob from
    // https://github.com/SecurityRAT/SecurityRAT/blob/master/src/main/java/org/appsec/securityRAT/service/UserService.java
    
    @Getter
    @Inject
    private UserRepository repository;
    
    @Getter
    @Inject
    private UserSearchRepository searchRepository;
    
    @Inject
    private PasswordEncoder passwordEncoder;
    
    @Inject
    private ApplicationProperties appSettings;
    
    @Inject
    private MailService mails;

    @Override
    protected org.appsec.securityrat.domain.User createOrUpdateEntity(
            User dto,
            org.appsec.securityrat.domain.User target) throws ApiException {
        if (target == null) {
            target = new org.appsec.securityrat.domain.User();
            
            // By default, all users have the frontend role
            
            org.appsec.securityrat.domain.Authority authority =
                    new org.appsec.securityrat.domain.Authority();
            
            authority.setName(AuthoritiesConstants.FRONTEND_USER);
            target.setAuthorities(Collections.singleton(authority));
            
            // Depending on the authentication settings, the user may need to
            // activate their account first.
            
            switch (this.appSettings.getAuthentication().getType()) {
                case CAS:
                    target.setActivated(true);
                    break;
                    
                case FORM:
                    target.setActivated(false);
                    target.setActivationKey(RandomUtil.generateActivationKey());
                    break;
                    
                default:
                    throw new UnsupportedOperationException(
                            "Authentication type not supported!");
            }
        }
        
        // Checking, if the username or email is already in use (only if the
        // updated information differ from the persistent one)
        
        if (!Objects.equals(target.getEmail(), dto.getEmail()) &&
                this.repository.findOneByEmail(dto.getEmail()).isPresent()) {
            throw new EmailAlreadyInUseException();
        }
        
        if (!Objects.equals(target.getLogin(), dto.getLogin()) &&
                this.repository.findOneByLogin(dto.getLogin()).isPresent()) {
            throw new UsernameTakenException();
        }
        
        // Updating the information
        
        target.setLogin(dto.getLogin());
        target.setFirstName(dto.getFirstName());
        target.setLastName(dto.getLastName());
        target.setEmail(dto.getEmail());
        target.setLangKey(dto.getLangKey());
        target.setAuthorities(dto.getAuthorities()
                .stream()
                .map(e -> {
                    org.appsec.securityrat.domain.Authority authority =
                            new org.appsec.securityrat.domain.Authority();                    
                    
                    authority.setName(e.getName());
                    
                    return authority;
                })
                .collect(Collectors.toSet()));
        
        // If provided, we update the password
        
        if (dto.getPassword() != null) {
            target.setPassword(this.passwordEncoder.encode(dto.getPassword()));
        }
        
        return target;
    }

    @Override
    protected Long getId(org.appsec.securityrat.domain.User entity) {
        return entity.getId();
    }

    @Override
    protected User createDto(org.appsec.securityrat.domain.User entity) {
        User dto = new User();
        
        dto.setId(entity.getId());
        dto.setLogin(entity.getLogin());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setActivated(entity.getActivated());
        dto.setLangKey(entity.getLangKey());
        dto.setResetKey(entity.getResetKey());
        dto.setResetDate(entity.getResetDate());
        dto.setAuthorities(entity.getAuthorities()
                .stream()
                .map(e -> {
                    Authority authority = new Authority();
                    authority.setName(e.getName());
                    return authority;
                })
                .collect(Collectors.toSet()));
        
        dto.setPersistentTokens(entity.getPersistentTokens()
                .stream()
                .map(e -> {
                    PersistentToken token = new PersistentToken();
                    
                    token.setFormattedTokenDate(e.getTokenDate().format(
                            UserProviderImpl.FORMAT));
                    
                    token.setIpAddress(e.getIpAddress());
                    token.setSeries(e.getSeries());
                    token.setUserAgent(e.getUserAgent());
                    
                    return token;
                })
                .collect(Collectors.toSet()));
        
        return dto;
    }

    @Override
    @Transactional
    public Optional<User> findByLogin(String login) {
        return this.repository.findOneByLogin(login)
                .map(this::createDto);
    }

    @Override
    @Transactional
    public boolean activate(String activationKey) throws ApiException {
        Optional<org.appsec.securityrat.domain.User> nullableUser =
                this.repository.findOneByActivationKey(activationKey);
        
        if (nullableUser.isEmpty()) {
            return false;
        }
        
        org.appsec.securityrat.domain.User user = nullableUser.get();
        boolean result = false;
        
        if (!user.getActivated()) {
            user.setActivated(true);
            
            // We only return "true", if the user really got activated and was
            // not already activated before.
            
            result = true;
        }
        
        user.setActivationKey(null);
        this.doUpdate(user);
        
        return result;
    }

    @Override
    @Transactional
    public boolean confirmPassword(Long userId, String password)
            throws ApiException, UnknownIdentifierException {
        Optional<org.appsec.securityrat.domain.User> nullableUser =
                this.repository.findOneById(userId);
        
        if (nullableUser.isEmpty()) {
            throw new UnknownIdentifierException(
                    "userId is unknown: " + userId);
        }
        
        return this.passwordEncoder.matches(
                password,
                nullableUser.get().getPassword());
    }

    @Override
    @Transactional
    public boolean invalidateToken(Long userId, String series)
            throws ApiException, UnknownIdentifierException {
        Optional<org.appsec.securityrat.domain.User> nullableUser =
                this.repository.findOneById(userId);
        
        if (nullableUser.isEmpty()) {
            throw new UnknownIdentifierException(
                    "userId is unknown: " + userId);
        }
        
        org.appsec.securityrat.domain.User user = nullableUser.get();
        
        Optional<org.appsec.securityrat.domain.PersistentToken> nullableToken =
                user.getPersistentTokens()
                        .stream()
                        .filter(token -> Objects.equals(
                                token.getSeries(),
                                series))
                        .findAny();
        
        if (nullableToken.isEmpty()) {
            return false;
        }
        
        user.getPersistentTokens().remove(nullableToken.get());
        this.doUpdate(user);
        
        return true;
    }

    @Override
    @Transactional
    public void requestPasswordReset(String email)
            throws ApiException, UnknownEmailException, NotActivatedException {
        Optional<org.appsec.securityrat.domain.User> nullableUser =
                this.repository.findOneByEmail(email);
        
        if (nullableUser.isEmpty()) {
            throw new UnknownEmailException();
        }
        
        org.appsec.securityrat.domain.User user = nullableUser.get();
        
        if (!user.getActivated()) {
            throw new NotActivatedException();
        }
        
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        this.doUpdate(user);
        
        this.mails.sendPasswordResetMail(user);
    }

    @Override
    @Transactional
    public boolean finishPasswordReset(String resetKey, String password)
            throws ApiException {
        Optional<org.appsec.securityrat.domain.User> nullableUser =
                this.repository.findOneByResetKey(resetKey);
        
        if (nullableUser.isEmpty()) {
            return false;
        }
        
        org.appsec.securityrat.domain.User user = nullableUser.get();
        
        // Resetting a password needs to be completed within 24 hours
        
        boolean inTime = user.getResetDate()
                .isAfter(Instant.now().minus(24, ChronoUnit.HOURS));
        
        if (inTime) {
            user.setPassword(this.passwordEncoder.encode(password));
        }
        
        user.setResetKey(null);
        user.setResetDate(null);
        this.doUpdate(user);
        
        return inTime;
    }
    
    @Override
    protected void onCreated(org.appsec.securityrat.domain.User entity) {
        if (this.appSettings.getAuthentication()
                .getType() != AuthenticationType.FORM) {
            return;
        }
        
        this.mails.sendActivationMail(entity);
    }
}
