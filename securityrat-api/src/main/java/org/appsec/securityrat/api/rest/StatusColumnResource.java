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
import org.appsec.securityrat.api.StatusColumnProvider;
import org.appsec.securityrat.api.dto.StatusColumn;

@RestController
@RequestMapping("/api")
@Slf4j
public class StatusColumnResource extends AbstractResourceBase<Long, StatusColumn> {
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private StatusColumnProvider dtoProvider;
    
    public StatusColumnResource() {
        super("statusColumn");
    }
    
    @RequestMapping(value = "/statusColumns",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatusColumn> create(@RequestBody StatusColumn statusColumn) throws URISyntaxException {
        return this.doCreate(statusColumn);
    }

    @RequestMapping(value = "/statusColumns",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatusColumn> update(@RequestBody StatusColumn statusColumn) throws URISyntaxException {
        return this.doCreate(statusColumn);
    }

    @RequestMapping(value = "/statusColumns",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StatusColumn> getAll() {
        return this.doGetAll();
    }

    @RequestMapping(value = "/statusColumns/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatusColumn> get(@PathVariable Long id) {
        return this.doGet(id);
    }

    @RequestMapping(value = "/statusColumns/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return this.doDelete(id);
    }

    @RequestMapping(value = "/_search/statusColumns/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StatusColumn> search(@PathVariable String query) {
        return this.doSearch(query);
    }

    @Override
    protected URI getLocation(StatusColumn dto) throws URISyntaxException {
        return new URI("/api/statusColumns/" + dto.getId().get());
    }
}
