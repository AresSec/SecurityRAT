package org.appsec.securityrat.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.TrainingBranchNode;

@RestController
@RequestMapping("/api")
@Slf4j
public class TrainingBranchNodeResource {
    @RequestMapping(value = "/trainingBranchNodes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingBranchNode> create(@RequestBody TrainingBranchNode trainingBranchNode) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingBranchNodes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingBranchNode> update(@RequestBody TrainingBranchNode trainingBranchNode) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingBranchNodes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TrainingBranchNode> getAll() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingBranchNodes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingBranchNode> get(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingBranchNodes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/_search/trainingBranchNodes/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TrainingBranchNode> search(@PathVariable String query) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/TrainingBranchNodeByTrainingTreeNode/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingBranchNode> getTrainingBranchNodeByTrainingTreeNode(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }
}
