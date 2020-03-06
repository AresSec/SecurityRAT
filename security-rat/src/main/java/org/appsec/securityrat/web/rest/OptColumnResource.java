package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.domain.OptColumn;
import org.appsec.securityrat.repository.OptColumnRepository;
import org.appsec.securityrat.repository.search.OptColumnSearchRepository;
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
 * REST controller for managing {@link org.appsec.securityrat.domain.OptColumn}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OptColumnResource {

    private final Logger log = LoggerFactory.getLogger(OptColumnResource.class);

    private static final String ENTITY_NAME = "optColumn";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OptColumnRepository optColumnRepository;

    private final OptColumnSearchRepository optColumnSearchRepository;

    public OptColumnResource(OptColumnRepository optColumnRepository, OptColumnSearchRepository optColumnSearchRepository) {
        this.optColumnRepository = optColumnRepository;
        this.optColumnSearchRepository = optColumnSearchRepository;
    }

    /**
     * {@code POST  /opt-columns} : Create a new optColumn.
     *
     * @param optColumn the optColumn to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new optColumn, or with status {@code 400 (Bad Request)} if the optColumn has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/opt-columns")
    public ResponseEntity<OptColumn> createOptColumn(@RequestBody OptColumn optColumn) throws URISyntaxException {
        log.debug("REST request to save OptColumn : {}", optColumn);
        if (optColumn.getId() != null) {
            throw new BadRequestAlertException("A new optColumn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OptColumn result = optColumnRepository.save(optColumn);
        optColumnSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/opt-columns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /opt-columns} : Updates an existing optColumn.
     *
     * @param optColumn the optColumn to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated optColumn,
     * or with status {@code 400 (Bad Request)} if the optColumn is not valid,
     * or with status {@code 500 (Internal Server Error)} if the optColumn couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/opt-columns")
    public ResponseEntity<OptColumn> updateOptColumn(@RequestBody OptColumn optColumn) throws URISyntaxException {
        log.debug("REST request to update OptColumn : {}", optColumn);
        if (optColumn.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OptColumn result = optColumnRepository.save(optColumn);
        optColumnSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, optColumn.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /opt-columns} : get all the optColumns.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of optColumns in body.
     */
    @GetMapping("/opt-columns")
    public List<OptColumn> getAllOptColumns() {
        log.debug("REST request to get all OptColumns");
        return optColumnRepository.findAll();
    }

    /**
     * {@code GET  /opt-columns/:id} : get the "id" optColumn.
     *
     * @param id the id of the optColumn to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the optColumn, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/opt-columns/{id}")
    public ResponseEntity<OptColumn> getOptColumn(@PathVariable Long id) {
        log.debug("REST request to get OptColumn : {}", id);
        Optional<OptColumn> optColumn = optColumnRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(optColumn);
    }

    /**
     * {@code DELETE  /opt-columns/:id} : delete the "id" optColumn.
     *
     * @param id the id of the optColumn to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/opt-columns/{id}")
    public ResponseEntity<Void> deleteOptColumn(@PathVariable Long id) {
        log.debug("REST request to delete OptColumn : {}", id);
        optColumnRepository.deleteById(id);
        optColumnSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/opt-columns?query=:query} : search for the optColumn corresponding
     * to the query.
     *
     * @param query the query of the optColumn search.
     * @return the result of the search.
     */
    @GetMapping("/_search/opt-columns")
    public List<OptColumn> searchOptColumns(@RequestParam String query) {
        log.debug("REST request to search OptColumns for query {}", query);
        return StreamSupport
            .stream(optColumnSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
