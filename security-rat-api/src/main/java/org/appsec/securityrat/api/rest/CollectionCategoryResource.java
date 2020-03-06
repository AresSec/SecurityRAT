package org.appsec.securityrat.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.CollectionCategory;


@RestController
@RequestMapping("/api")
@Slf4j
public class CollectionCategoryResource {
    @RequestMapping(value = "/collectionCategorys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionCategory> create(@RequestBody CollectionCategory collectionCategory) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/collectionCategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionCategory> update(@RequestBody CollectionCategory collectionCategory) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/collectionCategorys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CollectionCategory> getAll() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/collectionCategorys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionCategory> get(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/collectionCategorys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/_search/collectionCategorys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CollectionCategory> search(@PathVariable String query) {
        log.warn("Not implemented");
        return null;
    }
}
