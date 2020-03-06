package org.appsec.securityrat.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.StatusColumn;

@RestController
@RequestMapping("/api")
@Slf4j
public class StatusColumnResource {
    @RequestMapping(value = "/statusColumns",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatusColumn> create(@RequestBody StatusColumn statusColumn) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/statusColumns",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatusColumn> update(@RequestBody StatusColumn statusColumn) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/statusColumns",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StatusColumn> getAll() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/statusColumns/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatusColumn> get(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/statusColumns/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/_search/statusColumns/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StatusColumn> search(@PathVariable String query) {
        log.warn("Not implemented");
        return null;
    }
}
