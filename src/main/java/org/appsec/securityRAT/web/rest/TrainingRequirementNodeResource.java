package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.domain.TrainingRequirementNode;
import org.appsec.securityrat.repository.TrainingRequirementNodeRepository;
import org.appsec.securityrat.repository.search.TrainingRequirementNodeSearchRepository;
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
 * REST controller for managing {@link org.appsec.securityrat.domain.TrainingRequirementNode}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TrainingRequirementNodeResource {

    private final Logger log = LoggerFactory.getLogger(TrainingRequirementNodeResource.class);

    private static final String ENTITY_NAME = "trainingRequirementNode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrainingRequirementNodeRepository trainingRequirementNodeRepository;

    private final TrainingRequirementNodeSearchRepository trainingRequirementNodeSearchRepository;

    public TrainingRequirementNodeResource(TrainingRequirementNodeRepository trainingRequirementNodeRepository, TrainingRequirementNodeSearchRepository trainingRequirementNodeSearchRepository) {
        this.trainingRequirementNodeRepository = trainingRequirementNodeRepository;
        this.trainingRequirementNodeSearchRepository = trainingRequirementNodeSearchRepository;
    }

    /**
     * {@code POST  /training-requirement-nodes} : Create a new trainingRequirementNode.
     *
     * @param trainingRequirementNode the trainingRequirementNode to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trainingRequirementNode, or with status {@code 400 (Bad Request)} if the trainingRequirementNode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/training-requirement-nodes")
    public ResponseEntity<TrainingRequirementNode> createTrainingRequirementNode(@RequestBody TrainingRequirementNode trainingRequirementNode) throws URISyntaxException {
        log.debug("REST request to save TrainingRequirementNode : {}", trainingRequirementNode);
        if (trainingRequirementNode.getId() != null) {
            throw new BadRequestAlertException("A new trainingRequirementNode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainingRequirementNode result = trainingRequirementNodeRepository.save(trainingRequirementNode);
        trainingRequirementNodeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/training-requirement-nodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /training-requirement-nodes} : Updates an existing trainingRequirementNode.
     *
     * @param trainingRequirementNode the trainingRequirementNode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainingRequirementNode,
     * or with status {@code 400 (Bad Request)} if the trainingRequirementNode is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trainingRequirementNode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/training-requirement-nodes")
    public ResponseEntity<TrainingRequirementNode> updateTrainingRequirementNode(@RequestBody TrainingRequirementNode trainingRequirementNode) throws URISyntaxException {
        log.debug("REST request to update TrainingRequirementNode : {}", trainingRequirementNode);
        if (trainingRequirementNode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TrainingRequirementNode result = trainingRequirementNodeRepository.save(trainingRequirementNode);
        trainingRequirementNodeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trainingRequirementNode.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /training-requirement-nodes} : get all the trainingRequirementNodes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trainingRequirementNodes in body.
     */
    @GetMapping("/training-requirement-nodes")
    public List<TrainingRequirementNode> getAllTrainingRequirementNodes() {
        log.debug("REST request to get all TrainingRequirementNodes");
        return trainingRequirementNodeRepository.findAll();
    }

    /**
     * {@code GET  /training-requirement-nodes/:id} : get the "id" trainingRequirementNode.
     *
     * @param id the id of the trainingRequirementNode to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trainingRequirementNode, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/training-requirement-nodes/{id}")
    public ResponseEntity<TrainingRequirementNode> getTrainingRequirementNode(@PathVariable Long id) {
        log.debug("REST request to get TrainingRequirementNode : {}", id);
        Optional<TrainingRequirementNode> trainingRequirementNode = trainingRequirementNodeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(trainingRequirementNode);
    }

    /**
     * {@code DELETE  /training-requirement-nodes/:id} : delete the "id" trainingRequirementNode.
     *
     * @param id the id of the trainingRequirementNode to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/training-requirement-nodes/{id}")
    public ResponseEntity<Void> deleteTrainingRequirementNode(@PathVariable Long id) {
        log.debug("REST request to delete TrainingRequirementNode : {}", id);
        trainingRequirementNodeRepository.deleteById(id);
        trainingRequirementNodeSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/training-requirement-nodes?query=:query} : search for the trainingRequirementNode corresponding
     * to the query.
     *
     * @param query the query of the trainingRequirementNode search.
     * @return the result of the search.
     */
    @GetMapping("/_search/training-requirement-nodes")
    public List<TrainingRequirementNode> searchTrainingRequirementNodes(@RequestParam String query) {
        log.debug("REST request to search TrainingRequirementNodes for query {}", query);
        return StreamSupport
            .stream(trainingRequirementNodeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
