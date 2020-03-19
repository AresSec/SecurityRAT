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
import org.appsec.securityrat.api.OptColumnProvider;
import org.appsec.securityrat.api.dto.OptColumn;

@RestController
@RequestMapping("/api")
@Slf4j
public class OptColumnResource extends AbstractResourceBase<Long, OptColumn> {
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private OptColumnProvider dtoProvider;
    
    public OptColumnResource() {
        super("optColumn");
    }
    
    @RequestMapping(value = "/optColumns",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody OptColumn optColumn) throws URISyntaxException {
        return this.doCreate(optColumn);
    }

    @RequestMapping(value = "/optColumns",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody OptColumn optColumn) throws URISyntaxException {
        return this.doUpdate(optColumn);
    }

    @RequestMapping(value = "/optColumns",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        return this.doGetAll();
    }

    @RequestMapping(value = "/optColumns/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@PathVariable Long id) {
        return this.doGet(id);
    }

    @RequestMapping(value = "/optColumns/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return this.doDelete(id);
    }

    @RequestMapping(value = "/_search/optColumns/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(@PathVariable String query) {
        return this.doSearch(query);
    }

    @Override
    protected URI getLocation(OptColumn dto) throws URISyntaxException {
        return new URI("/api/optColumns/" + dto.getId().get());
    }
}
