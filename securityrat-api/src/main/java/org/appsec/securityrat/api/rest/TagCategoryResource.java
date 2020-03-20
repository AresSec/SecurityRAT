package org.appsec.securityrat.api.rest;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.TagCategoryProvider;
import org.appsec.securityrat.api.dto.TagCategory;

@RestController
@RequestMapping("/api")
@Slf4j
public class TagCategoryResource extends AbstractResourceBase<Long, TagCategory> {
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private TagCategoryProvider dtoProvider;
    
    public TagCategoryResource() {
        super("tagCategory");
    }
    
    @RequestMapping(value = "/tagCategorys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody TagCategory tagCategory) throws URISyntaxException {
        return this.doCreate(tagCategory);
    }

    @RequestMapping(value = "/tagCategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody TagCategory tagCategory) throws URISyntaxException {
        return this.doUpdate(tagCategory);
    }

    @RequestMapping(value = "/tagCategorys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        return this.doGetAll();
    }

    @RequestMapping(value = "/tagCategorys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@PathVariable Long id) {
        return this.doGet(id);
    }

    @RequestMapping(value = "/tagCategorys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return this.doDelete(id);
    }

    @RequestMapping(value = "/_search/tagCategorys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(@PathVariable String query) {
        return this.doSearch(query);
    }

    @Override
    protected URI getLocation(TagCategory dto) throws URISyntaxException {
        return new URI("/api/tagCategorys/" + dto.getId().get());
    }
}
