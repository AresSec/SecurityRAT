package org.appsec.securityrat.api.rest;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import org.appsec.securityrat.api.AuthoritiesConstants;
import org.appsec.securityrat.api.dto.rest.ConfigConstant;


@RestController
@RequestMapping("/admin-api")
@RolesAllowed(value=AuthoritiesConstants.ADMIN)
public class ConfigConstantResource
        extends AbstractResourceBase<Long, ConfigConstant> {
    
    public ConfigConstantResource() {
        super("configConstant");
    }
    
    @RequestMapping(value = "/configConstants",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(
            @RequestBody ConfigConstant configConstant)
            throws URISyntaxException {
        return this.doCreate(configConstant);
    }

    @RequestMapping(value = "/configConstants",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(value=AuthoritiesConstants.ADMIN)
    public ResponseEntity<?> update(
            @RequestBody ConfigConstant configConstant)
            throws URISyntaxException {
        return this.doUpdate(configConstant);
    }

    @RequestMapping(value = "/configConstants",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        return this.doGetAll();
    }

    @RequestMapping(value = "/configConstants/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@PathVariable Long id) {
        return this.doGet(id);
    }

    @RequestMapping(value = "/configConstants/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return this.doDelete(id);
    }

    @RequestMapping(value = "/_search/configConstants/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(@PathVariable String query) {
        return this.doSearch(query);
    }
    
    @Override
    protected URI getLocation(ConfigConstant dto) throws URISyntaxException {
        return new URI("/admin-api/configConstants/" + dto.getId().get());
    }
}
