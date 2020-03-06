package org.appsec.securityrat.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.SlideTemplate;

@RestController
@RequestMapping("/api")
@Slf4j
public class SlideTemplateResource {
    @RequestMapping(value = "/slideTemplates",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SlideTemplate> create(@RequestBody SlideTemplate slideTemplate) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/slideTemplates",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SlideTemplate> update(@RequestBody SlideTemplate slideTemplate) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/slideTemplates",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SlideTemplate> getAll() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/slideTemplates/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SlideTemplate> get(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/slideTemplates/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/_search/slideTemplates/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SlideTemplate> search(@PathVariable String query) {
        log.warn("Not implemented");
        return null;
    }
}
