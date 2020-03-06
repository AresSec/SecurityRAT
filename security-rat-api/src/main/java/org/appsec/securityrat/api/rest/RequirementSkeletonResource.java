package org.appsec.securityrat.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.RequirementSkeleton;

@RestController
@RequestMapping("/api")
@Slf4j
public class RequirementSkeletonResource {
    @RequestMapping(value = "/requirementSkeletons",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequirementSkeleton> create(@RequestBody RequirementSkeleton requirementSkeleton) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/requirementSkeletons",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequirementSkeleton> update(@RequestBody RequirementSkeleton requirementSkeleton) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/requirementSkeletons",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RequirementSkeleton> getAll() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/requirementSkeletons/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequirementSkeleton> get(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value="/requirementSkeletons/getSelected",
    		method=RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RequirementSkeleton> get(@RequestParam("collections") Long[] collections, @RequestParam("projecttypes") Long[] projectTypes) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/requirementSkeletons/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/_search/requirementSkeletons/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RequirementSkeleton> search(@PathVariable String query) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/requirementSkeletons/foo/{shortName}",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RequirementSkeleton> foo(@PathVariable String shortName) {
        log.warn("Not implemented");
        return null;
    }
}
