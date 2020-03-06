package org.appsec.securityrat.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.OptColumnContent;


@RestController
@RequestMapping("/api")
@Slf4j
public class OptColumnContentResource {
    @RequestMapping(value = "/optColumnContents",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OptColumnContent> create(@RequestBody OptColumnContent optColumnContent) {
        log.warn("Not implemented");
        return null;
    }
    
    @RequestMapping(value = "/optColumnContents",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OptColumnContent> update(@RequestBody OptColumnContent optColumnContent) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/optColumnContents",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OptColumnContent> getAll() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/optColumnContents/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OptColumnContent> get(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
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
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/_search/optColumnContents/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OptColumnContent> search(@PathVariable String query) {
        log.warn("Not implemented");
        return null;
    }
}
