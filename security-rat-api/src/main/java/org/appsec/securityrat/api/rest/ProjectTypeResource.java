package org.appsec.securityrat.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.ProjectType;


/**
 * REST controller for managing ProjectType.
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class ProjectTypeResource {
    @RequestMapping(value = "/projectTypes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectType> create(@RequestBody ProjectType projectType) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/projectTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectType> update(@RequestBody ProjectType projectType) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/projectTypes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProjectType> getAll() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/projectTypes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectType> get(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/projectTypes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/_search/projectTypes/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProjectType> search(@PathVariable String query) {
        log.warn("Not implemented");
        return null;
    }
}
