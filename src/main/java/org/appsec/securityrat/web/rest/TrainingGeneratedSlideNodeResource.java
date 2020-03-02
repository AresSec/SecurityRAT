package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.domain.TrainingGeneratedSlideNode;
import org.appsec.securityrat.repository.TrainingGeneratedSlideNodeRepository;
import org.appsec.securityrat.repository.search.TrainingGeneratedSlideNodeSearchRepository;
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
 * REST controller for managing {@link org.appsec.securityrat.domain.TrainingGeneratedSlideNode}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TrainingGeneratedSlideNodeResource {

    private final Logger log = LoggerFactory.getLogger(TrainingGeneratedSlideNodeResource.class);

    private static final String ENTITY_NAME = "trainingGeneratedSlideNode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrainingGeneratedSlideNodeRepository trainingGeneratedSlideNodeRepository;

    private final TrainingGeneratedSlideNodeSearchRepository trainingGeneratedSlideNodeSearchRepository;

    public TrainingGeneratedSlideNodeResource(TrainingGeneratedSlideNodeRepository trainingGeneratedSlideNodeRepository, TrainingGeneratedSlideNodeSearchRepository trainingGeneratedSlideNodeSearchRepository) {
        this.trainingGeneratedSlideNodeRepository = trainingGeneratedSlideNodeRepository;
        this.trainingGeneratedSlideNodeSearchRepository = trainingGeneratedSlideNodeSearchRepository;
    }

    /**
     * {@code POST  /training-generated-slide-nodes} : Create a new trainingGeneratedSlideNode.
     *
     * @param trainingGeneratedSlideNode the trainingGeneratedSlideNode to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trainingGeneratedSlideNode, or with status {@code 400 (Bad Request)} if the trainingGeneratedSlideNode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/training-generated-slide-nodes")
    public ResponseEntity<TrainingGeneratedSlideNode> createTrainingGeneratedSlideNode(@RequestBody TrainingGeneratedSlideNode trainingGeneratedSlideNode) throws URISyntaxException {
        log.debug("REST request to save TrainingGeneratedSlideNode : {}", trainingGeneratedSlideNode);
        if (trainingGeneratedSlideNode.getId() != null) {
            throw new BadRequestAlertException("A new trainingGeneratedSlideNode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainingGeneratedSlideNode result = trainingGeneratedSlideNodeRepository.save(trainingGeneratedSlideNode);
        trainingGeneratedSlideNodeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/training-generated-slide-nodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /training-generated-slide-nodes} : Updates an existing trainingGeneratedSlideNode.
     *
     * @param trainingGeneratedSlideNode the trainingGeneratedSlideNode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainingGeneratedSlideNode,
     * or with status {@code 400 (Bad Request)} if the trainingGeneratedSlideNode is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trainingGeneratedSlideNode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/training-generated-slide-nodes")
    public ResponseEntity<TrainingGeneratedSlideNode> updateTrainingGeneratedSlideNode(@RequestBody TrainingGeneratedSlideNode trainingGeneratedSlideNode) throws URISyntaxException {
        log.debug("REST request to update TrainingGeneratedSlideNode : {}", trainingGeneratedSlideNode);
        if (trainingGeneratedSlideNode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TrainingGeneratedSlideNode result = trainingGeneratedSlideNodeRepository.save(trainingGeneratedSlideNode);
        trainingGeneratedSlideNodeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trainingGeneratedSlideNode.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /training-generated-slide-nodes} : get all the trainingGeneratedSlideNodes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trainingGeneratedSlideNodes in body.
     */
    @GetMapping("/training-generated-slide-nodes")
    public List<TrainingGeneratedSlideNode> getAllTrainingGeneratedSlideNodes() {
        log.debug("REST request to get all TrainingGeneratedSlideNodes");
        return trainingGeneratedSlideNodeRepository.findAll();
    }

    /**
     * {@code GET  /training-generated-slide-nodes/:id} : get the "id" trainingGeneratedSlideNode.
     *
     * @param id the id of the trainingGeneratedSlideNode to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trainingGeneratedSlideNode, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/training-generated-slide-nodes/{id}")
    public ResponseEntity<TrainingGeneratedSlideNode> getTrainingGeneratedSlideNode(@PathVariable Long id) {
        log.debug("REST request to get TrainingGeneratedSlideNode : {}", id);
        Optional<TrainingGeneratedSlideNode> trainingGeneratedSlideNode = trainingGeneratedSlideNodeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(trainingGeneratedSlideNode);
    }

    /**
     * {@code DELETE  /training-generated-slide-nodes/:id} : delete the "id" trainingGeneratedSlideNode.
     *
     * @param id the id of the trainingGeneratedSlideNode to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/training-generated-slide-nodes/{id}")
    public ResponseEntity<Void> deleteTrainingGeneratedSlideNode(@PathVariable Long id) {
        log.debug("REST request to delete TrainingGeneratedSlideNode : {}", id);
        trainingGeneratedSlideNodeRepository.deleteById(id);
        trainingGeneratedSlideNodeSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/training-generated-slide-nodes?query=:query} : search for the trainingGeneratedSlideNode corresponding
     * to the query.
     *
     * @param query the query of the trainingGeneratedSlideNode search.
     * @return the result of the search.
     */
    @GetMapping("/_search/training-generated-slide-nodes")
    public List<TrainingGeneratedSlideNode> searchTrainingGeneratedSlideNodes(@RequestParam String query) {
        log.debug("REST request to search TrainingGeneratedSlideNodes for query {}", query);
        return StreamSupport
            .stream(trainingGeneratedSlideNodeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
