package org.appsec.securityrat.api.rest;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.RequirementSkeleton;

@RestController
@RequestMapping("/api")
@Slf4j
public class RequirementSkeletonResource
        extends AbstractResourceBase<Long, RequirementSkeleton> {
    
    public RequirementSkeletonResource() {
        super("requirementSkeleton");
    }
    
    @RequestMapping(value = "/requirementSkeletons",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody RequirementSkeleton requirementSkeleton) throws URISyntaxException {
        return this.doCreate(requirementSkeleton);
    }

    @RequestMapping(value = "/requirementSkeletons",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody RequirementSkeleton requirementSkeleton) throws URISyntaxException {
        return this.doUpdate(requirementSkeleton);
    }

    @RequestMapping(value = "/requirementSkeletons",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        return this.doGetAll();
    }

    @RequestMapping(value = "/requirementSkeletons/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@PathVariable Long id) {
        return this.doGet(id);
    }

    @RequestMapping(value="/requirementSkeletons/getSelected",
    		method=RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@RequestParam("collections") Long[] collections, @RequestParam("projecttypes") Long[] projectTypes) {
        // TODO [luis.felger@bosch.com]: Implementation.
        
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/requirementSkeletons/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return this.doDelete(id);
    }

    @RequestMapping(value = "/_search/requirementSkeletons/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(@PathVariable String query) {
        return this.doSearch(query);
    }

    @RequestMapping(value = "/requirementSkeletons/foo/{shortName}",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RequirementSkeleton> foo(@PathVariable String shortName) {
        // TODO [luis.felger@bosch.com]: Implementation.
        
        log.warn("Not implemented");
        return null;
    }

    @Override
    protected URI getLocation(RequirementSkeleton dto) throws URISyntaxException {
        return new URI("/api/requirementSkeletons/" + dto.getId().get());
    }
}
