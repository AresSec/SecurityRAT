package org.appsec.securityrat.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.rest.TrainingTreeNode;
import org.appsec.securityrat.api.dto.rest.TrainingTreeStatus;

@RestController
@RequestMapping("/api")
@Slf4j
public class TrainingTreeNodeResource {
    @RequestMapping(value = "/trainingTreeNodes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingTreeNode> create(@RequestBody TrainingTreeNode trainingTreeNode) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingTreeNodes", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingTreeNode> update(@RequestBody TrainingTreeNode trainingTreeNode) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingTreeNodeUpdate/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingTreeStatus> updateTreeReadOnly(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingTreeNodeUpdate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingTreeStatus> updateTree(@RequestBody TrainingTreeNode id_wrapped) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingTreeNodes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TrainingTreeNode> getAll() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingTreeNodes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingTreeNode> get(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingTreeNodesWithPreparedContent/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingTreeNode> getWithContent(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/trainingTreeNodes/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/_search/trainingTreeNodes/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TrainingTreeNode> search(@PathVariable String query) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/TrainingTreeNode/rootNode/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingTreeNode> getTrainingRoot(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/TrainingTreeNode/childrenOf/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TrainingTreeNode>> getChildrenOf(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }
}
