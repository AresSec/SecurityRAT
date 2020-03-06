package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.domain.TagCategory;
import org.appsec.securityrat.repository.TagCategoryRepository;
import org.appsec.securityrat.repository.search.TagCategorySearchRepository;
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
 * REST controller for managing {@link org.appsec.securityrat.domain.TagCategory}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TagCategoryResource {

    private final Logger log = LoggerFactory.getLogger(TagCategoryResource.class);

    private static final String ENTITY_NAME = "tagCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TagCategoryRepository tagCategoryRepository;

    private final TagCategorySearchRepository tagCategorySearchRepository;

    public TagCategoryResource(TagCategoryRepository tagCategoryRepository, TagCategorySearchRepository tagCategorySearchRepository) {
        this.tagCategoryRepository = tagCategoryRepository;
        this.tagCategorySearchRepository = tagCategorySearchRepository;
    }

    /**
     * {@code POST  /tag-categories} : Create a new tagCategory.
     *
     * @param tagCategory the tagCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tagCategory, or with status {@code 400 (Bad Request)} if the tagCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tag-categories")
    public ResponseEntity<TagCategory> createTagCategory(@RequestBody TagCategory tagCategory) throws URISyntaxException {
        log.debug("REST request to save TagCategory : {}", tagCategory);
        if (tagCategory.getId() != null) {
            throw new BadRequestAlertException("A new tagCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TagCategory result = tagCategoryRepository.save(tagCategory);
        tagCategorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/tag-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tag-categories} : Updates an existing tagCategory.
     *
     * @param tagCategory the tagCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tagCategory,
     * or with status {@code 400 (Bad Request)} if the tagCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tagCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tag-categories")
    public ResponseEntity<TagCategory> updateTagCategory(@RequestBody TagCategory tagCategory) throws URISyntaxException {
        log.debug("REST request to update TagCategory : {}", tagCategory);
        if (tagCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TagCategory result = tagCategoryRepository.save(tagCategory);
        tagCategorySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tagCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tag-categories} : get all the tagCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tagCategories in body.
     */
    @GetMapping("/tag-categories")
    public List<TagCategory> getAllTagCategories() {
        log.debug("REST request to get all TagCategories");
        return tagCategoryRepository.findAll();
    }

    /**
     * {@code GET  /tag-categories/:id} : get the "id" tagCategory.
     *
     * @param id the id of the tagCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tagCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tag-categories/{id}")
    public ResponseEntity<TagCategory> getTagCategory(@PathVariable Long id) {
        log.debug("REST request to get TagCategory : {}", id);
        Optional<TagCategory> tagCategory = tagCategoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tagCategory);
    }

    /**
     * {@code DELETE  /tag-categories/:id} : delete the "id" tagCategory.
     *
     * @param id the id of the tagCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tag-categories/{id}")
    public ResponseEntity<Void> deleteTagCategory(@PathVariable Long id) {
        log.debug("REST request to delete TagCategory : {}", id);
        tagCategoryRepository.deleteById(id);
        tagCategorySearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/tag-categories?query=:query} : search for the tagCategory corresponding
     * to the query.
     *
     * @param query the query of the tagCategory search.
     * @return the result of the search.
     */
    @GetMapping("/_search/tag-categories")
    public List<TagCategory> searchTagCategories(@RequestParam String query) {
        log.debug("REST request to search TagCategories for query {}", query);
        return StreamSupport
            .stream(tagCategorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
