package org.appsec.securityrat.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.rest.Training;

@RestController
@RequestMapping("/api")
@Slf4j
public class TrainingResource {
    @RequestMapping(value = "/trainings",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Training> create(@RequestBody Training training) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Training> update(@RequestBody Training training) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainings",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Training> getAll() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainings/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Training> get(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainings/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/_search/trainings/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Training> search(@PathVariable String query) {
        log.warn("Not implemented");
        return null;
    }
}
