package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.domain.OptColumnContent;
import org.appsec.securityrat.repository.OptColumnContentRepository;
import org.appsec.securityrat.repository.search.OptColumnContentSearchRepository;
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
 * REST controller for managing {@link org.appsec.securityrat.domain.OptColumnContent}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OptColumnContentResource {

    private final Logger log = LoggerFactory.getLogger(OptColumnContentResource.class);

    private static final String ENTITY_NAME = "optColumnContent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OptColumnContentRepository optColumnContentRepository;

    private final OptColumnContentSearchRepository optColumnContentSearchRepository;

    public OptColumnContentResource(OptColumnContentRepository optColumnContentRepository, OptColumnContentSearchRepository optColumnContentSearchRepository) {
        this.optColumnContentRepository = optColumnContentRepository;
        this.optColumnContentSearchRepository = optColumnContentSearchRepository;
    }

    /**
     * {@code POST  /opt-column-contents} : Create a new optColumnContent.
     *
     * @param optColumnContent the optColumnContent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new optColumnContent, or with status {@code 400 (Bad Request)} if the optColumnContent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/opt-column-contents")
    public ResponseEntity<OptColumnContent> createOptColumnContent(@RequestBody OptColumnContent optColumnContent) throws URISyntaxException {
        log.debug("REST request to save OptColumnContent : {}", optColumnContent);
        if (optColumnContent.getId() != null) {
            throw new BadRequestAlertException("A new optColumnContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OptColumnContent result = optColumnContentRepository.save(optColumnContent);
        optColumnContentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/opt-column-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /opt-column-contents} : Updates an existing optColumnContent.
     *
     * @param optColumnContent the optColumnContent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated optColumnContent,
     * or with status {@code 400 (Bad Request)} if the optColumnContent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the optColumnContent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/opt-column-contents")
    public ResponseEntity<OptColumnContent> updateOptColumnContent(@RequestBody OptColumnContent optColumnContent) throws URISyntaxException {
        log.debug("REST request to update OptColumnContent : {}", optColumnContent);
        if (optColumnContent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OptColumnContent result = optColumnContentRepository.save(optColumnContent);
        optColumnContentSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, optColumnContent.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /opt-column-contents} : get all the optColumnContents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of optColumnContents in body.
     */
    @GetMapping("/opt-column-contents")
    public List<OptColumnContent> getAllOptColumnContents() {
        log.debug("REST request to get all OptColumnContents");
        return optColumnContentRepository.findAll();
    }

    /**
     * {@code GET  /opt-column-contents/:id} : get the "id" optColumnContent.
     *
     * @param id the id of the optColumnContent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the optColumnContent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/opt-column-contents/{id}")
    public ResponseEntity<OptColumnContent> getOptColumnContent(@PathVariable Long id) {
        log.debug("REST request to get OptColumnContent : {}", id);
        Optional<OptColumnContent> optColumnContent = optColumnContentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(optColumnContent);
    }

    /**
     * {@code DELETE  /opt-column-contents/:id} : delete the "id" optColumnContent.
     *
     * @param id the id of the optColumnContent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/opt-column-contents/{id}")
    public ResponseEntity<Void> deleteOptColumnContent(@PathVariable Long id) {
        log.debug("REST request to delete OptColumnContent : {}", id);
        optColumnContentRepository.deleteById(id);
        optColumnContentSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/opt-column-contents?query=:query} : search for the optColumnContent corresponding
     * to the query.
     *
     * @param query the query of the optColumnContent search.
     * @return the result of the search.
     */
    @GetMapping("/_search/opt-column-contents")
    public List<OptColumnContent> searchOptColumnContents(@RequestParam String query) {
        log.debug("REST request to search OptColumnContents for query {}", query);
        return StreamSupport
            .stream(optColumnContentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
