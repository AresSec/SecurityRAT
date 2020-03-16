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
import org.appsec.securityrat.api.CollectionInstanceProvider;
import org.appsec.securityrat.api.dto.CollectionInstance;


@RestController
@RequestMapping("/api")
@Slf4j
public class CollectionInstanceResource extends AbstractResourceBase<Long, CollectionInstance> {
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private CollectionInstanceProvider dtoProvider;
    
    public CollectionInstanceResource() {
        super("collectionInstance");
    }
    
    @RequestMapping(value = "/collectionInstances",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionInstance> create(@RequestBody CollectionInstance collectionInstance) throws URISyntaxException {
        return this.doCreate(collectionInstance);
    }

    @RequestMapping(value = "/collectionInstances",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionInstance> update(@RequestBody CollectionInstance collectionInstance) throws URISyntaxException {
        return this.doUpdate(collectionInstance);
    }

    @RequestMapping(value = "/collectionInstances",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CollectionInstance> getAll() {
        return this.doGetAll();
    }

    @RequestMapping(value = "/collectionInstances/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionInstance> get(@PathVariable Long id) {
        return this.doGet(id);
    }

    @RequestMapping(value = "/collectionInstances/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return this.doDelete(id);
    }

    @RequestMapping(value = "/_search/collectionInstances/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CollectionInstance> search(@PathVariable String query) {
        return this.doSearch(query);
    }

    @Override
    protected URI getLocation(CollectionInstance dto) throws URISyntaxException {
        return new URI("/api/collectionInstances/" + dto.getId().get());
    }
}
