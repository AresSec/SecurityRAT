package org.appsec.securityrat.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.rest.TrainingGeneratedSlideNode;

@RestController
@RequestMapping("/api")
@Slf4j
public class TrainingGeneratedSlideNodeResource {
    @RequestMapping(value = "/trainingGeneratedSlideNodes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingGeneratedSlideNode> create(@RequestBody TrainingGeneratedSlideNode trainingGeneratedSlideNode) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingGeneratedSlideNodes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingGeneratedSlideNode> update(@RequestBody TrainingGeneratedSlideNode trainingGeneratedSlideNode) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingGeneratedSlideNodes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TrainingGeneratedSlideNode> getAll() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingGeneratedSlideNodes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingGeneratedSlideNode> get(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingGeneratedSlideNodes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/_search/trainingGeneratedSlideNodes/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TrainingGeneratedSlideNode> search(@PathVariable String query) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/TrainingGeneratedSlideNodeByTrainingTreeNode/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingGeneratedSlideNode> getTrainingGeneratedSlideNodeByTrainingTreeNode(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }
}
