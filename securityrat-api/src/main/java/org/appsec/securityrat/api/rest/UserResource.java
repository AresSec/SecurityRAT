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
import javax.inject.Inject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.UserProvider;
import org.appsec.securityrat.api.dto.User;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/admin-api")
@Slf4j
public class UserResource extends AbstractResourceBase<Long, User> {
    @Inject
    @Getter
    private UserProvider dtoProvider;
    
    public UserResource() {
        super("user");
    }
    
    @RequestMapping(value = "/users",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        log.debug("REST request to get all Users");
        return this.dtoProvider.findAll();
    }
    
    @RequestMapping(value = "/userAuthorities",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsersWithAuthority() {
        log.debug("REST request to get all Users with their authorities");
        return this.dtoProvider.findAll();
    }
    
    @RequestMapping(value = "/userAuthorities/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        log.debug("REST request to get User : {}", id);
        
        return this.dtoProvider.findById(id)
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
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @RequestMapping(value = "/users",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@RequestBody User userdto)
            throws URISyntaxException {
        
        return this.doCreate(userdto);
    }
    
    @RequestMapping(value = "/users",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> update(@RequestBody User user)
            throws URISyntaxException {
        return this.doUpdate(user);
    }

    @RequestMapping(value = "/users/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete  user: {}", id);
        return this.doDelete(id);
    }

    @RequestMapping(value = "/_search/users/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> search(@PathVariable String query) {
        return this.doSearch(query);
    }

    @Override
    protected URI getLocation(User dto) throws URISyntaxException {
        return new URI("/admin-api/users/" + dto.getId().get());
    }
}
