package org.appsec.securityrat.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.TrainingCategoryNode;

@RestController
@RequestMapping("/api")
@Slf4j
public class TrainingCategoryNodeResource {
    @RequestMapping(value = "/trainingCategoryNodes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingCategoryNode> create(@RequestBody TrainingCategoryNode trainingCategoryNode) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingCategoryNodes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingCategoryNode> update(@RequestBody TrainingCategoryNode trainingCategoryNode) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingCategoryNodes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TrainingCategoryNode> getAll() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingCategoryNodes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingCategoryNode> get(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingCategoryNodes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/_search/trainingCategoryNodes/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TrainingCategoryNode> search(@PathVariable String query) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/TrainingCategoryNodeByTrainingTreeNode/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingCategoryNode> getTrainingCategoryNodeByTrainingTreeNode(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }
}
