package org.appsec.securityrat.api.rest;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.appsec.securityrat.api.dto.OptColumnType;

@RestController
@RequestMapping("/api")
public class OptColumnTypeResource
        extends AbstractResourceBase<Long, OptColumnType> {
    
    public OptColumnTypeResource() {
        super("optColumnType");
    }
    
    @RequestMapping(value = "/optColumnTypes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody OptColumnType optColumnType) throws URISyntaxException {
        return this.doCreate(optColumnType);
    }

    @RequestMapping(value = "/optColumnTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody OptColumnType optColumnType) throws URISyntaxException {
        return this.doUpdate(optColumnType);
    }

    @RequestMapping(value = "/optColumnTypes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        return this.doGetAll();
    }

    @RequestMapping(value = "/optColumnTypes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@PathVariable Long id) {
        return this.doGet(id);
    }

    @RequestMapping(value = "/optColumnTypes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return this.doDelete(id);
    }

    @RequestMapping(value = "/_search/optColumnTypes/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(@PathVariable String query) {
        return this.doSearch(query);
    }

    @Override
    protected URI getLocation(OptColumnType dto) throws URISyntaxException {
        return new URI("/api/optColumnTypes/" + dto.getId().get());
    }
}
