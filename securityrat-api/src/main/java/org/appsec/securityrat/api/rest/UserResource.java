package org.appsec.securityrat.api.rest;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.UserProvider;
import org.appsec.securityrat.api.dto.rest.User;
import org.appsec.securityrat.api.exception.ApiException;
import org.appsec.securityrat.api.util.HeaderUtil;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/admin-api")
@Slf4j
public class UserResource {
    private static User toRestDto(org.appsec.securityrat.api.dto.User user) {
        User dto = new User();
        
        dto.setId(user.getId().get());
        dto.setLogin(user.getLogin());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setActivated(user.getActivated());
        dto.setLangKey(user.getLangKey());
        dto.setResetKey(user.getResetKey());
        dto.setResetDate(user.getResetDate());
        dto.setAuthorities(user.getAuthorities());
        dto.setPersistentTokens(user.getPersistentTokens());
        
        return dto;
    }
    
    private static org.appsec.securityrat.api.dto.User toGeneralDto(User user) {
        org.appsec.securityrat.api.dto.User dto =
                new org.appsec.securityrat.api.dto.User();
        
        dto.setId(user.getId().get());
        dto.setLogin(user.getLogin());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setActivated(user.getActivated());
        dto.setLangKey(user.getLangKey());
        dto.setResetKey(user.getResetKey());
        dto.setResetDate(user.getResetDate());
        dto.setAuthorities(user.getAuthorities());
        dto.setPersistentTokens(user.getPersistentTokens());
        
        return dto;
    }
    
    @Inject
    private UserProvider dtoProvider;
    
    @RequestMapping(value = "/users",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        log.debug("REST request to get all Users");
        
        return this.dtoProvider.findAll()
                .stream()
                .map(UserResource::toRestDto)
                .collect(Collectors.toList());
    }
    
    @RequestMapping(value = "/userAuthorities",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsersWithAuthority() {
        log.debug("REST request to get all Users with their authorities");
        
        return this.dtoProvider.findAll()
                .stream()
                .map(UserResource::toRestDto)
                .collect(Collectors.toList());
    }
    
    @RequestMapping(value = "/userAuthorities/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        log.debug("REST request to get User : {}", id);
        
        return this.dtoProvider.findById(id)
                .map(UserResource::toRestDto)
                .map(u -> ResponseEntity.ok(u))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @RequestMapping(value = "/users/{login}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        
        return this.dtoProvider.findByLogin(login)
                .map(u -> ResponseEntity.ok(u))
                .map(UserResource::toRestDto)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @RequestMapping(value = "/users",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody User user)
            throws URISyntaxException {
        
        if (user.getId().isPresent()) {
            return ResponseEntity.badRequest()
                    .header("Failure", "A new user cannot already have an ID")
                    .body(null);
        }
        
        try {
            user = UserResource.toRestDto(this.dtoProvider.save(
                    UserResource.toGeneralDto(user)));
        } catch (ApiException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return ResponseEntity.created(this.getLocation(user))
                .headers(HeaderUtil.createEntityCreationAlert(
                        "user",
                        user.getId().get().toString()))
                .body(user);
    }
    
    @RequestMapping(value = "/users",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody User user)
            throws URISyntaxException {
        if (user.getId().isEmpty()) {
            return this.create(user);
        }
        
        try {
            user = UserResource.toRestDto(this.dtoProvider.save(
                    UserResource.toGeneralDto(user)));
        } catch (ApiException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return ResponseEntity.created(this.getLocation(user))
                .headers(HeaderUtil.createEntityUpdateAlert(
                        "user",
                        user.getId().get().toString()))
                .body(user);
    }

    @RequestMapping(value = "/users/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        log.debug("REST request to delete  user: {}", id);
        
        boolean deleted;
        
        try {
            deleted = this.dtoProvider.delete(id);
        } catch (ApiException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(
                        "user",
                        id.toString()))
                .build();
    }

    @RequestMapping(value = "/_search/users/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(@PathVariable String query) {
        return ResponseEntity.ok(this.dtoProvider.search(query)
                .stream()
                .map(UserResource::toRestDto)
                .collect(Collectors.toList()));
    }

    protected URI getLocation(User dto) throws URISyntaxException {
        return new URI("/admin-api/users/" + dto.getId().get());
    }
}
