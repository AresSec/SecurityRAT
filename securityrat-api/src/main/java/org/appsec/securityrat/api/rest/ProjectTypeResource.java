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
import org.appsec.securityrat.api.ProjectTypeProvider;
import org.appsec.securityrat.api.dto.ProjectType;


/**
 * REST controller for managing ProjectType.
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class ProjectTypeResource extends AbstractResourceBase<Long, ProjectType> {
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private ProjectTypeProvider dtoProvider;
    
    public ProjectTypeResource() {
        super("projectType");
    }
    
    @RequestMapping(value = "/projectTypes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody ProjectType projectType) throws URISyntaxException {
        return this.doCreate(projectType);
    }

    @RequestMapping(value = "/projectTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody ProjectType projectType) throws URISyntaxException {
        return this.doUpdate(projectType);
    }

    @RequestMapping(value = "/projectTypes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        return this.doGetAll();
    }

    @RequestMapping(value = "/projectTypes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@PathVariable Long id) {
        return this.doGet(id);
    }

    @RequestMapping(value = "/projectTypes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return this.doDelete(id);
    }

    @RequestMapping(value = "/_search/projectTypes/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(@PathVariable String query) {
        return this.doSearch(query);
    }

    @Override
    protected URI getLocation(ProjectType dto) throws URISyntaxException {
        return new URI("/api/projectTypes/" + dto.getId().get());
    }
}
