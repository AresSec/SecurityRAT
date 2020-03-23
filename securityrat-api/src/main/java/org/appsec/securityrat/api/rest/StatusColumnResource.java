package org.appsec.securityrat.api.rest;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.appsec.securityrat.api.dto.rest.StatusColumn;

@RestController
@RequestMapping("/api")
public class StatusColumnResource
        extends AbstractResourceBase<Long, StatusColumn> {
    
    public StatusColumnResource() {
        super("statusColumn");
    }
    
    @RequestMapping(value = "/statusColumns",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody StatusColumn statusColumn)
            throws URISyntaxException {
        return this.doCreate(statusColumn);
    }

    @RequestMapping(value = "/statusColumns",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody StatusColumn statusColumn)
            throws URISyntaxException {
        return this.doCreate(statusColumn);
    }

    @RequestMapping(value = "/statusColumns",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        return this.doGetAll();
    }

    @RequestMapping(value = "/statusColumns/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@PathVariable Long id) {
        return this.doGet(id);
    }

    @RequestMapping(value = "/statusColumns/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return this.doDelete(id);
    }

    @RequestMapping(value = "/_search/statusColumns/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(@PathVariable String query) {
        return this.doSearch(query);
    }

    @Override
    protected URI getLocation(StatusColumn dto) throws URISyntaxException {
        return new URI("/api/statusColumns/" + dto.getId().get());
    }
}
