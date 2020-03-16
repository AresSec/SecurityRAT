package org.appsec.securityrat.api.rest;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.TagInstanceProvider;
import org.appsec.securityrat.api.dto.TagInstance;

@RestController
@RequestMapping("/api")
@Slf4j
public class TagInstanceResource extends AbstractResourceBase<Long, TagInstance> {
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private TagInstanceProvider dtoProvider;
    
    public TagInstanceResource() {
        super("tagInstance");
    }
    
    @RequestMapping(value = "/tagInstances",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TagInstance> create(@RequestBody TagInstance tagInstance) throws URISyntaxException {
        return this.doCreate(tagInstance);
    }

    @RequestMapping(value = "/tagInstances",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TagInstance> update(@RequestBody TagInstance tagInstance) throws URISyntaxException {
        return this.doUpdate(tagInstance);
    }

    @RequestMapping(value = "/tagInstances",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TagInstance> getAll() {
        return this.doGetAll();
    }

    @RequestMapping(value = "/tagInstances/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TagInstance> get(@PathVariable Long id) {
        return this.doGet(id);
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
        return this.doDelete(id);
    }

    @RequestMapping(value = "/_search/tagInstances/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TagInstance> search(@PathVariable String query) {
        return this.doSearch(query);
    }

    @Override
    protected URI getLocation(TagInstance dto) throws URISyntaxException {
        return new URI("/api/tagInstances/" + dto.getId().get());
    }
}
