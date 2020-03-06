package org.appsec.securityrat.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.StatusColumnValue;

@RestController
@RequestMapping("/api")
@Slf4j
public class StatusColumnValueResource {
    @RequestMapping(value = "/statusColumnValues",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatusColumnValue> create(@RequestBody StatusColumnValue statusColumnValue) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/statusColumnValues",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatusColumnValue> update(@RequestBody StatusColumnValue statusColumnValue) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/statusColumnValues",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StatusColumnValue> getAll() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/statusColumnValues/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatusColumnValue> get(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/statusColumnValues/statusColumn/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<StatusColumnValue>> getStatusColumn(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/statusColumnValues/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/_search/statusColumnValues/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StatusColumnValue> search(@PathVariable String query) {
        log.warn("Not implemented");
        return null;
    }
}
