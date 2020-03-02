package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.domain.TrainingCategoryNode;
import org.appsec.securityrat.repository.TrainingCategoryNodeRepository;
import org.appsec.securityrat.repository.search.TrainingCategoryNodeSearchRepository;
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
 * REST controller for managing {@link org.appsec.securityrat.domain.TrainingCategoryNode}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TrainingCategoryNodeResource {

    private final Logger log = LoggerFactory.getLogger(TrainingCategoryNodeResource.class);

    private static final String ENTITY_NAME = "trainingCategoryNode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrainingCategoryNodeRepository trainingCategoryNodeRepository;

    private final TrainingCategoryNodeSearchRepository trainingCategoryNodeSearchRepository;

    public TrainingCategoryNodeResource(TrainingCategoryNodeRepository trainingCategoryNodeRepository, TrainingCategoryNodeSearchRepository trainingCategoryNodeSearchRepository) {
        this.trainingCategoryNodeRepository = trainingCategoryNodeRepository;
        this.trainingCategoryNodeSearchRepository = trainingCategoryNodeSearchRepository;
    }

    /**
     * {@code POST  /training-category-nodes} : Create a new trainingCategoryNode.
     *
     * @param trainingCategoryNode the trainingCategoryNode to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trainingCategoryNode, or with status {@code 400 (Bad Request)} if the trainingCategoryNode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/training-category-nodes")
    public ResponseEntity<TrainingCategoryNode> createTrainingCategoryNode(@RequestBody TrainingCategoryNode trainingCategoryNode) throws URISyntaxException {
        log.debug("REST request to save TrainingCategoryNode : {}", trainingCategoryNode);
        if (trainingCategoryNode.getId() != null) {
            throw new BadRequestAlertException("A new trainingCategoryNode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainingCategoryNode result = trainingCategoryNodeRepository.save(trainingCategoryNode);
        trainingCategoryNodeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/training-category-nodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /training-category-nodes} : Updates an existing trainingCategoryNode.
     *
     * @param trainingCategoryNode the trainingCategoryNode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainingCategoryNode,
     * or with status {@code 400 (Bad Request)} if the trainingCategoryNode is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trainingCategoryNode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/training-category-nodes")
    public ResponseEntity<TrainingCategoryNode> updateTrainingCategoryNode(@RequestBody TrainingCategoryNode trainingCategoryNode) throws URISyntaxException {
        log.debug("REST request to update TrainingCategoryNode : {}", trainingCategoryNode);
        if (trainingCategoryNode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TrainingCategoryNode result = trainingCategoryNodeRepository.save(trainingCategoryNode);
        trainingCategoryNodeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trainingCategoryNode.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /training-category-nodes} : get all the trainingCategoryNodes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trainingCategoryNodes in body.
     */
    @GetMapping("/training-category-nodes")
    public List<TrainingCategoryNode> getAllTrainingCategoryNodes() {
        log.debug("REST request to get all TrainingCategoryNodes");
        return trainingCategoryNodeRepository.findAll();
    }

    /**
     * {@code GET  /training-category-nodes/:id} : get the "id" trainingCategoryNode.
     *
     * @param id the id of the trainingCategoryNode to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trainingCategoryNode, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/training-category-nodes/{id}")
    public ResponseEntity<TrainingCategoryNode> getTrainingCategoryNode(@PathVariable Long id) {
        log.debug("REST request to get TrainingCategoryNode : {}", id);
        Optional<TrainingCategoryNode> trainingCategoryNode = trainingCategoryNodeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(trainingCategoryNode);
    }

    /**
     * {@code DELETE  /training-category-nodes/:id} : delete the "id" trainingCategoryNode.
     *
     * @param id the id of the trainingCategoryNode to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/training-category-nodes/{id}")
    public ResponseEntity<Void> deleteTrainingCategoryNode(@PathVariable Long id) {
        log.debug("REST request to delete TrainingCategoryNode : {}", id);
        trainingCategoryNodeRepository.deleteById(id);
        trainingCategoryNodeSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/training-category-nodes?query=:query} : search for the trainingCategoryNode corresponding
     * to the query.
     *
     * @param query the query of the trainingCategoryNode search.
     * @return the result of the search.
     */
    @GetMapping("/_search/training-category-nodes")
    public List<TrainingCategoryNode> searchTrainingCategoryNodes(@RequestParam String query) {
        log.debug("REST request to search TrainingCategoryNodes for query {}", query);
        return StreamSupport
            .stream(trainingCategoryNodeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
