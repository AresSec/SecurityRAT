package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.domain.TrainingBranchNode;
import org.appsec.securityrat.repository.TrainingBranchNodeRepository;
import org.appsec.securityrat.repository.search.TrainingBranchNodeSearchRepository;
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
 * REST controller for managing {@link org.appsec.securityrat.domain.TrainingBranchNode}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TrainingBranchNodeResource {

    private final Logger log = LoggerFactory.getLogger(TrainingBranchNodeResource.class);

    private static final String ENTITY_NAME = "trainingBranchNode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrainingBranchNodeRepository trainingBranchNodeRepository;

    private final TrainingBranchNodeSearchRepository trainingBranchNodeSearchRepository;

    public TrainingBranchNodeResource(TrainingBranchNodeRepository trainingBranchNodeRepository, TrainingBranchNodeSearchRepository trainingBranchNodeSearchRepository) {
        this.trainingBranchNodeRepository = trainingBranchNodeRepository;
        this.trainingBranchNodeSearchRepository = trainingBranchNodeSearchRepository;
    }

    /**
     * {@code POST  /training-branch-nodes} : Create a new trainingBranchNode.
     *
     * @param trainingBranchNode the trainingBranchNode to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trainingBranchNode, or with status {@code 400 (Bad Request)} if the trainingBranchNode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/training-branch-nodes")
    public ResponseEntity<TrainingBranchNode> createTrainingBranchNode(@RequestBody TrainingBranchNode trainingBranchNode) throws URISyntaxException {
        log.debug("REST request to save TrainingBranchNode : {}", trainingBranchNode);
        if (trainingBranchNode.getId() != null) {
            throw new BadRequestAlertException("A new trainingBranchNode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainingBranchNode result = trainingBranchNodeRepository.save(trainingBranchNode);
        trainingBranchNodeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/training-branch-nodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /training-branch-nodes} : Updates an existing trainingBranchNode.
     *
     * @param trainingBranchNode the trainingBranchNode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainingBranchNode,
     * or with status {@code 400 (Bad Request)} if the trainingBranchNode is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trainingBranchNode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/training-branch-nodes")
    public ResponseEntity<TrainingBranchNode> updateTrainingBranchNode(@RequestBody TrainingBranchNode trainingBranchNode) throws URISyntaxException {
        log.debug("REST request to update TrainingBranchNode : {}", trainingBranchNode);
        if (trainingBranchNode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TrainingBranchNode result = trainingBranchNodeRepository.save(trainingBranchNode);
        trainingBranchNodeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trainingBranchNode.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /training-branch-nodes} : get all the trainingBranchNodes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trainingBranchNodes in body.
     */
    @GetMapping("/training-branch-nodes")
    public List<TrainingBranchNode> getAllTrainingBranchNodes() {
        log.debug("REST request to get all TrainingBranchNodes");
        return trainingBranchNodeRepository.findAll();
    }

    /**
     * {@code GET  /training-branch-nodes/:id} : get the "id" trainingBranchNode.
     *
     * @param id the id of the trainingBranchNode to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trainingBranchNode, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/training-branch-nodes/{id}")
    public ResponseEntity<TrainingBranchNode> getTrainingBranchNode(@PathVariable Long id) {
        log.debug("REST request to get TrainingBranchNode : {}", id);
        Optional<TrainingBranchNode> trainingBranchNode = trainingBranchNodeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(trainingBranchNode);
    }

    /**
     * {@code DELETE  /training-branch-nodes/:id} : delete the "id" trainingBranchNode.
     *
     * @param id the id of the trainingBranchNode to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/training-branch-nodes/{id}")
    public ResponseEntity<Void> deleteTrainingBranchNode(@PathVariable Long id) {
        log.debug("REST request to delete TrainingBranchNode : {}", id);
        trainingBranchNodeRepository.deleteById(id);
        trainingBranchNodeSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/training-branch-nodes?query=:query} : search for the trainingBranchNode corresponding
     * to the query.
     *
     * @param query the query of the trainingBranchNode search.
     * @return the result of the search.
     */
    @GetMapping("/_search/training-branch-nodes")
    public List<TrainingBranchNode> searchTrainingBranchNodes(@RequestParam String query) {
        log.debug("REST request to search TrainingBranchNodes for query {}", query);
        return StreamSupport
            .stream(trainingBranchNodeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
