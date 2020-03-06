package org.appsec.securityrat.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.AlternativeSet;

@RestController
@RequestMapping("/api")
@Slf4j
public class AlternativeSetResource {
    @RequestMapping(value = "/alternativeSets",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlternativeSet> create(@RequestBody AlternativeSet alternativeSet) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/alternativeSets",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlternativeSet> update(@RequestBody AlternativeSet alternativeSet) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/alternativeSets",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlternativeSet> getAll() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/alternativeSets/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlternativeSet> get(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/alternativeSets/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/_search/alternativeSets/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlternativeSet> search(@PathVariable String query) {
        log.warn("Not implemented");
        return null;
    }
}
