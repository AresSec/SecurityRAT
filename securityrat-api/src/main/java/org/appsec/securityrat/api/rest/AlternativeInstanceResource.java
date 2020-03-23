package org.appsec.securityrat.api.rest;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.appsec.securityrat.api.dto.rest.AlternativeInstance;

@RestController
@RequestMapping("/api")
public class AlternativeInstanceResource
        extends AbstractResourceBase<Long, AlternativeInstance> {
    
    public AlternativeInstanceResource() {
        super("alternativeInstance");
    }
    
    @RequestMapping(value = "/alternativeInstances",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(
            @RequestBody
            AlternativeInstance alternativeInstance) throws URISyntaxException {
        return this.doCreate(alternativeInstance);
    }

    @RequestMapping(value = "/alternativeInstances",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(
            @RequestBody
            AlternativeInstance alternativeInstance) throws URISyntaxException {
        return this.doUpdate(alternativeInstance);
    }

    @RequestMapping(value = "/alternativeInstances",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        return this.doGetAll();
    }

    @RequestMapping(value = "/alternativeInstances/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(
            @PathVariable
            Long id) {
        return this.doGet(id);
    }

    @RequestMapping(value = "/alternativeInstances/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(
            @PathVariable
            Long id) {
        return this.doDelete(id);
    }

    @RequestMapping(value = "/_search/alternativeInstances/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(
            @PathVariable
            String query) {
        return this.doSearch(query);
    }

    @Override
    protected URI getLocation(AlternativeInstance dto)
            throws URISyntaxException {
        return new URI("/api/alternativeInstances/" + dto.getId().get());
    }
}
