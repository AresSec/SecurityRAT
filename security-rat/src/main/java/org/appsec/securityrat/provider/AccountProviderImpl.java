package org.appsec.securityrat.provider;

import java.util.Optional;
import javax.inject.Inject;
import org.appsec.securityrat.api.AccountProvider;
import org.appsec.securityrat.api.dto.Account;
import org.appsec.securityrat.domain.User;
import org.appsec.securityrat.mapper.AccountMapper;
import org.appsec.securityrat.repository.UserRepository;
import org.appsec.securityrat.security.SecurityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountProviderImpl implements AccountProvider {
    @Inject
    private UserRepository users;
    
    @Inject
    private AccountMapper mapper;
    
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
        return this.users.findOneByEmailIgnoreCase(email)
                .map(e -> this.mapper.toDto(e));
    }

    @Override
    public Account save(Account account) {
        throw new UnsupportedOperationException();
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
}
