package org.appsec.securityrat.api.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.Authority;

@RestController
@RequestMapping("/admin-api")
@Slf4j
public class AuthorityResource {
    @RequestMapping(value = "/authorities",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Authority> getAll() {
        log.warn("Not implemented");
        return null;
    }
}
