package org.appsec.securityrat.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.rest.Logger;

@RestController
@RequestMapping("/api")
@Slf4j
public class LogsResource {

    @RequestMapping(value = "/logs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Logger> getList() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/logs",
            method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeLevel(@RequestBody Logger jsonLogger) {
        log.warn("Not implemented");
    }
}
