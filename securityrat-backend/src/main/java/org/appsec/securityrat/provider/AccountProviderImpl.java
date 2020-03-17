package org.appsec.securityrat.provider;

import io.github.jhipster.security.RandomUtil;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Inject;
import org.appsec.securityrat.api.AccountProvider;
import org.appsec.securityrat.api.AuthoritiesConstants;
import org.appsec.securityrat.api.EmailAlreadyInUseException;
import org.appsec.securityrat.api.NotActivatedException;
import org.appsec.securityrat.api.UnauthorizedContextException;
import org.appsec.securityrat.api.UnknownEmailException;
import org.appsec.securityrat.api.UsernameTakenException;
import org.appsec.securityrat.api.dto.Account;
import org.appsec.securityrat.api.dto.PersistentToken;
import org.appsec.securityrat.config.ApplicationProperties;
import org.appsec.securityrat.domain.Authority;
import org.appsec.securityrat.domain.User;
import org.appsec.securityrat.mapper.AccountMapper;
import org.appsec.securityrat.mapper.PersistentTokenMapper;
import org.appsec.securityrat.repository.UserRepository;
import org.appsec.securityrat.repository.search.UserSearchRepository;
import org.appsec.securityrat.security.SecurityUtils;
import org.appsec.securityrat.service.MailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountProviderImpl implements AccountProvider {
    @Inject
    private UserRepository users;
    
    @Inject
    private UserSearchRepository userSearch;
    
    @Inject
    private AccountMapper mapper;
    
    @Inject
    private PersistentTokenMapper tokenMapper;
    
    @Inject
    private PasswordEncoder passwordEncoder;
    
    @Inject
    private ApplicationProperties appSettings;
    
    @Inject
    private MailService mails;

    // TODO [luis.felger@bosch.com]: Implement cronjob from
    // https://github.com/SecurityRAT/SecurityRAT/blob/master/src/main/java/org/appsec/securityRAT/service/UserService.java

    @Override
    @Transactional
    public Account save(Account account)
            throws EmailAlreadyInUseException, UnauthorizedContextException {
        
        // Before we do anything else, we should always clear the roles
        // property. Otherwise, a user may be able to change their own roles.
        
        account.setRoles(null);
        
        User user = this.getCurrentUser();
        
        if (!Objects.equals(user.getLogin(), account.getLogin())) {
            throw new UnauthorizedContextException("Attempted to modify "
                    + "sombody else's account information");
        }
        
        user = this.mapper.toEntity(account, user);
        this.save(user);
        
        return this.mapper.toDto(user);
    }

    @Override
    @Transactional
    public Account create(Account account)
            throws UsernameTakenException, EmailAlreadyInUseException {
        
        // Before we do anything else, we should always clear the roles
        // property. Otherwise, a user may be able to change their own roles.
        
        account.setRoles(null);
        
        Optional<User> checkUser =
                this.users.findOneByEmail(account.getEmail());
        
        if (checkUser.isPresent()) {
            throw new EmailAlreadyInUseException();
        }
        
        checkUser = this.users.findOneByLogin(account.getLogin());
        
        if (checkUser.isPresent()) {
            throw new UsernameTakenException();
        }
        
        User createdUser = this.mapper.toEntity(account, new User());
        
        // Depending on the authentication settings the user may need to
        // activate their account first.
        
        ApplicationProperties.AuthenticationType authType =
                this.appSettings.getAuthentication().getType();
        
        switch (authType) {
            case CAS:
                createdUser.setActivated(true);
                createdUser.setActivationKey(null);
                break;
            case FORM:
                createdUser.setActivated(false);
                createdUser.setActivationKey(
                        RandomUtil.generateActivationKey());
                break;
            default:
                throw new UnsupportedOperationException(
                        "Authentication type not supported: " + authType);
        }
        
        // By default, all users have the frontend role
        
        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.FRONTEND_USER);
        createdUser.setAuthorities(Collections.singleton(authority));
        
        this.save(createdUser);
        
        if (authType == ApplicationProperties.AuthenticationType.FORM) {
            this.mails.sendActivationMail(createdUser);
        }
        
        // Sending the activation mail
        
        return this.mapper.toDto(createdUser);
    }

    @Override
    @Transactional
    public boolean activate(String activationKey) {
        Optional<User> nullableUser =
                this.users.findOneByActivationKey(activationKey);
        
        if (nullableUser.isEmpty()) {
            return false;
        }
        
        User user = nullableUser.get();
        boolean result = false;
        
        if (!user.getActivated()) {
            user.setActivated(true);
            
            // We only return "true", if the user really got activated and was
            // not already activated before.
            
            result = true;
        }
        
        user.setActivationKey(null);
        this.save(user);
        
        return result;
    }

    @Override
    @Transactional
    public Account getCurrent() throws UnauthorizedContextException {
        return this.mapper.toDto(this.getCurrentUser());
    }

    @Override
    @Transactional
    public boolean confirmPassword(String password)
            throws UnauthorizedContextException {
        
        return this.passwordEncoder.matches(
                password,
                this.getCurrentUser().getPassword());
    }

    @Override
    @Transactional
    public List<PersistentToken> getCurrentTokens()
            throws UnauthorizedContextException {
        
        return this.tokenMapper.toDtoList(new ArrayList<>(
                this.getCurrentUser().getPersistentTokens()));
    }

    @Override
    @Transactional
    public boolean invalidateToken(String series)
            throws UnauthorizedContextException {
        
        User user = this.getCurrentUser();
        Optional<org.appsec.securityrat.domain.PersistentToken> nullableToken =
                user.getPersistentTokens()
                        .stream()
                        .filter(t -> Objects.equals(series, t.getSeries()))
                        .findAny();
        
        if (nullableToken.isEmpty()) {
            return false;
        }
        
        this.getCurrentUser()
                .getPersistentTokens()
                .remove(nullableToken.get());
        
        this.save(user);

        return true;
    }

    @Override
    public void requestPasswordReset(String email)
            throws UnknownEmailException, NotActivatedException {
        
        Optional<User> nullableUser = this.users.findOneByEmail(email);
        
        if (nullableUser.isEmpty()) {
            throw new UnknownEmailException();
        }
        
        User user = nullableUser.get();
        
        if (!user.getActivated()) {
            throw new NotActivatedException();
        }
        
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        
        this.save(user);
        this.mails.sendPasswordResetMail(user);
    }

    @Override
    public boolean finishPasswordReset(String resetKey, String password) {
        Optional<User> nullableUser = this.users.findOneByResetKey(resetKey);
        
        if (nullableUser.isEmpty()) {
            return false;
        }
        
        User user = nullableUser.get();
        
        // Resetting a password needs to be completed within 24 hours
        
        boolean inTime = user.getResetDate().isAfter(
                Instant.now().minus(24, ChronoUnit.HOURS));
        
        if (inTime) {
            user.setPassword(this.passwordEncoder.encode(password));
        }
        
        user.setResetKey(null);
        user.setResetDate(null);
        this.save(user);
        
        return inTime;
    }
    
    /**
     * Returns the {@link User} object for the current
     * {@link org.springframework.security.core.context.SecurityContext}.
     * 
     * Please ensure that you call this method and access the returned
     * {@link User} object in the same transaction (e.g. by using the
     * {@link Transactional} annotation).
     * 
     * @return The {@link User} object for the current
     *         {@link org.springframework.security.core.context.SecurityContext}.
     * 
     * @throws UnauthorizedContextException If the current user was not
     *                                      authenticated.
     */
    private User getCurrentUser() throws UnauthorizedContextException {
        Optional<String> username = SecurityUtils.getCurrentUserLogin();
        
        if (username.isEmpty()) {
            throw new UnauthorizedContextException("User not authenticated!");
        }
        
        Optional<User> user = this.users.findOneByLogin(username.get());
        
        if (user.isEmpty()) {
            // NOTE: This should exception should never occur as this would mean
            //       that a user could authenticate themselves without existing
            //       in the database...
            
            throw new IllegalStateException(
                    "Authenticated user not persistent!");
        }
        
        return user.get();
    }
    
    /**
     * Saves modifications of the specified <code>user</code> to the persistent
     * storage.
     * 
     * @param user The modified user.
     */
    private void save(User user) {
        this.users.save(user);
        this.userSearch.save(user);
    }
}
