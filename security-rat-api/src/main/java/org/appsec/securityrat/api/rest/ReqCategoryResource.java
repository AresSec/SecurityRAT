package org.appsec.securityrat.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.ReqCategory;


@RestController
@RequestMapping("/api")
@Slf4j
public class ReqCategoryResource {
    @RequestMapping(value = "/reqCategorys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReqCategory> create(@RequestBody ReqCategory reqCategory) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/reqCategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReqCategory> update(@RequestBody ReqCategory reqCategory) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/reqCategorys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReqCategory> getAll() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/reqCategorys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReqCategory> get(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/reqCategorys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/_search/reqCategorys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReqCategory> search(@PathVariable String query) {
        log.warn("Not implemented");
        return null;
    }
}
