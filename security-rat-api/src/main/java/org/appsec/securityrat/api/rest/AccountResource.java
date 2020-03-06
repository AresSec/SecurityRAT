package org.appsec.securityrat.api.rest;

import java.util.Collection;
import java.util.Collections;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.appsec.securityrat.api.MailServiceProvider;
import org.appsec.securityrat.api.PersistentTokenRepositoryProvider;
import org.appsec.securityrat.api.UserRepositoryProvider;
import org.appsec.securityrat.api.UserServiceProvider;
import org.appsec.securityrat.api.dto.Account;
import org.appsec.securityrat.api.dto.ResetKeyAndPassword;
import org.appsec.securityrat.api.dto.PersistentToken;
import org.springframework.http.HttpStatus;
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
    private UserRepositoryProvider userRepository;
    
    @Inject
    private UserServiceProvider userService;
    
    @Inject
    private PersistentTokenRepositoryProvider persistentTokenRepository;
    
    @Inject
    private MailServiceProvider mailService;
    
    @GetMapping("/account")
    @ResponseStatus(HttpStatus.OK)
    public Account get() {
        log.warn("Not implemented");
        return new Account();
    }
    
    @PostMapping("/account")
    @ResponseStatus(HttpStatus.OK)
    public void post(@Valid @RequestBody Account account) {
        log.warn("Not implemented");
    }
    
    @PostMapping("/account/change_password")
    @ResponseStatus(HttpStatus.OK)
    public void postChangePassword(
            @RequestBody
            @Pattern(regexp = Account.PASSWORD_REGEX)
            @Size(min = Account.PASSWORD_MIN_LENGTH,
                    max = Account.PASSWORD_MAX_LENGTH)
            String password) {
        log.warn("Not implemented");
    }
    
    @PostMapping("/account/confirm_password")
    @ResponseStatus(HttpStatus.OK)
    public void postConfirmPassword(@RequestBody String password) {
        log.warn("Not implemented");
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
    public String postRegister(
            @Valid
            @RequestBody
            Account account) {
        log.warn("Not implemented");
        return "";
    }
    
    @PostMapping("/activate")
    @ResponseStatus(HttpStatus.OK)
    public void getActivate(
            @RequestParam("key")
            @NotBlank
            String key) {
        log.warn("Not implemented");
    }
}
