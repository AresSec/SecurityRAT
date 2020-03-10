package org.appsec.securityrat.api.rest;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.OptColumnContentProvider;
import org.appsec.securityrat.api.dto.OptColumnContent;


@RestController
@RequestMapping("/api")
@Slf4j
public class OptColumnContentResource extends AbstractResourceBase<Long, OptColumnContent> {
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private OptColumnContentProvider dtoProvider;
    
    public OptColumnContentResource() {
        super("optColumnContent");
    }
    
    @RequestMapping(value = "/optColumnContents",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OptColumnContent> create(@RequestBody OptColumnContent optColumnContent) throws URISyntaxException {
        return this.doCreate(optColumnContent);
    }
    
    @RequestMapping(value = "/optColumnContents",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OptColumnContent> update(@RequestBody OptColumnContent optColumnContent) throws URISyntaxException {
        return this.doUpdate(optColumnContent);
    }

    @RequestMapping(value = "/optColumnContents",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OptColumnContent> getAll() {
        return this.doGetAll();
    }

    @RequestMapping(value = "/optColumnContents/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OptColumnContent> get(@PathVariable Long id) {
        return this.doGet(id);
    }

    @RequestMapping(value = "/optColumnContents/getRequirement/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<OptColumnContent>> getRequirement(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value="/optColumnContents/filter",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OptColumnContent> foo(
    		@RequestParam("projectTypeId") Long projectTypeId,
    		@RequestParam("requirementId") Long requirementSkeletonId) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value="/optColumnContents/byOptColumnAndRequirement/{optColumnId}/{requirementId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public OptColumnContent getOptColumnContentByOptColumnAndRequirement(
        @PathVariable("optColumnId") Long optColumnId,
        @PathVariable("requirementId") Long requirementId) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/optColumnContents/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return this.doDelete(id);
    }

    @RequestMapping(value = "/_search/optColumnContents/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OptColumnContent> search(@PathVariable String query) {
        return this.doSearch(query);
    }

    @Override
    protected URI getLocation(OptColumnContent dto) throws URISyntaxException {
        return new URI("/api/optColumnContents/" + dto.getId().get());
    }
}