package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.domain.ReqCategory;
import org.appsec.securityrat.repository.ReqCategoryRepository;
import org.appsec.securityrat.repository.search.ReqCategorySearchRepository;
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
 * REST controller for managing {@link org.appsec.securityrat.domain.ReqCategory}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReqCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ReqCategoryResource.class);

    private static final String ENTITY_NAME = "reqCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReqCategoryRepository reqCategoryRepository;

    private final ReqCategorySearchRepository reqCategorySearchRepository;

    public ReqCategoryResource(ReqCategoryRepository reqCategoryRepository, ReqCategorySearchRepository reqCategorySearchRepository) {
        this.reqCategoryRepository = reqCategoryRepository;
        this.reqCategorySearchRepository = reqCategorySearchRepository;
    }

    /**
     * {@code POST  /req-categories} : Create a new reqCategory.
     *
     * @param reqCategory the reqCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reqCategory, or with status {@code 400 (Bad Request)} if the reqCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/req-categories")
    public ResponseEntity<ReqCategory> createReqCategory(@RequestBody ReqCategory reqCategory) throws URISyntaxException {
        log.debug("REST request to save ReqCategory : {}", reqCategory);
        if (reqCategory.getId() != null) {
            throw new BadRequestAlertException("A new reqCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReqCategory result = reqCategoryRepository.save(reqCategory);
        reqCategorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/req-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /req-categories} : Updates an existing reqCategory.
     *
     * @param reqCategory the reqCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reqCategory,
     * or with status {@code 400 (Bad Request)} if the reqCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reqCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/req-categories")
    public ResponseEntity<ReqCategory> updateReqCategory(@RequestBody ReqCategory reqCategory) throws URISyntaxException {
        log.debug("REST request to update ReqCategory : {}", reqCategory);
        if (reqCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReqCategory result = reqCategoryRepository.save(reqCategory);
        reqCategorySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reqCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /req-categories} : get all the reqCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reqCategories in body.
     */
    @GetMapping("/req-categories")
    public List<ReqCategory> getAllReqCategories() {
        log.debug("REST request to get all ReqCategories");
        return reqCategoryRepository.findAll();
    }

    /**
     * {@code GET  /req-categories/:id} : get the "id" reqCategory.
     *
     * @param id the id of the reqCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reqCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/req-categories/{id}")
    public ResponseEntity<ReqCategory> getReqCategory(@PathVariable Long id) {
        log.debug("REST request to get ReqCategory : {}", id);
        Optional<ReqCategory> reqCategory = reqCategoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reqCategory);
    }

    /**
     * {@code DELETE  /req-categories/:id} : delete the "id" reqCategory.
     *
     * @param id the id of the reqCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/req-categories/{id}")
    public ResponseEntity<Void> deleteReqCategory(@PathVariable Long id) {
        log.debug("REST request to delete ReqCategory : {}", id);
        reqCategoryRepository.deleteById(id);
        reqCategorySearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/req-categories?query=:query} : search for the reqCategory corresponding
     * to the query.
     *
     * @param query the query of the reqCategory search.
     * @return the result of the search.
     */
    @GetMapping("/_search/req-categories")
    public List<ReqCategory> searchReqCategories(@RequestParam String query) {
        log.debug("REST request to search ReqCategories for query {}", query);
        return StreamSupport
            .stream(reqCategorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
