package org.appsec.securityrat.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.rest.TrainingRequirementNode;

@RestController
@RequestMapping("/api")
@Slf4j
public class TrainingRequirementNodeResource {
    @RequestMapping(value = "/trainingRequirementNodes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingRequirementNode> create(@RequestBody TrainingRequirementNode trainingRequirementNode) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingRequirementNodes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingRequirementNode> update(@RequestBody TrainingRequirementNode trainingRequirementNode) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingRequirementNodes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TrainingRequirementNode> getAll() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingRequirementNodes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingRequirementNode> get(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingRequirementNodes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/_search/trainingRequirementNodes/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TrainingRequirementNode> search(@PathVariable String query) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/TrainingRequirementNodeByTrainingTreeNode/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingRequirementNode> getTrainingRequirementNodeByTrainingTreeNode(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }
}
