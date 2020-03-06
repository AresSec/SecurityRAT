package org.appsec.securityrat.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.OptColumnType;

@RestController
@RequestMapping("/api")
@Slf4j
public class OptColumnTypeResource {
    @RequestMapping(value = "/optColumnTypes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OptColumnType> create(@RequestBody OptColumnType optColumnType) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/optColumnTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OptColumnType> update(@RequestBody OptColumnType optColumnType) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/optColumnTypes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OptColumnType> getAll() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/optColumnTypes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OptColumnType> get(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/optColumnTypes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/_search/optColumnTypes/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OptColumnType> search(@PathVariable String query) {
        log.warn("Not implemented");
        return null;
    }
}
