package org.appsec.securityrat.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.TagCategory;

@RestController
@RequestMapping("/api")
@Slf4j
public class TagCategoryResource {
    @RequestMapping(value = "/tagCategorys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TagCategory> create(@RequestBody TagCategory tagCategory) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/tagCategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TagCategory> update(@RequestBody TagCategory tagCategory) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/tagCategorys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TagCategory> getAll() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/tagCategorys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TagCategory> get(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/tagCategorys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/_search/tagCategorys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TagCategory> search(@PathVariable String query) {
        log.warn("Not implemented");
        return null;
    }
}
