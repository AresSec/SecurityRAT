package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.domain.RequirementSkeleton;
import org.appsec.securityrat.repository.RequirementSkeletonRepository;
import org.appsec.securityrat.repository.search.RequirementSkeletonSearchRepository;
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
 * REST controller for managing {@link org.appsec.securityrat.domain.RequirementSkeleton}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RequirementSkeletonResource {

    private final Logger log = LoggerFactory.getLogger(RequirementSkeletonResource.class);

    private static final String ENTITY_NAME = "requirementSkeleton";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequirementSkeletonRepository requirementSkeletonRepository;

    private final RequirementSkeletonSearchRepository requirementSkeletonSearchRepository;

    public RequirementSkeletonResource(RequirementSkeletonRepository requirementSkeletonRepository, RequirementSkeletonSearchRepository requirementSkeletonSearchRepository) {
        this.requirementSkeletonRepository = requirementSkeletonRepository;
        this.requirementSkeletonSearchRepository = requirementSkeletonSearchRepository;
    }

    /**
     * {@code POST  /requirement-skeletons} : Create a new requirementSkeleton.
     *
     * @param requirementSkeleton the requirementSkeleton to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requirementSkeleton, or with status {@code 400 (Bad Request)} if the requirementSkeleton has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/requirement-skeletons")
    public ResponseEntity<RequirementSkeleton> createRequirementSkeleton(@RequestBody RequirementSkeleton requirementSkeleton) throws URISyntaxException {
        log.debug("REST request to save RequirementSkeleton : {}", requirementSkeleton);
        if (requirementSkeleton.getId() != null) {
            throw new BadRequestAlertException("A new requirementSkeleton cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequirementSkeleton result = requirementSkeletonRepository.save(requirementSkeleton);
        requirementSkeletonSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/requirement-skeletons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /requirement-skeletons} : Updates an existing requirementSkeleton.
     *
     * @param requirementSkeleton the requirementSkeleton to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requirementSkeleton,
     * or with status {@code 400 (Bad Request)} if the requirementSkeleton is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requirementSkeleton couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/requirement-skeletons")
    public ResponseEntity<RequirementSkeleton> updateRequirementSkeleton(@RequestBody RequirementSkeleton requirementSkeleton) throws URISyntaxException {
        log.debug("REST request to update RequirementSkeleton : {}", requirementSkeleton);
        if (requirementSkeleton.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RequirementSkeleton result = requirementSkeletonRepository.save(requirementSkeleton);
        requirementSkeletonSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requirementSkeleton.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /requirement-skeletons} : get all the requirementSkeletons.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requirementSkeletons in body.
     */
    @GetMapping("/requirement-skeletons")
    public List<RequirementSkeleton> getAllRequirementSkeletons(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all RequirementSkeletons");
        return requirementSkeletonRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /requirement-skeletons/:id} : get the "id" requirementSkeleton.
     *
     * @param id the id of the requirementSkeleton to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requirementSkeleton, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/requirement-skeletons/{id}")
    public ResponseEntity<RequirementSkeleton> getRequirementSkeleton(@PathVariable Long id) {
        log.debug("REST request to get RequirementSkeleton : {}", id);
        Optional<RequirementSkeleton> requirementSkeleton = requirementSkeletonRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(requirementSkeleton);
    }

    /**
     * {@code DELETE  /requirement-skeletons/:id} : delete the "id" requirementSkeleton.
     *
     * @param id the id of the requirementSkeleton to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/requirement-skeletons/{id}")
    public ResponseEntity<Void> deleteRequirementSkeleton(@PathVariable Long id) {
        log.debug("REST request to delete RequirementSkeleton : {}", id);
        requirementSkeletonRepository.deleteById(id);
        requirementSkeletonSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/requirement-skeletons?query=:query} : search for the requirementSkeleton corresponding
     * to the query.
     *
     * @param query the query of the requirementSkeleton search.
     * @return the result of the search.
     */
    @GetMapping("/_search/requirement-skeletons")
    public List<RequirementSkeleton> searchRequirementSkeletons(@RequestParam String query) {
        log.debug("REST request to search RequirementSkeletons for query {}", query);
        return StreamSupport
            .stream(requirementSkeletonSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
