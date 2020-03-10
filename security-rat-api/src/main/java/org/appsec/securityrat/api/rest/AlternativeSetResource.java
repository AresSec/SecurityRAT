package org.appsec.securityrat.api.rest;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import org.appsec.securityrat.api.AlternativeSetProvider;
import org.appsec.securityrat.api.dto.AlternativeSet;

@RestController
@RequestMapping("/api")
public class AlternativeSetResource
        extends AbstractResourceBase<Long, AlternativeSet> {
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private AlternativeSetProvider dtoProvider;
    
    public AlternativeSetResource() {
        super("alternativeSet");
    }
    
    @RequestMapping(value = "/alternativeSets",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlternativeSet> create(
            @RequestBody AlternativeSet alternativeSet)
            throws URISyntaxException {
        return this.doCreate(alternativeSet);
    }

    @RequestMapping(value = "/alternativeSets",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlternativeSet> update(
            @RequestBody AlternativeSet alternativeSet)
            throws URISyntaxException {
        return this.doUpdate(alternativeSet);
    }

    @RequestMapping(value = "/alternativeSets",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlternativeSet> getAll() {
        return this.doGetAll();
    }

    @RequestMapping(value = "/alternativeSets/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlternativeSet> get(@PathVariable Long id) {
        return this.doGet(id);
    }

    @RequestMapping(value = "/alternativeSets/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return this.doDelete(id);
    }

    @RequestMapping(value = "/_search/alternativeSets/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlternativeSet> search(@PathVariable String query) {
        return this.doSearch(query);
    }

    @Override
    protected URI getLocation(AlternativeSet dto) throws URISyntaxException {
        return new URI("/api/alternativeSets/" + dto.getId().get());
    }
}