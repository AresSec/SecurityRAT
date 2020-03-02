package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.domain.StatusColumnValue;
import org.appsec.securityrat.repository.StatusColumnValueRepository;
import org.appsec.securityrat.repository.search.StatusColumnValueSearchRepository;
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
 * REST controller for managing {@link org.appsec.securityrat.domain.StatusColumnValue}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StatusColumnValueResource {

    private final Logger log = LoggerFactory.getLogger(StatusColumnValueResource.class);

    private static final String ENTITY_NAME = "statusColumnValue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatusColumnValueRepository statusColumnValueRepository;

    private final StatusColumnValueSearchRepository statusColumnValueSearchRepository;

    public StatusColumnValueResource(StatusColumnValueRepository statusColumnValueRepository, StatusColumnValueSearchRepository statusColumnValueSearchRepository) {
        this.statusColumnValueRepository = statusColumnValueRepository;
        this.statusColumnValueSearchRepository = statusColumnValueSearchRepository;
    }

    /**
     * {@code POST  /status-column-values} : Create a new statusColumnValue.
     *
     * @param statusColumnValue the statusColumnValue to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statusColumnValue, or with status {@code 400 (Bad Request)} if the statusColumnValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/status-column-values")
    public ResponseEntity<StatusColumnValue> createStatusColumnValue(@RequestBody StatusColumnValue statusColumnValue) throws URISyntaxException {
        log.debug("REST request to save StatusColumnValue : {}", statusColumnValue);
        if (statusColumnValue.getId() != null) {
            throw new BadRequestAlertException("A new statusColumnValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatusColumnValue result = statusColumnValueRepository.save(statusColumnValue);
        statusColumnValueSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/status-column-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /status-column-values} : Updates an existing statusColumnValue.
     *
     * @param statusColumnValue the statusColumnValue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statusColumnValue,
     * or with status {@code 400 (Bad Request)} if the statusColumnValue is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statusColumnValue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/status-column-values")
    public ResponseEntity<StatusColumnValue> updateStatusColumnValue(@RequestBody StatusColumnValue statusColumnValue) throws URISyntaxException {
        log.debug("REST request to update StatusColumnValue : {}", statusColumnValue);
        if (statusColumnValue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StatusColumnValue result = statusColumnValueRepository.save(statusColumnValue);
        statusColumnValueSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statusColumnValue.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /status-column-values} : get all the statusColumnValues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statusColumnValues in body.
     */
    @GetMapping("/status-column-values")
    public List<StatusColumnValue> getAllStatusColumnValues() {
        log.debug("REST request to get all StatusColumnValues");
        return statusColumnValueRepository.findAll();
    }

    /**
     * {@code GET  /status-column-values/:id} : get the "id" statusColumnValue.
     *
     * @param id the id of the statusColumnValue to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statusColumnValue, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/status-column-values/{id}")
    public ResponseEntity<StatusColumnValue> getStatusColumnValue(@PathVariable Long id) {
        log.debug("REST request to get StatusColumnValue : {}", id);
        Optional<StatusColumnValue> statusColumnValue = statusColumnValueRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(statusColumnValue);
    }

    /**
     * {@code DELETE  /status-column-values/:id} : delete the "id" statusColumnValue.
     *
     * @param id the id of the statusColumnValue to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/status-column-values/{id}")
    public ResponseEntity<Void> deleteStatusColumnValue(@PathVariable Long id) {
        log.debug("REST request to delete StatusColumnValue : {}", id);
        statusColumnValueRepository.deleteById(id);
        statusColumnValueSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/status-column-values?query=:query} : search for the statusColumnValue corresponding
     * to the query.
     *
     * @param query the query of the statusColumnValue search.
     * @return the result of the search.
     */
    @GetMapping("/_search/status-column-values")
    public List<StatusColumnValue> searchStatusColumnValues(@RequestParam String query) {
        log.debug("REST request to search StatusColumnValues for query {}", query);
        return StreamSupport
            .stream(statusColumnValueSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
