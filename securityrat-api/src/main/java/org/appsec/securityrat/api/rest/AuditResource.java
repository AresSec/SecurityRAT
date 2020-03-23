package org.appsec.securityrat.api.rest;

import java.time.LocalDate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.rest.AuditEvent;

@RestController
@RequestMapping("/api")
@Slf4j
public class AuditResource {
    @RequestMapping(value = "/audits/all",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AuditEvent> findAll() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/audits/byDates",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AuditEvent> findByDates(@RequestParam(value = "fromDate") LocalDate fromDate,
                                    @RequestParam(value = "toDate") LocalDate toDate) {
        log.warn("Not implemented");
        return null;
    }
}
