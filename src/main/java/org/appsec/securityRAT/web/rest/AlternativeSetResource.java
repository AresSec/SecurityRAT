package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.domain.AlternativeSet;
import org.appsec.securityrat.repository.AlternativeSetRepository;
import org.appsec.securityrat.repository.search.AlternativeSetSearchRepository;
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
 * REST controller for managing {@link org.appsec.securityrat.domain.AlternativeSet}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AlternativeSetResource {

    private final Logger log = LoggerFactory.getLogger(AlternativeSetResource.class);

    private static final String ENTITY_NAME = "alternativeSet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlternativeSetRepository alternativeSetRepository;

    private final AlternativeSetSearchRepository alternativeSetSearchRepository;

    public AlternativeSetResource(AlternativeSetRepository alternativeSetRepository, AlternativeSetSearchRepository alternativeSetSearchRepository) {
        this.alternativeSetRepository = alternativeSetRepository;
        this.alternativeSetSearchRepository = alternativeSetSearchRepository;
    }

    /**
     * {@code POST  /alternative-sets} : Create a new alternativeSet.
     *
     * @param alternativeSet the alternativeSet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alternativeSet, or with status {@code 400 (Bad Request)} if the alternativeSet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alternative-sets")
    public ResponseEntity<AlternativeSet> createAlternativeSet(@RequestBody AlternativeSet alternativeSet) throws URISyntaxException {
        log.debug("REST request to save AlternativeSet : {}", alternativeSet);
        if (alternativeSet.getId() != null) {
            throw new BadRequestAlertException("A new alternativeSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlternativeSet result = alternativeSetRepository.save(alternativeSet);
        alternativeSetSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/alternative-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alternative-sets} : Updates an existing alternativeSet.
     *
     * @param alternativeSet the alternativeSet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alternativeSet,
     * or with status {@code 400 (Bad Request)} if the alternativeSet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alternativeSet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alternative-sets")
    public ResponseEntity<AlternativeSet> updateAlternativeSet(@RequestBody AlternativeSet alternativeSet) throws URISyntaxException {
        log.debug("REST request to update AlternativeSet : {}", alternativeSet);
        if (alternativeSet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlternativeSet result = alternativeSetRepository.save(alternativeSet);
        alternativeSetSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alternativeSet.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /alternative-sets} : get all the alternativeSets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alternativeSets in body.
     */
    @GetMapping("/alternative-sets")
    public List<AlternativeSet> getAllAlternativeSets() {
        log.debug("REST request to get all AlternativeSets");
        return alternativeSetRepository.findAll();
    }

    /**
     * {@code GET  /alternative-sets/:id} : get the "id" alternativeSet.
     *
     * @param id the id of the alternativeSet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alternativeSet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alternative-sets/{id}")
    public ResponseEntity<AlternativeSet> getAlternativeSet(@PathVariable Long id) {
        log.debug("REST request to get AlternativeSet : {}", id);
        Optional<AlternativeSet> alternativeSet = alternativeSetRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(alternativeSet);
    }

    /**
     * {@code DELETE  /alternative-sets/:id} : delete the "id" alternativeSet.
     *
     * @param id the id of the alternativeSet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alternative-sets/{id}")
    public ResponseEntity<Void> deleteAlternativeSet(@PathVariable Long id) {
        log.debug("REST request to delete AlternativeSet : {}", id);
        alternativeSetRepository.deleteById(id);
        alternativeSetSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/alternative-sets?query=:query} : search for the alternativeSet corresponding
     * to the query.
     *
     * @param query the query of the alternativeSet search.
     * @return the result of the search.
     */
    @GetMapping("/_search/alternative-sets")
    public List<AlternativeSet> searchAlternativeSets(@RequestParam String query) {
        log.debug("REST request to search AlternativeSets for query {}", query);
        return StreamSupport
            .stream(alternativeSetSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
