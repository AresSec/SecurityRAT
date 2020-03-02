package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.domain.TrainingTreeNode;
import org.appsec.securityrat.repository.TrainingTreeNodeRepository;
import org.appsec.securityrat.repository.search.TrainingTreeNodeSearchRepository;
import org.appsec.securityrat.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link org.appsec.securityrat.domain.TrainingTreeNode}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TrainingTreeNodeResource {

    private final Logger log = LoggerFactory.getLogger(TrainingTreeNodeResource.class);

    private static final String ENTITY_NAME = "trainingTreeNode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrainingTreeNodeRepository trainingTreeNodeRepository;

    private final TrainingTreeNodeSearchRepository trainingTreeNodeSearchRepository;

    public TrainingTreeNodeResource(TrainingTreeNodeRepository trainingTreeNodeRepository, TrainingTreeNodeSearchRepository trainingTreeNodeSearchRepository) {
        this.trainingTreeNodeRepository = trainingTreeNodeRepository;
        this.trainingTreeNodeSearchRepository = trainingTreeNodeSearchRepository;
    }

    /**
     * {@code POST  /training-tree-nodes} : Create a new trainingTreeNode.
     *
     * @param trainingTreeNode the trainingTreeNode to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trainingTreeNode, or with status {@code 400 (Bad Request)} if the trainingTreeNode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/training-tree-nodes")
    public ResponseEntity<TrainingTreeNode> createTrainingTreeNode(@RequestBody TrainingTreeNode trainingTreeNode) throws URISyntaxException {
        log.debug("REST request to save TrainingTreeNode : {}", trainingTreeNode);
        if (trainingTreeNode.getId() != null) {
            throw new BadRequestAlertException("A new trainingTreeNode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainingTreeNode result = trainingTreeNodeRepository.save(trainingTreeNode);
        trainingTreeNodeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/training-tree-nodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /training-tree-nodes} : Updates an existing trainingTreeNode.
     *
     * @param trainingTreeNode the trainingTreeNode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainingTreeNode,
     * or with status {@code 400 (Bad Request)} if the trainingTreeNode is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trainingTreeNode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/training-tree-nodes")
    public ResponseEntity<TrainingTreeNode> updateTrainingTreeNode(@RequestBody TrainingTreeNode trainingTreeNode) throws URISyntaxException {
        log.debug("REST request to update TrainingTreeNode : {}", trainingTreeNode);
        if (trainingTreeNode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TrainingTreeNode result = trainingTreeNodeRepository.save(trainingTreeNode);
        trainingTreeNodeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trainingTreeNode.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /training-tree-nodes} : get all the trainingTreeNodes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trainingTreeNodes in body.
     */
    @GetMapping("/training-tree-nodes")
    public List<TrainingTreeNode> getAllTrainingTreeNodes() {
        log.debug("REST request to get all TrainingTreeNodes");
        return trainingTreeNodeRepository.findAll();
    }

    /**
     * {@code GET  /training-tree-nodes/:id} : get the "id" trainingTreeNode.
     *
     * @param id the id of the trainingTreeNode to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trainingTreeNode, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/training-tree-nodes/{id}")
    public ResponseEntity<TrainingTreeNode> getTrainingTreeNode(@PathVariable Long id) {
        log.debug("REST request to get TrainingTreeNode : {}", id);
        Optional<TrainingTreeNode> trainingTreeNode = trainingTreeNodeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(trainingTreeNode);
    }

    /**
     * {@code DELETE  /training-tree-nodes/:id} : delete the "id" trainingTreeNode.
     *
     * @param id the id of the trainingTreeNode to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/training-tree-nodes/{id}")
    public ResponseEntity<Void> deleteTrainingTreeNode(@PathVariable Long id) {
        log.debug("REST request to delete TrainingTreeNode : {}", id);
        trainingTreeNodeRepository.deleteById(id);
        trainingTreeNodeSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/training-tree-nodes?query=:query} : search for the trainingTreeNode corresponding
     * to the query.
     *
     * @param query the query of the trainingTreeNode search.
     * @return the result of the search.
     */
    @GetMapping("/_search/training-tree-nodes")
    public List<TrainingTreeNode> searchTrainingTreeNodes(@RequestParam String query) {
        log.debug("REST request to search TrainingTreeNodes for query {}", query);
        return StreamSupport
            .stream(trainingTreeNodeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
