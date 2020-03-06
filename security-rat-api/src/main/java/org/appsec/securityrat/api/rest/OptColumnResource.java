package org.appsec.securityrat.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.OptColumn;

@RestController
@RequestMapping("/api")
@Slf4j
public class OptColumnResource {
    @RequestMapping(value = "/optColumns",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OptColumn> create(@RequestBody OptColumn optColumn) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/optColumns",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OptColumn> update(@RequestBody OptColumn optColumn) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/optColumns",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OptColumn> getAll() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/optColumns/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OptColumn> get(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/optColumns/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/_search/optColumns/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OptColumn> search(@PathVariable String query) {
        log.warn("Not implemented");
        return null;
    }
}
