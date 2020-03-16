package org.appsec.securityrat.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.AuthoritiesConstants;
import org.appsec.securityrat.api.dto.ConfigConstant;


@RestController
@RequestMapping("/admin-api")
@RolesAllowed(value=AuthoritiesConstants.ADMIN)
@Slf4j
public class ConfigConstantResource {
    @RequestMapping(value = "/configConstants",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConfigConstant> create(@RequestBody ConfigConstant configConstant) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/configConstants",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(value=AuthoritiesConstants.ADMIN)
    public ResponseEntity<ConfigConstant> update(@RequestBody ConfigConstant configConstant) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/configConstants",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ConfigConstant> getAll() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/configConstants/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConfigConstant> get(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/configConstants/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/_search/configConstants/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ConfigConstant> search(@PathVariable String query) {
        log.warn("Not implemented");
        return null;
    }
}
