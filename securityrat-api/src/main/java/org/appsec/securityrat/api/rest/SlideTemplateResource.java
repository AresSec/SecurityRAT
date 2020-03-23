package org.appsec.securityrat.api.rest;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.appsec.securityrat.api.dto.rest.SlideTemplate;

@RestController
@RequestMapping("/api")
public class SlideTemplateResource
        extends AbstractResourceBase<Long, SlideTemplate> {
    
    public SlideTemplateResource() {
        super("slideTemplate");
    }
    
    @RequestMapping(value = "/slideTemplates",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody SlideTemplate slideTemplate) throws URISyntaxException {
        return this.doCreate(slideTemplate);
    }

    @RequestMapping(value = "/slideTemplates",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody SlideTemplate slideTemplate) throws URISyntaxException {
        return this.doUpdate(slideTemplate);
    }

    @RequestMapping(value = "/slideTemplates",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        return this.doGetAll();
    }

    @RequestMapping(value = "/slideTemplates/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@PathVariable Long id) {
        return this.doGet(id);
    }

    @RequestMapping(value = "/slideTemplates/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return this.doDelete(id);
    }

    @RequestMapping(value = "/_search/slideTemplates/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(@PathVariable String query) {
        return this.doSearch(query);
    }

    @Override
    protected URI getLocation(SlideTemplate dto) throws URISyntaxException {
        return new URI("/api/slideTemplates/" + dto.getId().get());
    }
}
