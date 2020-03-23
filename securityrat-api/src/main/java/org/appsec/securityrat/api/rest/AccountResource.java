package org.appsec.securityrat.api.rest;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.appsec.securityrat.api.AuthenticationProvider;
import org.appsec.securityrat.api.UserProvider;
import org.appsec.securityrat.api.dto.User;
import org.appsec.securityrat.api.exception.EmailAlreadyInUseException;
import org.appsec.securityrat.api.exception.NotActivatedException;
import org.appsec.securityrat.api.exception.UnknownEmailException;
import org.appsec.securityrat.api.exception.UsernameTakenException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.appsec.securityrat.api.dto.rest.Account;
import org.appsec.securityrat.api.dto.rest.ResetKeyAndPassword;
import org.appsec.securityrat.api.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
    private UserProvider accounts;
    
    @Inject
    private AuthenticationProvider authentication;
    
    private User getCurrentUser() {
        String currentUser = this.authentication.getCurrentUser();
        
        if (currentUser == null) {
            return null;
        }
        
        return this.accounts.findByLogin(currentUser)
                .orElseThrow(() -> {
                    // NOTE: This should never happen as this would indicate
                    //       that somebody got authenticated without existing in
                    //       the persistent storage.
                    
                    return new IllegalStateException(
                            "Unknown user, but authenticated!");
                });
    }
    
    private boolean checkPasswordLength(String password) {
      return (!StringUtils.isEmpty(password) &&
              password.length() >= Account.PASSWORD_MIN_LENGTH &&
              password.length() <= Account.PASSWORD_MAX_LENGTH) &&
              password.matches(Account.PASSWORD_REGEX);
    }
    
    @GetMapping("/account")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> get() {
        User user = this.getCurrentUser();
        
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not authenticated!");
        }
        
        Account dto = new Account();
        
        dto.setLogin(user.getLogin());
        dto.setPassword(null);
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setLangKey(user.getLangKey());
        
        dto.setRoles(user.getAuthorities()
                .stream()
                .map(a -> a.getName())
                .collect(Collectors.toSet()));
        
        return ResponseEntity.ok(dto);
    }
    
    @PostMapping("/account")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> post(@Valid @RequestBody Account account) {
        User user = this.getCurrentUser();
        
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not authenticated!");
        }
        
        user.setFirstName(account.getFirstName());
        user.setLastName(account.getLastName());
        user.setEmail(account.getEmail());
        user.setLangKey(account.getLangKey());
        
        try {
            this.accounts.save(user);
        } catch (EmailAlreadyInUseException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email address already in use!");
        } catch (ApiException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
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
        
        User user = this.getCurrentUser();
        
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not authenticated!");
        }
        
        if (!this.checkPasswordLength(password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Incorrect password");
        }
        
        user.setPassword(password);
        
        try {
            this.accounts.save(user);
        } catch (ApiException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/account/confirm_password")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> postConfirmPassword(@RequestBody String password) {
        User user = this.getCurrentUser();
        
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not authenticated!");
        }
        
        if (!this.accounts.confirmPassword(user.getId().get(), password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Password did not match");
        }
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/account/sessions")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getSessions() {
        User user = this.getCurrentUser();
        
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not authenticated!");
        }
        
        return ResponseEntity.ok(user.getPersistentTokens());
    }
    
    @DeleteMapping("/account/sessions/{series}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteSessions(
            @PathVariable @NotBlank String series) {
        
        // NOTE: Since the original value of "series" is a Base64-encoded
        //       string and the Spring firewall blocks URL-escaped characters
        //       (like '%2F') by default, we need to circumvent this by using
        //       another encoding that can pass the firewall.
        //       The current approach uses the hexadecimal representation of the
        //       UTF-8 encoded text.
        
        try {
            series = new String(Hex.decodeHex(series), StandardCharsets.UTF_8);
        } catch (DecoderException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid encoding");
        }
        
        User user = this.getCurrentUser();
        
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not authenticated!");
        }
        
        try {
            this.accounts.invalidateToken(user.getId().get(), series);
        } catch (ApiException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/account/reset_password/init")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> postResetPasswordInit(
            @RequestBody
            @Email
            @Size(min = 5, max = 100)
            String mail) {
        
        try {
            this.accounts.requestPasswordReset(mail);
        } catch (UnknownEmailException ex) {
            return ResponseEntity.badRequest()
                    .body("Unknown email address!");
        } catch (NotActivatedException ex) {
            return ResponseEntity.badRequest()
                    .body("Account not activated!");
        } catch (ApiException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return ResponseEntity.ok("Email was sent.");
    }
    
    @PostMapping("/account/reset_password/finish")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> postResetPasswordFinish(
            @Valid
            @RequestBody
            ResetKeyAndPassword dto) {
        
        try {
            if (!this.accounts.finishPasswordReset(
                    dto.getKey(),
                    dto.getNewPassword())) {
                return ResponseEntity.badRequest()
                    .body("Resetting the password failed!");
            }
        } catch (ApiException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> postRegister(
            @Valid
            @RequestBody
            Account account) {
        
        User user = new User();
        
        user.setLogin(account.getLogin());
        user.setPassword(account.getPassword());
        user.setFirstName(account.getFirstName());
        user.setLastName(account.getLastName());
        user.setEmail(account.getEmail());
        user.setLangKey(account.getLangKey());

        try {
            this.accounts.save(user);
        } catch (UsernameTakenException ex) {
            return ResponseEntity.badRequest()
                    .body("Username already in use!");
        } catch (EmailAlreadyInUseException ex) {
            return ResponseEntity.badRequest()
                    .body("Email already in use!");
        }  catch (ApiException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PostMapping("/activate")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getActivate(
            @RequestParam("key")
            @NotBlank
            String key) {
        
        try {
            if (!this.accounts.activate(key)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (ApiException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
