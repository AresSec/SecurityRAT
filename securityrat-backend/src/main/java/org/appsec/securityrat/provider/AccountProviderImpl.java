package org.appsec.securityrat.provider;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.appsec.securityrat.api.AccountProvider;
import org.appsec.securityrat.api.dto.Account;
import org.appsec.securityrat.api.dto.PersistentToken;
import org.appsec.securityrat.domain.User;
import org.appsec.securityrat.mapper.AccountMapper;
import org.appsec.securityrat.mapper.PersistentTokenMapper;
import org.appsec.securityrat.repository.UserRepository;
import org.appsec.securityrat.repository.search.UserSearchRepository;
import org.appsec.securityrat.security.SecurityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountProviderImpl implements AccountProvider {
    // TODO [luis.felger@bosch.com]: A lot of duplicated code in here... That
    //                               smells and should be shortened!
    
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

    @Override
    @Transactional
    public Optional<Account> findOneByLogin(String login) {
        return this.users.findOneByLogin(login)
                .map(e -> this.mapper.toDto(e));
    }

    @Override
    @Transactional
    public Optional<Account> findOneByEmail(String email) {
        return this.users.findOneByEmail(email)
                .map(e -> this.mapper.toDto(e));
    }

    @Override
    @Transactional
    public Account save(Account account) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        
        if (login.isEmpty()) {
            throw new IllegalArgumentException("User not authenticated");
        }
        
        Optional<User> nullableUser = this.users.findOneByLogin(login.get());
        
        if (nullableUser.isEmpty()) {
            throw new IllegalStateException("Authenticated user unknown");
        }
        
        User user = nullableUser.get();
        
        this.mapper.toUser(account, user);
        
        this.users.save(user);
        this.userSearch.save(user);
        
        return this.mapper.toDto(user);
    }

    @Override
    public boolean activate(String activationKey) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional
    public Optional<Account> getCurrent() {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        
        if (login.isEmpty()) {
            return Optional.empty();
        }
        
        return this.findOneByLogin(login.get());
    }

    @Override
    @Transactional
    public boolean confirmPassword(String password) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        
        if (login.isEmpty()) {
            return false;
        }
        
        Optional<User> user = this.users.findOneByLogin(login.get());
        
        if (user.isEmpty()) {
            return false;
        }
        
        return this.passwordEncoder.matches(password, user.get().getPassword());
    }

    @Override
    @Transactional
    public List<PersistentToken> getCurrentTokens() {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        
        if (login.isEmpty()) {
            throw new IllegalArgumentException("User not authenticated");
        }
        
        Optional<User> nullableUser = this.users.findOneByLogin(login.get());
        
        if (nullableUser.isEmpty()) {
            throw new IllegalStateException("Authenticated user unknown");
        }
        
        User user = nullableUser.get();
        
        return user.getPersistentTokens()
                .stream()
                .map(t -> this.tokenMapper.toDto(t))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean invalidateToken(String series) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        
        if (login.isEmpty()) {
            throw new IllegalArgumentException("User not authenticated");
        }
        
        Optional<User> nullableUser = this.users.findOneByLogin(login.get());
        
        if (nullableUser.isEmpty()) {
            throw new IllegalStateException("Authenticated user unknown");
        }
        
        User user = nullableUser.get();
        
        Optional<org.appsec.securityrat.domain.PersistentToken> token =
                user.getPersistentTokens()
                        .stream()
                        .filter(t -> Objects.equals(t.getSeries(), series))
                        .findAny();
        
        if (token.isEmpty()) {
            return false;
        }
        
        user.getPersistentTokens().remove(token.get());
        
        this.users.save(user);
        this.userSearch.save(user);
        
        return true;
    }
}
