package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.domain.CollectionCategory;
import org.appsec.securityrat.repository.CollectionCategoryRepository;
import org.appsec.securityrat.repository.search.CollectionCategorySearchRepository;
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
 * REST controller for managing {@link org.appsec.securityrat.domain.CollectionCategory}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CollectionCategoryResource {

    private final Logger log = LoggerFactory.getLogger(CollectionCategoryResource.class);

    private static final String ENTITY_NAME = "collectionCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollectionCategoryRepository collectionCategoryRepository;

    private final CollectionCategorySearchRepository collectionCategorySearchRepository;

    public CollectionCategoryResource(CollectionCategoryRepository collectionCategoryRepository, CollectionCategorySearchRepository collectionCategorySearchRepository) {
        this.collectionCategoryRepository = collectionCategoryRepository;
        this.collectionCategorySearchRepository = collectionCategorySearchRepository;
    }

    /**
     * {@code POST  /collection-categories} : Create a new collectionCategory.
     *
     * @param collectionCategory the collectionCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new collectionCategory, or with status {@code 400 (Bad Request)} if the collectionCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/collection-categories")
    public ResponseEntity<CollectionCategory> createCollectionCategory(@RequestBody CollectionCategory collectionCategory) throws URISyntaxException {
        log.debug("REST request to save CollectionCategory : {}", collectionCategory);
        if (collectionCategory.getId() != null) {
            throw new BadRequestAlertException("A new collectionCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CollectionCategory result = collectionCategoryRepository.save(collectionCategory);
        collectionCategorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/collection-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /collection-categories} : Updates an existing collectionCategory.
     *
     * @param collectionCategory the collectionCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collectionCategory,
     * or with status {@code 400 (Bad Request)} if the collectionCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collectionCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/collection-categories")
    public ResponseEntity<CollectionCategory> updateCollectionCategory(@RequestBody CollectionCategory collectionCategory) throws URISyntaxException {
        log.debug("REST request to update CollectionCategory : {}", collectionCategory);
        if (collectionCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CollectionCategory result = collectionCategoryRepository.save(collectionCategory);
        collectionCategorySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collectionCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /collection-categories} : get all the collectionCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of collectionCategories in body.
     */
    @GetMapping("/collection-categories")
    public List<CollectionCategory> getAllCollectionCategories() {
        log.debug("REST request to get all CollectionCategories");
        return collectionCategoryRepository.findAll();
    }

    /**
     * {@code GET  /collection-categories/:id} : get the "id" collectionCategory.
     *
     * @param id the id of the collectionCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the collectionCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/collection-categories/{id}")
    public ResponseEntity<CollectionCategory> getCollectionCategory(@PathVariable Long id) {
        log.debug("REST request to get CollectionCategory : {}", id);
        Optional<CollectionCategory> collectionCategory = collectionCategoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(collectionCategory);
    }

    /**
     * {@code DELETE  /collection-categories/:id} : delete the "id" collectionCategory.
     *
     * @param id the id of the collectionCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/collection-categories/{id}")
    public ResponseEntity<Void> deleteCollectionCategory(@PathVariable Long id) {
        log.debug("REST request to delete CollectionCategory : {}", id);
        collectionCategoryRepository.deleteById(id);
        collectionCategorySearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/collection-categories?query=:query} : search for the collectionCategory corresponding
     * to the query.
     *
     * @param query the query of the collectionCategory search.
     * @return the result of the search.
     */
    @GetMapping("/_search/collection-categories")
    public List<CollectionCategory> searchCollectionCategories(@RequestParam String query) {
        log.debug("REST request to search CollectionCategories for query {}", query);
        return StreamSupport
            .stream(collectionCategorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
