package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.domain.CollectionInstance;
import org.appsec.securityrat.repository.CollectionInstanceRepository;
import org.appsec.securityrat.repository.search.CollectionInstanceSearchRepository;
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
 * REST controller for managing {@link org.appsec.securityrat.domain.CollectionInstance}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CollectionInstanceResource {

    private final Logger log = LoggerFactory.getLogger(CollectionInstanceResource.class);

    private static final String ENTITY_NAME = "collectionInstance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollectionInstanceRepository collectionInstanceRepository;

    private final CollectionInstanceSearchRepository collectionInstanceSearchRepository;

    public CollectionInstanceResource(CollectionInstanceRepository collectionInstanceRepository, CollectionInstanceSearchRepository collectionInstanceSearchRepository) {
        this.collectionInstanceRepository = collectionInstanceRepository;
        this.collectionInstanceSearchRepository = collectionInstanceSearchRepository;
    }

    /**
     * {@code POST  /collection-instances} : Create a new collectionInstance.
     *
     * @param collectionInstance the collectionInstance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new collectionInstance, or with status {@code 400 (Bad Request)} if the collectionInstance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/collection-instances")
    public ResponseEntity<CollectionInstance> createCollectionInstance(@RequestBody CollectionInstance collectionInstance) throws URISyntaxException {
        log.debug("REST request to save CollectionInstance : {}", collectionInstance);
        if (collectionInstance.getId() != null) {
            throw new BadRequestAlertException("A new collectionInstance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CollectionInstance result = collectionInstanceRepository.save(collectionInstance);
        collectionInstanceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/collection-instances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /collection-instances} : Updates an existing collectionInstance.
     *
     * @param collectionInstance the collectionInstance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collectionInstance,
     * or with status {@code 400 (Bad Request)} if the collectionInstance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collectionInstance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/collection-instances")
    public ResponseEntity<CollectionInstance> updateCollectionInstance(@RequestBody CollectionInstance collectionInstance) throws URISyntaxException {
        log.debug("REST request to update CollectionInstance : {}", collectionInstance);
        if (collectionInstance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CollectionInstance result = collectionInstanceRepository.save(collectionInstance);
        collectionInstanceSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collectionInstance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /collection-instances} : get all the collectionInstances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of collectionInstances in body.
     */
    @GetMapping("/collection-instances")
    public List<CollectionInstance> getAllCollectionInstances() {
        log.debug("REST request to get all CollectionInstances");
        return collectionInstanceRepository.findAll();
    }

    /**
     * {@code GET  /collection-instances/:id} : get the "id" collectionInstance.
     *
     * @param id the id of the collectionInstance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the collectionInstance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/collection-instances/{id}")
    public ResponseEntity<CollectionInstance> getCollectionInstance(@PathVariable Long id) {
        log.debug("REST request to get CollectionInstance : {}", id);
        Optional<CollectionInstance> collectionInstance = collectionInstanceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(collectionInstance);
    }

    /**
     * {@code DELETE  /collection-instances/:id} : delete the "id" collectionInstance.
     *
     * @param id the id of the collectionInstance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/collection-instances/{id}")
    public ResponseEntity<Void> deleteCollectionInstance(@PathVariable Long id) {
        log.debug("REST request to delete CollectionInstance : {}", id);
        collectionInstanceRepository.deleteById(id);
        collectionInstanceSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/collection-instances?query=:query} : search for the collectionInstance corresponding
     * to the query.
     *
     * @param query the query of the collectionInstance search.
     * @return the result of the search.
     */
    @GetMapping("/_search/collection-instances")
    public List<CollectionInstance> searchCollectionInstances(@RequestParam String query) {
        log.debug("REST request to search CollectionInstances for query {}", query);
        return StreamSupport
            .stream(collectionInstanceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
