package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.domain.SlideTemplate;
import org.appsec.securityrat.repository.SlideTemplateRepository;
import org.appsec.securityrat.repository.search.SlideTemplateSearchRepository;
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
 * REST controller for managing {@link org.appsec.securityrat.domain.SlideTemplate}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SlideTemplateResource {

    private final Logger log = LoggerFactory.getLogger(SlideTemplateResource.class);

    private static final String ENTITY_NAME = "slideTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SlideTemplateRepository slideTemplateRepository;

    private final SlideTemplateSearchRepository slideTemplateSearchRepository;

    public SlideTemplateResource(SlideTemplateRepository slideTemplateRepository, SlideTemplateSearchRepository slideTemplateSearchRepository) {
        this.slideTemplateRepository = slideTemplateRepository;
        this.slideTemplateSearchRepository = slideTemplateSearchRepository;
    }

    /**
     * {@code POST  /slide-templates} : Create a new slideTemplate.
     *
     * @param slideTemplate the slideTemplate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new slideTemplate, or with status {@code 400 (Bad Request)} if the slideTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/slide-templates")
    public ResponseEntity<SlideTemplate> createSlideTemplate(@RequestBody SlideTemplate slideTemplate) throws URISyntaxException {
        log.debug("REST request to save SlideTemplate : {}", slideTemplate);
        if (slideTemplate.getId() != null) {
            throw new BadRequestAlertException("A new slideTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SlideTemplate result = slideTemplateRepository.save(slideTemplate);
        slideTemplateSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/slide-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /slide-templates} : Updates an existing slideTemplate.
     *
     * @param slideTemplate the slideTemplate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated slideTemplate,
     * or with status {@code 400 (Bad Request)} if the slideTemplate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the slideTemplate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/slide-templates")
    public ResponseEntity<SlideTemplate> updateSlideTemplate(@RequestBody SlideTemplate slideTemplate) throws URISyntaxException {
        log.debug("REST request to update SlideTemplate : {}", slideTemplate);
        if (slideTemplate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SlideTemplate result = slideTemplateRepository.save(slideTemplate);
        slideTemplateSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, slideTemplate.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /slide-templates} : get all the slideTemplates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of slideTemplates in body.
     */
    @GetMapping("/slide-templates")
    public List<SlideTemplate> getAllSlideTemplates() {
        log.debug("REST request to get all SlideTemplates");
        return slideTemplateRepository.findAll();
    }

    /**
     * {@code GET  /slide-templates/:id} : get the "id" slideTemplate.
     *
     * @param id the id of the slideTemplate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the slideTemplate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/slide-templates/{id}")
    public ResponseEntity<SlideTemplate> getSlideTemplate(@PathVariable Long id) {
        log.debug("REST request to get SlideTemplate : {}", id);
        Optional<SlideTemplate> slideTemplate = slideTemplateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(slideTemplate);
    }

    /**
     * {@code DELETE  /slide-templates/:id} : delete the "id" slideTemplate.
     *
     * @param id the id of the slideTemplate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/slide-templates/{id}")
    public ResponseEntity<Void> deleteSlideTemplate(@PathVariable Long id) {
        log.debug("REST request to delete SlideTemplate : {}", id);
        slideTemplateRepository.deleteById(id);
        slideTemplateSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/slide-templates?query=:query} : search for the slideTemplate corresponding
     * to the query.
     *
     * @param query the query of the slideTemplate search.
     * @return the result of the search.
     */
    @GetMapping("/_search/slide-templates")
    public List<SlideTemplate> searchSlideTemplates(@RequestParam String query) {
        log.debug("REST request to search SlideTemplates for query {}", query);
        return StreamSupport
            .stream(slideTemplateSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
