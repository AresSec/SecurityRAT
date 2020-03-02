package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.domain.AlternativeInstance;
import org.appsec.securityrat.repository.AlternativeInstanceRepository;
import org.appsec.securityrat.repository.search.AlternativeInstanceSearchRepository;
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
 * REST controller for managing {@link org.appsec.securityrat.domain.AlternativeInstance}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AlternativeInstanceResource {

    private final Logger log = LoggerFactory.getLogger(AlternativeInstanceResource.class);

    private static final String ENTITY_NAME = "alternativeInstance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlternativeInstanceRepository alternativeInstanceRepository;

    private final AlternativeInstanceSearchRepository alternativeInstanceSearchRepository;

    public AlternativeInstanceResource(AlternativeInstanceRepository alternativeInstanceRepository, AlternativeInstanceSearchRepository alternativeInstanceSearchRepository) {
        this.alternativeInstanceRepository = alternativeInstanceRepository;
        this.alternativeInstanceSearchRepository = alternativeInstanceSearchRepository;
    }

    /**
     * {@code POST  /alternative-instances} : Create a new alternativeInstance.
     *
     * @param alternativeInstance the alternativeInstance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alternativeInstance, or with status {@code 400 (Bad Request)} if the alternativeInstance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alternative-instances")
    public ResponseEntity<AlternativeInstance> createAlternativeInstance(@RequestBody AlternativeInstance alternativeInstance) throws URISyntaxException {
        log.debug("REST request to save AlternativeInstance : {}", alternativeInstance);
        if (alternativeInstance.getId() != null) {
            throw new BadRequestAlertException("A new alternativeInstance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlternativeInstance result = alternativeInstanceRepository.save(alternativeInstance);
        alternativeInstanceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/alternative-instances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alternative-instances} : Updates an existing alternativeInstance.
     *
     * @param alternativeInstance the alternativeInstance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alternativeInstance,
     * or with status {@code 400 (Bad Request)} if the alternativeInstance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alternativeInstance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alternative-instances")
    public ResponseEntity<AlternativeInstance> updateAlternativeInstance(@RequestBody AlternativeInstance alternativeInstance) throws URISyntaxException {
        log.debug("REST request to update AlternativeInstance : {}", alternativeInstance);
        if (alternativeInstance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlternativeInstance result = alternativeInstanceRepository.save(alternativeInstance);
        alternativeInstanceSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alternativeInstance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /alternative-instances} : get all the alternativeInstances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alternativeInstances in body.
     */
    @GetMapping("/alternative-instances")
    public List<AlternativeInstance> getAllAlternativeInstances() {
        log.debug("REST request to get all AlternativeInstances");
        return alternativeInstanceRepository.findAll();
    }

    /**
     * {@code GET  /alternative-instances/:id} : get the "id" alternativeInstance.
     *
     * @param id the id of the alternativeInstance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alternativeInstance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alternative-instances/{id}")
    public ResponseEntity<AlternativeInstance> getAlternativeInstance(@PathVariable Long id) {
        log.debug("REST request to get AlternativeInstance : {}", id);
        Optional<AlternativeInstance> alternativeInstance = alternativeInstanceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(alternativeInstance);
    }

    /**
     * {@code DELETE  /alternative-instances/:id} : delete the "id" alternativeInstance.
     *
     * @param id the id of the alternativeInstance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alternative-instances/{id}")
    public ResponseEntity<Void> deleteAlternativeInstance(@PathVariable Long id) {
        log.debug("REST request to delete AlternativeInstance : {}", id);
        alternativeInstanceRepository.deleteById(id);
        alternativeInstanceSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/alternative-instances?query=:query} : search for the alternativeInstance corresponding
     * to the query.
     *
     * @param query the query of the alternativeInstance search.
     * @return the result of the search.
     */
    @GetMapping("/_search/alternative-instances")
    public List<AlternativeInstance> searchAlternativeInstances(@RequestParam String query) {
        log.debug("REST request to search AlternativeInstances for query {}", query);
        return StreamSupport
            .stream(alternativeInstanceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
