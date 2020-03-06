package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.domain.StatusColumn;
import org.appsec.securityrat.repository.StatusColumnRepository;
import org.appsec.securityrat.repository.search.StatusColumnSearchRepository;
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
 * REST controller for managing {@link org.appsec.securityrat.domain.StatusColumn}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StatusColumnResource {

    private final Logger log = LoggerFactory.getLogger(StatusColumnResource.class);

    private static final String ENTITY_NAME = "statusColumn";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatusColumnRepository statusColumnRepository;

    private final StatusColumnSearchRepository statusColumnSearchRepository;

    public StatusColumnResource(StatusColumnRepository statusColumnRepository, StatusColumnSearchRepository statusColumnSearchRepository) {
        this.statusColumnRepository = statusColumnRepository;
        this.statusColumnSearchRepository = statusColumnSearchRepository;
    }

    /**
     * {@code POST  /status-columns} : Create a new statusColumn.
     *
     * @param statusColumn the statusColumn to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statusColumn, or with status {@code 400 (Bad Request)} if the statusColumn has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/status-columns")
    public ResponseEntity<StatusColumn> createStatusColumn(@RequestBody StatusColumn statusColumn) throws URISyntaxException {
        log.debug("REST request to save StatusColumn : {}", statusColumn);
        if (statusColumn.getId() != null) {
            throw new BadRequestAlertException("A new statusColumn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatusColumn result = statusColumnRepository.save(statusColumn);
        statusColumnSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/status-columns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /status-columns} : Updates an existing statusColumn.
     *
     * @param statusColumn the statusColumn to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statusColumn,
     * or with status {@code 400 (Bad Request)} if the statusColumn is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statusColumn couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/status-columns")
    public ResponseEntity<StatusColumn> updateStatusColumn(@RequestBody StatusColumn statusColumn) throws URISyntaxException {
        log.debug("REST request to update StatusColumn : {}", statusColumn);
        if (statusColumn.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StatusColumn result = statusColumnRepository.save(statusColumn);
        statusColumnSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statusColumn.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /status-columns} : get all the statusColumns.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statusColumns in body.
     */
    @GetMapping("/status-columns")
    public List<StatusColumn> getAllStatusColumns() {
        log.debug("REST request to get all StatusColumns");
        return statusColumnRepository.findAll();
    }

    /**
     * {@code GET  /status-columns/:id} : get the "id" statusColumn.
     *
     * @param id the id of the statusColumn to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statusColumn, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/status-columns/{id}")
    public ResponseEntity<StatusColumn> getStatusColumn(@PathVariable Long id) {
        log.debug("REST request to get StatusColumn : {}", id);
        Optional<StatusColumn> statusColumn = statusColumnRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(statusColumn);
    }

    /**
     * {@code DELETE  /status-columns/:id} : delete the "id" statusColumn.
     *
     * @param id the id of the statusColumn to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/status-columns/{id}")
    public ResponseEntity<Void> deleteStatusColumn(@PathVariable Long id) {
        log.debug("REST request to delete StatusColumn : {}", id);
        statusColumnRepository.deleteById(id);
        statusColumnSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/status-columns?query=:query} : search for the statusColumn corresponding
     * to the query.
     *
     * @param query the query of the statusColumn search.
     * @return the result of the search.
     */
    @GetMapping("/_search/status-columns")
    public List<StatusColumn> searchStatusColumns(@RequestParam String query) {
        log.debug("REST request to search StatusColumns for query {}", query);
        return StreamSupport
            .stream(statusColumnSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
