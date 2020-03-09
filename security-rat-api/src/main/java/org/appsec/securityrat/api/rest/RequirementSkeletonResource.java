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
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.RequirementSkeletonProvider;
import org.appsec.securityrat.api.dto.RequirementSkeleton;

@RestController
@RequestMapping("/api")
@Slf4j
public class RequirementSkeletonResource extends AbstractResourceBase<Long, RequirementSkeleton> {
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private RequirementSkeletonProvider dtoProvider;
    
    public RequirementSkeletonResource() {
        super("requirementSkeleton");
    }
    
    @RequestMapping(value = "/requirementSkeletons",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequirementSkeleton> create(@RequestBody RequirementSkeleton requirementSkeleton) throws URISyntaxException {
        return this.doCreate(requirementSkeleton);
    }

    @RequestMapping(value = "/requirementSkeletons",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequirementSkeleton> update(@RequestBody RequirementSkeleton requirementSkeleton) throws URISyntaxException {
        return this.doUpdate(requirementSkeleton);
    }

    @RequestMapping(value = "/requirementSkeletons",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RequirementSkeleton> getAll() {
        return this.doGetAll();
    }

    @RequestMapping(value = "/requirementSkeletons/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequirementSkeleton> get(@PathVariable Long id) {
        return this.doGet(id);
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
        return this.doDelete(id);
    }

    @RequestMapping(value = "/_search/requirementSkeletons/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RequirementSkeleton> search(@PathVariable String query) {
        return this.doSearch(query);
    }

    @RequestMapping(value = "/requirementSkeletons/foo/{shortName}",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RequirementSkeleton> foo(@PathVariable String shortName) {
        log.warn("Not implemented");
        return null;
    }

    @Override
    protected URI getLocation(RequirementSkeleton dto) throws URISyntaxException {
        return new URI("/api/requirementSkeletons/" + dto.getId().get());
    }
}
