package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.domain.ProjectType;
import org.appsec.securityrat.repository.ProjectTypeRepository;
import org.appsec.securityrat.repository.search.ProjectTypeSearchRepository;
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
 * REST controller for managing {@link org.appsec.securityrat.domain.ProjectType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProjectTypeResource {

    private final Logger log = LoggerFactory.getLogger(ProjectTypeResource.class);

    private static final String ENTITY_NAME = "projectType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectTypeRepository projectTypeRepository;

    private final ProjectTypeSearchRepository projectTypeSearchRepository;

    public ProjectTypeResource(ProjectTypeRepository projectTypeRepository, ProjectTypeSearchRepository projectTypeSearchRepository) {
        this.projectTypeRepository = projectTypeRepository;
        this.projectTypeSearchRepository = projectTypeSearchRepository;
    }

    /**
     * {@code POST  /project-types} : Create a new projectType.
     *
     * @param projectType the projectType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectType, or with status {@code 400 (Bad Request)} if the projectType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-types")
    public ResponseEntity<ProjectType> createProjectType(@RequestBody ProjectType projectType) throws URISyntaxException {
        log.debug("REST request to save ProjectType : {}", projectType);
        if (projectType.getId() != null) {
            throw new BadRequestAlertException("A new projectType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectType result = projectTypeRepository.save(projectType);
        projectTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/project-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-types} : Updates an existing projectType.
     *
     * @param projectType the projectType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectType,
     * or with status {@code 400 (Bad Request)} if the projectType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-types")
    public ResponseEntity<ProjectType> updateProjectType(@RequestBody ProjectType projectType) throws URISyntaxException {
        log.debug("REST request to update ProjectType : {}", projectType);
        if (projectType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectType result = projectTypeRepository.save(projectType);
        projectTypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-types} : get all the projectTypes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectTypes in body.
     */
    @GetMapping("/project-types")
    public List<ProjectType> getAllProjectTypes(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all ProjectTypes");
        return projectTypeRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /project-types/:id} : get the "id" projectType.
     *
     * @param id the id of the projectType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-types/{id}")
    public ResponseEntity<ProjectType> getProjectType(@PathVariable Long id) {
        log.debug("REST request to get ProjectType : {}", id);
        Optional<ProjectType> projectType = projectTypeRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(projectType);
    }

    /**
     * {@code DELETE  /project-types/:id} : delete the "id" projectType.
     *
     * @param id the id of the projectType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-types/{id}")
    public ResponseEntity<Void> deleteProjectType(@PathVariable Long id) {
        log.debug("REST request to delete ProjectType : {}", id);
        projectTypeRepository.deleteById(id);
        projectTypeSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/project-types?query=:query} : search for the projectType corresponding
     * to the query.
     *
     * @param query the query of the projectType search.
     * @return the result of the search.
     */
    @GetMapping("/_search/project-types")
    public List<ProjectType> searchProjectTypes(@RequestParam String query) {
        log.debug("REST request to search ProjectTypes for query {}", query);
        return StreamSupport
            .stream(projectTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
