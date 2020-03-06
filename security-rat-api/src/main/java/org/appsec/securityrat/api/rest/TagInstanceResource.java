package org.appsec.securityrat.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.TagInstance;

@RestController
@RequestMapping("/api")
@Slf4j
public class TagInstanceResource {
    @RequestMapping(value = "/tagInstances",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TagInstance> create(@RequestBody TagInstance tagInstance) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/tagInstances",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TagInstance> update(@RequestBody TagInstance tagInstance) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/tagInstances",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TagInstance> getAll() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/tagInstances/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TagInstance> get(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/tagInstances/tagCategory/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<TagInstance>> getTagInstanceValues(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/tagInstances/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/_search/tagInstances/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TagInstance> search(@PathVariable String query) {
        log.warn("Not implemented");
        return null;
    }
}
