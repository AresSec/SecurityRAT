package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.domain.TrainingCustomSlideNode;
import org.appsec.securityrat.repository.TrainingCustomSlideNodeRepository;
import org.appsec.securityrat.repository.search.TrainingCustomSlideNodeSearchRepository;
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
 * REST controller for managing {@link org.appsec.securityrat.domain.TrainingCustomSlideNode}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TrainingCustomSlideNodeResource {

    private final Logger log = LoggerFactory.getLogger(TrainingCustomSlideNodeResource.class);

    private static final String ENTITY_NAME = "trainingCustomSlideNode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrainingCustomSlideNodeRepository trainingCustomSlideNodeRepository;

    private final TrainingCustomSlideNodeSearchRepository trainingCustomSlideNodeSearchRepository;

    public TrainingCustomSlideNodeResource(TrainingCustomSlideNodeRepository trainingCustomSlideNodeRepository, TrainingCustomSlideNodeSearchRepository trainingCustomSlideNodeSearchRepository) {
        this.trainingCustomSlideNodeRepository = trainingCustomSlideNodeRepository;
        this.trainingCustomSlideNodeSearchRepository = trainingCustomSlideNodeSearchRepository;
    }

    /**
     * {@code POST  /training-custom-slide-nodes} : Create a new trainingCustomSlideNode.
     *
     * @param trainingCustomSlideNode the trainingCustomSlideNode to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trainingCustomSlideNode, or with status {@code 400 (Bad Request)} if the trainingCustomSlideNode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/training-custom-slide-nodes")
    public ResponseEntity<TrainingCustomSlideNode> createTrainingCustomSlideNode(@RequestBody TrainingCustomSlideNode trainingCustomSlideNode) throws URISyntaxException {
        log.debug("REST request to save TrainingCustomSlideNode : {}", trainingCustomSlideNode);
        if (trainingCustomSlideNode.getId() != null) {
            throw new BadRequestAlertException("A new trainingCustomSlideNode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainingCustomSlideNode result = trainingCustomSlideNodeRepository.save(trainingCustomSlideNode);
        trainingCustomSlideNodeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/training-custom-slide-nodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /training-custom-slide-nodes} : Updates an existing trainingCustomSlideNode.
     *
     * @param trainingCustomSlideNode the trainingCustomSlideNode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainingCustomSlideNode,
     * or with status {@code 400 (Bad Request)} if the trainingCustomSlideNode is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trainingCustomSlideNode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/training-custom-slide-nodes")
    public ResponseEntity<TrainingCustomSlideNode> updateTrainingCustomSlideNode(@RequestBody TrainingCustomSlideNode trainingCustomSlideNode) throws URISyntaxException {
        log.debug("REST request to update TrainingCustomSlideNode : {}", trainingCustomSlideNode);
        if (trainingCustomSlideNode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TrainingCustomSlideNode result = trainingCustomSlideNodeRepository.save(trainingCustomSlideNode);
        trainingCustomSlideNodeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trainingCustomSlideNode.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /training-custom-slide-nodes} : get all the trainingCustomSlideNodes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trainingCustomSlideNodes in body.
     */
    @GetMapping("/training-custom-slide-nodes")
    public List<TrainingCustomSlideNode> getAllTrainingCustomSlideNodes() {
        log.debug("REST request to get all TrainingCustomSlideNodes");
        return trainingCustomSlideNodeRepository.findAll();
    }

    /**
     * {@code GET  /training-custom-slide-nodes/:id} : get the "id" trainingCustomSlideNode.
     *
     * @param id the id of the trainingCustomSlideNode to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trainingCustomSlideNode, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/training-custom-slide-nodes/{id}")
    public ResponseEntity<TrainingCustomSlideNode> getTrainingCustomSlideNode(@PathVariable Long id) {
        log.debug("REST request to get TrainingCustomSlideNode : {}", id);
        Optional<TrainingCustomSlideNode> trainingCustomSlideNode = trainingCustomSlideNodeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(trainingCustomSlideNode);
    }

    /**
     * {@code DELETE  /training-custom-slide-nodes/:id} : delete the "id" trainingCustomSlideNode.
     *
     * @param id the id of the trainingCustomSlideNode to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/training-custom-slide-nodes/{id}")
    public ResponseEntity<Void> deleteTrainingCustomSlideNode(@PathVariable Long id) {
        log.debug("REST request to delete TrainingCustomSlideNode : {}", id);
        trainingCustomSlideNodeRepository.deleteById(id);
        trainingCustomSlideNodeSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/training-custom-slide-nodes?query=:query} : search for the trainingCustomSlideNode corresponding
     * to the query.
     *
     * @param query the query of the trainingCustomSlideNode search.
     * @return the result of the search.
     */
    @GetMapping("/_search/training-custom-slide-nodes")
    public List<TrainingCustomSlideNode> searchTrainingCustomSlideNodes(@RequestParam String query) {
        log.debug("REST request to search TrainingCustomSlideNodes for query {}", query);
        return StreamSupport
            .stream(trainingCustomSlideNodeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
