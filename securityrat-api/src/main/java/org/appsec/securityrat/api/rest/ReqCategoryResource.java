package org.appsec.securityrat.api.rest;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import org.appsec.securityrat.api.ReqCategoryProvider;
import org.appsec.securityrat.api.dto.ReqCategory;


@RestController
@RequestMapping("/api")
public class ReqCategoryResource extends AbstractResourceBase<Long, ReqCategory> {
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private ReqCategoryProvider dtoProvider;
    
    public ReqCategoryResource() {
        super("reqCategory");
    }
    
    @RequestMapping(value = "/reqCategorys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody ReqCategory reqCategory) throws URISyntaxException {
        return this.doCreate(reqCategory);
    }

    @RequestMapping(value = "/reqCategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody ReqCategory reqCategory) throws URISyntaxException {
        return this.doUpdate(reqCategory);
    }

    @RequestMapping(value = "/reqCategorys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        return this.doGetAll();
    }

    @RequestMapping(value = "/reqCategorys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@PathVariable Long id) {
        return this.doGet(id);
    }

    @RequestMapping(value = "/reqCategorys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return this.doDelete(id);
    }

    @RequestMapping(value = "/_search/reqCategorys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(@PathVariable String query) {
        return this.doSearch(query);
    }

    @Override
    protected URI getLocation(ReqCategory dto) throws URISyntaxException {
        return new URI("/api/reqCategorys/" + dto.getId().get());
    }
}
