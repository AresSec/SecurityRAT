package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.domain.OptColumnType;
import org.appsec.securityrat.repository.OptColumnTypeRepository;
import org.appsec.securityrat.repository.search.OptColumnTypeSearchRepository;
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
 * REST controller for managing {@link org.appsec.securityrat.domain.OptColumnType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OptColumnTypeResource {

    private final Logger log = LoggerFactory.getLogger(OptColumnTypeResource.class);

    private static final String ENTITY_NAME = "optColumnType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OptColumnTypeRepository optColumnTypeRepository;

    private final OptColumnTypeSearchRepository optColumnTypeSearchRepository;

    public OptColumnTypeResource(OptColumnTypeRepository optColumnTypeRepository, OptColumnTypeSearchRepository optColumnTypeSearchRepository) {
        this.optColumnTypeRepository = optColumnTypeRepository;
        this.optColumnTypeSearchRepository = optColumnTypeSearchRepository;
    }

    /**
     * {@code POST  /opt-column-types} : Create a new optColumnType.
     *
     * @param optColumnType the optColumnType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new optColumnType, or with status {@code 400 (Bad Request)} if the optColumnType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/opt-column-types")
    public ResponseEntity<OptColumnType> createOptColumnType(@RequestBody OptColumnType optColumnType) throws URISyntaxException {
        log.debug("REST request to save OptColumnType : {}", optColumnType);
        if (optColumnType.getId() != null) {
            throw new BadRequestAlertException("A new optColumnType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OptColumnType result = optColumnTypeRepository.save(optColumnType);
        optColumnTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/opt-column-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /opt-column-types} : Updates an existing optColumnType.
     *
     * @param optColumnType the optColumnType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated optColumnType,
     * or with status {@code 400 (Bad Request)} if the optColumnType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the optColumnType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/opt-column-types")
    public ResponseEntity<OptColumnType> updateOptColumnType(@RequestBody OptColumnType optColumnType) throws URISyntaxException {
        log.debug("REST request to update OptColumnType : {}", optColumnType);
        if (optColumnType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OptColumnType result = optColumnTypeRepository.save(optColumnType);
        optColumnTypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, optColumnType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /opt-column-types} : get all the optColumnTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of optColumnTypes in body.
     */
    @GetMapping("/opt-column-types")
    public List<OptColumnType> getAllOptColumnTypes() {
        log.debug("REST request to get all OptColumnTypes");
        return optColumnTypeRepository.findAll();
    }

    /**
     * {@code GET  /opt-column-types/:id} : get the "id" optColumnType.
     *
     * @param id the id of the optColumnType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the optColumnType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/opt-column-types/{id}")
    public ResponseEntity<OptColumnType> getOptColumnType(@PathVariable Long id) {
        log.debug("REST request to get OptColumnType : {}", id);
        Optional<OptColumnType> optColumnType = optColumnTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(optColumnType);
    }

    /**
     * {@code DELETE  /opt-column-types/:id} : delete the "id" optColumnType.
     *
     * @param id the id of the optColumnType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/opt-column-types/{id}")
    public ResponseEntity<Void> deleteOptColumnType(@PathVariable Long id) {
        log.debug("REST request to delete OptColumnType : {}", id);
        optColumnTypeRepository.deleteById(id);
        optColumnTypeSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/opt-column-types?query=:query} : search for the optColumnType corresponding
     * to the query.
     *
     * @param query the query of the optColumnType search.
     * @return the result of the search.
     */
    @GetMapping("/_search/opt-column-types")
    public List<OptColumnType> searchOptColumnTypes(@RequestParam String query) {
        log.debug("REST request to search OptColumnTypes for query {}", query);
        return StreamSupport
            .stream(optColumnTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
