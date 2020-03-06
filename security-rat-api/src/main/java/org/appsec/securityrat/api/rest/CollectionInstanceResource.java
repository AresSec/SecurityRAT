package org.appsec.securityrat.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.CollectionInstance;


@RestController
@RequestMapping("/api")
@Slf4j
public class CollectionInstanceResource {
    @RequestMapping(value = "/collectionInstances",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionInstance> create(@RequestBody CollectionInstance collectionInstance) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/collectionInstances",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionInstance> update(@RequestBody CollectionInstance collectionInstance) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/collectionInstances",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CollectionInstance> getAll() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/collectionInstances/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionInstance> get(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/collectionInstances/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/_search/collectionInstances/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CollectionInstance> search(@PathVariable String query) {
        log.warn("Not implemented");
        return null;
    }
}
