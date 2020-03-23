package org.appsec.securityrat.api.rest;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.appsec.securityrat.api.dto.rest.AlternativeSet;

@RestController
@RequestMapping("/api")
public class AlternativeSetResource
        extends AbstractResourceBase<Long, AlternativeSet> {
    
    public AlternativeSetResource() {
        super("alternativeSet");
    }
    
    @RequestMapping(value = "/alternativeSets",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(
            @RequestBody AlternativeSet alternativeSet)
            throws URISyntaxException {
        return this.doCreate(alternativeSet);
    }

    @RequestMapping(value = "/alternativeSets",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(
            @RequestBody AlternativeSet alternativeSet)
            throws URISyntaxException {
        return this.doUpdate(alternativeSet);
    }

    @RequestMapping(value = "/alternativeSets",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        return this.doGetAll();
    }

    @RequestMapping(value = "/alternativeSets/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@PathVariable Long id) {
        return this.doGet(id);
    }

    @RequestMapping(value = "/alternativeSets/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return this.doDelete(id);
    }

    @RequestMapping(value = "/_search/alternativeSets/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(@PathVariable String query) {
        return this.doSearch(query);
    }

    @Override
    protected URI getLocation(AlternativeSet dto) throws URISyntaxException {
        return new URI("/api/alternativeSets/" + dto.getId().get());
    }
}
