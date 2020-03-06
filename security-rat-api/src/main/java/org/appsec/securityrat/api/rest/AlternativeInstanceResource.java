package org.appsec.securityrat.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.AlternativeInstance;

@RestController
@RequestMapping("/api")
@Slf4j
public class AlternativeInstanceResource {
    @RequestMapping(value = "/alternativeInstances",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlternativeInstance> create(
            @RequestBody
            AlternativeInstance alternativeInstance) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/alternativeInstances",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlternativeInstance> update(
            @RequestBody
            AlternativeInstance alternativeInstance) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/alternativeInstances",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlternativeInstance> getAll() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/alternativeInstances/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlternativeInstance> get(
            @PathVariable
            Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/alternativeInstances/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(
            @PathVariable
            Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/_search/alternativeInstances/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlternativeInstance> search(
            @PathVariable
            String query) {
        log.warn("Not implemented");
        return null;
    }
}
