package org.appsec.securityrat.api.rest;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.AccountProvider;
import org.appsec.securityrat.api.MailProvider;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.appsec.securityrat.api.dto.Account;
import org.appsec.securityrat.api.dto.ResetKeyAndPassword;
import org.appsec.securityrat.api.dto.PersistentToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api")
@Slf4j
public class AccountResource {
    @Inject
    private AccountProvider accounts;
    
    @Inject
    private MailProvider mails;
    
    @GetMapping("/account")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Account> get() {
        Optional<Account> account = this.accounts.getCurrent();
        
        if (account.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return ResponseEntity.ok(account.get());
    }
    
    @PostMapping("/account")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> post(@Valid @RequestBody Account account) {
        // AccountProvider.save will create a new account, if the account is not
        // existing yet. To prevent this, we need to check first whether the
        // account exists or not.
        
        if (this.accounts.findOneByLogin(account.getLogin()).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        // NOTE: It is important to clear the password property first.
        //       Otherwise, clients may change their passwords by calling this
        //       endpoint.
        
        account.setPassword(null);
        
        this.accounts.save(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/account/change_password")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> postChangePassword(
            @RequestBody
            @Pattern(regexp = Account.PASSWORD_REGEX)
            @Size(min = Account.PASSWORD_MIN_LENGTH,
                    max = Account.PASSWORD_MAX_LENGTH)
            String password) {
        
        Optional<Account> currentAccountNullable = this.accounts.getCurrent();
        
        if (currentAccountNullable.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        // TODO: Is the password length validation required?
        
        Account currentAccount = currentAccountNullable.get();
        currentAccount.setPassword(password);
        this.accounts.save(currentAccount);
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/account/confirm_password")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> postConfirmPassword(@RequestBody String password) {
        if (!this.accounts.confirmPassword(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Password did not match");
        }
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/account/sessions")
    @ResponseStatus(HttpStatus.OK)
    public Collection<PersistentToken> getSessions() {
        log.warn("Not implemented");
        return Collections.EMPTY_SET;
    }
    
    @DeleteMapping("/account/sessions/{series}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSessions(@PathVariable @NotBlank String series) {
        log.warn("Not implemented");
    }
    
    @PostMapping("/account/reset_password/init")
    @ResponseStatus(HttpStatus.OK)
    public String postResetPasswordInit(
            @RequestBody
            @Email
            @Size(min = 5, max = 100)
            String mail) {
        log.warn("Not implemented");
        return "";
    }
    
    @PostMapping("/account/reset_password/finish")
    @ResponseStatus(HttpStatus.OK)
    public String postResetPasswordFinish(
            @Valid
            @RequestBody
            ResetKeyAndPassword dto) {
        log.warn("Not implemented");
        return "";
    }
    
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> postRegister(
            @Valid
            @RequestBody
            Account account) {
        
        if (this.accounts.findOneByLogin(account.getLogin()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body("login already in use");
        }
        
        if (this.accounts.findOneByEmail(account.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body("e-mail address already in use");
        }
        
        this.mails.sendActivationEmail(this.accounts.save(account));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PostMapping("/activate")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getActivate(
            @RequestParam("key")
            @NotBlank
            String key) {
        
        if (this.accounts.activate(key)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
