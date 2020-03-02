package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.domain.TagInstance;
import org.appsec.securityrat.repository.TagInstanceRepository;
import org.appsec.securityrat.repository.search.TagInstanceSearchRepository;
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
 * REST controller for managing {@link org.appsec.securityrat.domain.TagInstance}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TagInstanceResource {

    private final Logger log = LoggerFactory.getLogger(TagInstanceResource.class);

    private static final String ENTITY_NAME = "tagInstance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TagInstanceRepository tagInstanceRepository;

    private final TagInstanceSearchRepository tagInstanceSearchRepository;

    public TagInstanceResource(TagInstanceRepository tagInstanceRepository, TagInstanceSearchRepository tagInstanceSearchRepository) {
        this.tagInstanceRepository = tagInstanceRepository;
        this.tagInstanceSearchRepository = tagInstanceSearchRepository;
    }

    /**
     * {@code POST  /tag-instances} : Create a new tagInstance.
     *
     * @param tagInstance the tagInstance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tagInstance, or with status {@code 400 (Bad Request)} if the tagInstance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tag-instances")
    public ResponseEntity<TagInstance> createTagInstance(@RequestBody TagInstance tagInstance) throws URISyntaxException {
        log.debug("REST request to save TagInstance : {}", tagInstance);
        if (tagInstance.getId() != null) {
            throw new BadRequestAlertException("A new tagInstance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TagInstance result = tagInstanceRepository.save(tagInstance);
        tagInstanceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/tag-instances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tag-instances} : Updates an existing tagInstance.
     *
     * @param tagInstance the tagInstance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tagInstance,
     * or with status {@code 400 (Bad Request)} if the tagInstance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tagInstance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tag-instances")
    public ResponseEntity<TagInstance> updateTagInstance(@RequestBody TagInstance tagInstance) throws URISyntaxException {
        log.debug("REST request to update TagInstance : {}", tagInstance);
        if (tagInstance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TagInstance result = tagInstanceRepository.save(tagInstance);
        tagInstanceSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tagInstance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tag-instances} : get all the tagInstances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tagInstances in body.
     */
    @GetMapping("/tag-instances")
    public List<TagInstance> getAllTagInstances() {
        log.debug("REST request to get all TagInstances");
        return tagInstanceRepository.findAll();
    }

    /**
     * {@code GET  /tag-instances/:id} : get the "id" tagInstance.
     *
     * @param id the id of the tagInstance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tagInstance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tag-instances/{id}")
    public ResponseEntity<TagInstance> getTagInstance(@PathVariable Long id) {
        log.debug("REST request to get TagInstance : {}", id);
        Optional<TagInstance> tagInstance = tagInstanceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tagInstance);
    }

    /**
     * {@code DELETE  /tag-instances/:id} : delete the "id" tagInstance.
     *
     * @param id the id of the tagInstance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tag-instances/{id}")
    public ResponseEntity<Void> deleteTagInstance(@PathVariable Long id) {
        log.debug("REST request to delete TagInstance : {}", id);
        tagInstanceRepository.deleteById(id);
        tagInstanceSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/tag-instances?query=:query} : search for the tagInstance corresponding
     * to the query.
     *
     * @param query the query of the tagInstance search.
     * @return the result of the search.
     */
    @GetMapping("/_search/tag-instances")
    public List<TagInstance> searchTagInstances(@RequestParam String query) {
        log.debug("REST request to search TagInstances for query {}", query);
        return StreamSupport
            .stream(tagInstanceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
