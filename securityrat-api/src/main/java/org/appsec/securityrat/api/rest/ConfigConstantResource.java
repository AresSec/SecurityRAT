package org.appsec.securityrat.api.rest;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.AuthoritiesConstants;
import org.appsec.securityrat.api.ConfigConstantProvider;
import org.appsec.securityrat.api.dto.ConfigConstant;


@RestController
@RequestMapping("/admin-api")
@RolesAllowed(value=AuthoritiesConstants.ADMIN)
@Slf4j
public class ConfigConstantResource
        extends AbstractResourceBase<Long, ConfigConstant> {
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private ConfigConstantProvider dtoProvider;
    
    public ConfigConstantResource() {
        super("configConstant");
    }
    
    @RequestMapping(value = "/configConstants",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConfigConstant> create(
            @RequestBody ConfigConstant configConstant)
            throws URISyntaxException {
        return this.doCreate(configConstant);
    }

    @RequestMapping(value = "/configConstants",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(value=AuthoritiesConstants.ADMIN)
    public ResponseEntity<ConfigConstant> update(
            @RequestBody ConfigConstant configConstant)
            throws URISyntaxException {
        return this.doUpdate(configConstant);
    }

    @RequestMapping(value = "/configConstants",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ConfigConstant> getAll() {
        return this.doGetAll();
    }

    @RequestMapping(value = "/configConstants/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConfigConstant> get(@PathVariable Long id) {
        return this.doGet(id);
    }

    @RequestMapping(value = "/configConstants/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return this.doDelete(id);
    }

    @RequestMapping(value = "/_search/configConstants/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ConfigConstant> search(@PathVariable String query) {
        return this.doSearch(query);
    }
    
    @Override
    protected URI getLocation(ConfigConstant dto) throws URISyntaxException {
        return new URI("/admin-api/configConstants/" + dto.getId().get());
    }
}
