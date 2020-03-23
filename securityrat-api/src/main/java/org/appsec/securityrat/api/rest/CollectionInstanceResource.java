package org.appsec.securityrat.api.rest;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.rest.CollectionInstance;


@RestController
@RequestMapping("/api")
@Slf4j
public class CollectionInstanceResource
        extends AbstractResourceBase<Long, CollectionInstance> {
    
    public CollectionInstanceResource() {
        super("collectionInstance");
    }
    
    @RequestMapping(value = "/collectionInstances",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody CollectionInstance collectionInstance) throws URISyntaxException {
        return this.doCreate(collectionInstance);
    }

    @RequestMapping(value = "/collectionInstances",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody CollectionInstance collectionInstance) throws URISyntaxException {
        return this.doUpdate(collectionInstance);
    }

    @RequestMapping(value = "/collectionInstances",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        return this.doGetAll();
    }

    @RequestMapping(value = "/collectionInstances/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@PathVariable Long id) {
        return this.doGet(id);
    }

    @RequestMapping(value = "/collectionInstances/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return this.doDelete(id);
    }

    @RequestMapping(value = "/_search/collectionInstances/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(@PathVariable String query) {
        return this.doSearch(query);
    }

    @Override
    protected URI getLocation(CollectionInstance dto) throws URISyntaxException {
        return new URI("/api/collectionInstances/" + dto.getId().get());
    }
}
