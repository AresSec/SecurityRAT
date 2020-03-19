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
import org.appsec.securityrat.api.StatusColumnValueProvider;
import org.appsec.securityrat.api.dto.StatusColumnValue;

@RestController
@RequestMapping("/api")
@Slf4j
public class StatusColumnValueResource extends AbstractResourceBase<Long, StatusColumnValue> {
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private StatusColumnValueProvider dtoProvider;
    
    public StatusColumnValueResource() {
        super("statusColumnValue");
    }
    
    @RequestMapping(value = "/statusColumnValues",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody StatusColumnValue statusColumnValue) throws URISyntaxException {
        return this.doCreate(statusColumnValue);
    }

    @RequestMapping(value = "/statusColumnValues",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody StatusColumnValue statusColumnValue) throws URISyntaxException {
        return this.doUpdate(statusColumnValue);
    }

    @RequestMapping(value = "/statusColumnValues",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        return this.doGetAll();
    }

    @RequestMapping(value = "/statusColumnValues/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@PathVariable Long id) {
        return this.doGet(id);
    }

    @RequestMapping(value = "/statusColumnValues/statusColumn/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getStatusColumn(@PathVariable Long id) {
        // TODO [luis.felger@bosch.com]: Implementation.
        
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/statusColumnValues/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return this.doDelete(id);
    }

    @RequestMapping(value = "/_search/statusColumnValues/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(@PathVariable String query) {
        return this.doSearch(query);
    }

    @Override
    protected URI getLocation(StatusColumnValue dto) throws URISyntaxException {
        return new URI("/api/statusColumnValues/" + dto.getId().get());
    }
}
