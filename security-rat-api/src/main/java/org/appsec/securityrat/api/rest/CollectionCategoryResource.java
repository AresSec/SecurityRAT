package org.appsec.securityrat.api.rest;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.CollectionCategoryProvider;
import org.appsec.securityrat.api.dto.CollectionCategory;


@RestController
@RequestMapping("/api")
@Slf4j
public class CollectionCategoryResource extends AbstractResourceBase<Long, CollectionCategory> {
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private CollectionCategoryProvider dtoProvider;
    
    public CollectionCategoryResource() {
        super("collectionCategory");
    }
    
    @RequestMapping(value = "/collectionCategorys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionCategory> create(@RequestBody CollectionCategory collectionCategory) throws URISyntaxException {
        return this.doCreate(collectionCategory);
    }

    @RequestMapping(value = "/collectionCategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionCategory> update(@RequestBody CollectionCategory collectionCategory) throws URISyntaxException {
        return this.doUpdate(collectionCategory);
    }

    @RequestMapping(value = "/collectionCategorys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CollectionCategory> getAll() {
        return this.doGetAll();
    }

    @RequestMapping(value = "/collectionCategorys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionCategory> get(@PathVariable Long id) {
        return this.doGet(id);
    }

    @RequestMapping(value = "/collectionCategorys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return this.doDelete(id);
    }

    @RequestMapping(value = "/_search/collectionCategorys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CollectionCategory> search(@PathVariable String query) {
        return this.doSearch(query);
    }

    @Override
    protected URI getLocation(CollectionCategory dto) throws URISyntaxException {
        return new URI("/api/collectionCategorys/" + dto.getId().get());
    }
}