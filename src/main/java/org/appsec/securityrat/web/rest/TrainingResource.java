package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.domain.Training;
import org.appsec.securityrat.repository.TrainingRepository;
import org.appsec.securityrat.repository.search.TrainingSearchRepository;
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
 * REST controller for managing {@link org.appsec.securityrat.domain.Training}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TrainingResource {

    private final Logger log = LoggerFactory.getLogger(TrainingResource.class);

    private static final String ENTITY_NAME = "training";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrainingRepository trainingRepository;

    private final TrainingSearchRepository trainingSearchRepository;

    public TrainingResource(TrainingRepository trainingRepository, TrainingSearchRepository trainingSearchRepository) {
        this.trainingRepository = trainingRepository;
        this.trainingSearchRepository = trainingSearchRepository;
    }

    /**
     * {@code POST  /trainings} : Create a new training.
     *
     * @param training the training to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new training, or with status {@code 400 (Bad Request)} if the training has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trainings")
    public ResponseEntity<Training> createTraining(@RequestBody Training training) throws URISyntaxException {
        log.debug("REST request to save Training : {}", training);
        if (training.getId() != null) {
            throw new BadRequestAlertException("A new training cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Training result = trainingRepository.save(training);
        trainingSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/trainings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trainings} : Updates an existing training.
     *
     * @param training the training to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated training,
     * or with status {@code 400 (Bad Request)} if the training is not valid,
     * or with status {@code 500 (Internal Server Error)} if the training couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trainings")
    public ResponseEntity<Training> updateTraining(@RequestBody Training training) throws URISyntaxException {
        log.debug("REST request to update Training : {}", training);
        if (training.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Training result = trainingRepository.save(training);
        trainingSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, training.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /trainings} : get all the trainings.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trainings in body.
     */
    @GetMapping("/trainings")
    public List<Training> getAllTrainings(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Trainings");
        return trainingRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /trainings/:id} : get the "id" training.
     *
     * @param id the id of the training to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the training, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trainings/{id}")
    public ResponseEntity<Training> getTraining(@PathVariable Long id) {
        log.debug("REST request to get Training : {}", id);
        Optional<Training> training = trainingRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(training);
    }

    /**
     * {@code DELETE  /trainings/:id} : delete the "id" training.
     *
     * @param id the id of the training to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trainings/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        log.debug("REST request to delete Training : {}", id);
        trainingRepository.deleteById(id);
        trainingSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/trainings?query=:query} : search for the training corresponding
     * to the query.
     *
     * @param query the query of the training search.
     * @return the result of the search.
     */
    @GetMapping("/_search/trainings")
    public List<Training> searchTrainings(@RequestParam String query) {
        log.debug("REST request to search Trainings for query {}", query);
        return StreamSupport
            .stream(trainingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
