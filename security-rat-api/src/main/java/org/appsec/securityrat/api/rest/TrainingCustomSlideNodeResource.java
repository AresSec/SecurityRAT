package org.appsec.securityrat.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.TrainingCustomSlideNode;

@RestController
@RequestMapping("/api")
@Slf4j
public class TrainingCustomSlideNodeResource {
    @RequestMapping(value = "/trainingCustomSlideNodes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingCustomSlideNode> create(@RequestBody TrainingCustomSlideNode trainingCustomSlideNode) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingCustomSlideNodes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingCustomSlideNode> update(@RequestBody TrainingCustomSlideNode trainingCustomSlideNode) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingCustomSlideNodes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TrainingCustomSlideNode> getAll() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingCustomSlideNodes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingCustomSlideNode> get(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingCustomSlideNodes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/_search/trainingCustomSlideNodes/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TrainingCustomSlideNode> search(@PathVariable String query) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/TrainingCustomSlideNodeByTrainingTreeNode/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingCustomSlideNode> getTrainingCustomSlideNodeByTrainingTreeNode(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }
}
