package org.appsec.securityrat.api.rest;

import java.nio.charset.StandardCharsets;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.appsec.securityrat.api.AccountProvider;
import org.appsec.securityrat.api.exception.EmailAlreadyInUseException;
import org.appsec.securityrat.api.exception.NotActivatedException;
import org.appsec.securityrat.api.exception.UnauthorizedContextException;
import org.appsec.securityrat.api.exception.UnknownEmailException;
import org.appsec.securityrat.api.exception.UsernameTakenException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.appsec.securityrat.api.dto.Account;
import org.appsec.securityrat.api.dto.ResetKeyAndPassword;
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
    
    @GetMapping("/account")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> get() {
        try {
            return ResponseEntity.ok(this.accounts.getCurrent());
        } catch (UnauthorizedContextException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not authenticated!");
        }
    }
    
    @PostMapping("/account")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> post(@Valid @RequestBody Account account) {
        // NOTE: It is important to clear the password property first.
        //       Otherwise, clients may change their passwords by calling this
        //       endpoint.
        
        account.setPassword(null);
        
        try {
            this.accounts.save(account);
        } catch (EmailAlreadyInUseException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email address already in use!");
        } catch (UnauthorizedContextException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Either you are not authenticated or you attempted "
                            + "to change another user's account information!");
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
        
        Account currentAccount;
        
        try {
            currentAccount = this.accounts.getCurrent();
        } catch (UnauthorizedContextException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not authenticated!");
        }

        // TODO: Is the password length validation required?
        
        currentAccount.setPassword(password);
        
        try {
            this.accounts.save(currentAccount);
        } catch (EmailAlreadyInUseException | UnauthorizedContextException ex) {
            // NOTE: This exception should never occur since we already queried
            //       the requester's account and did not change their email.
            
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/account/confirm_password")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> postConfirmPassword(@RequestBody String password) {
        // NOTE: This call adds zero security because it does not change the
        //       user's state on the server side and thus their is no
        //       verification that a user ever made this API call.
        
        try {
            if (!this.accounts.confirmPassword(password)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Password did not match");
            }
        } catch (UnauthorizedContextException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not authenticated!");
        }
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/account/sessions")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getSessions() {
        try {
            return ResponseEntity.ok(this.accounts.getCurrentTokens());
        } catch (UnauthorizedContextException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not authenticated!");
        }
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
        
        try {
            this.accounts.invalidateToken(series);
        } catch (UnauthorizedContextException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not authenticated!");
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
        }
        
        return ResponseEntity.ok("Email was sent.");
    }
    
    @PostMapping("/account/reset_password/finish")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> postResetPasswordFinish(
            @Valid
            @RequestBody
            ResetKeyAndPassword dto) {
        
        boolean success = this.accounts.finishPasswordReset(
                dto.getKey(),
                dto.getNewPassword());
        
        if (!success) {
            return ResponseEntity.badRequest()
                    .body("Resetting the password failed!");
        }
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> postRegister(
            @Valid
            @RequestBody
            Account account) {

        try {
            this.accounts.create(account);
        } catch (UsernameTakenException ex) {
            return ResponseEntity.badRequest()
                    .body("Username already in use!");
        } catch (EmailAlreadyInUseException ex) {
            return ResponseEntity.badRequest()
                    .body("Email already in use!");
        }
        
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
        
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
