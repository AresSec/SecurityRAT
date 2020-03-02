package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.SecurityRatApp;
import org.appsec.securityrat.domain.SlideTemplate;
import org.appsec.securityrat.repository.SlideTemplateRepository;
import org.appsec.securityrat.repository.search.SlideTemplateSearchRepository;
import org.appsec.securityrat.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.appsec.securityrat.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SlideTemplateResource} REST controller.
 */
@SpringBootTest(classes = SecurityRatApp.class)
public class SlideTemplateResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private SlideTemplateRepository slideTemplateRepository;

    /**
     * This repository is mocked in the org.appsec.securityrat.repository.search test package.
     *
     * @see org.appsec.securityrat.repository.search.SlideTemplateSearchRepositoryMockConfiguration
     */
    @Autowired
    private SlideTemplateSearchRepository mockSlideTemplateSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSlideTemplateMockMvc;

    private SlideTemplate slideTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SlideTemplateResource slideTemplateResource = new SlideTemplateResource(slideTemplateRepository, mockSlideTemplateSearchRepository);
        this.restSlideTemplateMockMvc = MockMvcBuilders.standaloneSetup(slideTemplateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SlideTemplate createEntity(EntityManager em) {
        SlideTemplate slideTemplate = new SlideTemplate();
        slideTemplate.setName(DEFAULT_NAME);
        slideTemplate.setDescription(DEFAULT_DESCRIPTION);
        slideTemplate.setContent(DEFAULT_CONTENT);
        return slideTemplate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SlideTemplate createUpdatedEntity(EntityManager em) {
        SlideTemplate slideTemplate = new SlideTemplate();
        slideTemplate.setName(UPDATED_NAME);
        slideTemplate.setDescription(UPDATED_DESCRIPTION);
        slideTemplate.setContent(UPDATED_CONTENT);
        return slideTemplate;
    }

    @BeforeEach
    public void initTest() {
        slideTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createSlideTemplate() throws Exception {
        int databaseSizeBeforeCreate = slideTemplateRepository.findAll().size();

        // Create the SlideTemplate
        restSlideTemplateMockMvc.perform(post("/api/slide-templates")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(slideTemplate)))
            .andExpect(status().isCreated());

        // Validate the SlideTemplate in the database
        List<SlideTemplate> slideTemplateList = slideTemplateRepository.findAll();
        assertThat(slideTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        SlideTemplate testSlideTemplate = slideTemplateList.get(slideTemplateList.size() - 1);
        assertThat(testSlideTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSlideTemplate.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSlideTemplate.getContent()).isEqualTo(DEFAULT_CONTENT);

        // Validate the SlideTemplate in Elasticsearch
        verify(mockSlideTemplateSearchRepository, times(1)).save(testSlideTemplate);
    }

    @Test
    @Transactional
    public void createSlideTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = slideTemplateRepository.findAll().size();

        // Create the SlideTemplate with an existing ID
        slideTemplate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSlideTemplateMockMvc.perform(post("/api/slide-templates")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(slideTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the SlideTemplate in the database
        List<SlideTemplate> slideTemplateList = slideTemplateRepository.findAll();
        assertThat(slideTemplateList).hasSize(databaseSizeBeforeCreate);

        // Validate the SlideTemplate in Elasticsearch
        verify(mockSlideTemplateSearchRepository, times(0)).save(slideTemplate);
    }


    @Test
    @Transactional
    public void getAllSlideTemplates() throws Exception {
        // Initialize the database
        slideTemplateRepository.saveAndFlush(slideTemplate);

        // Get all the slideTemplateList
        restSlideTemplateMockMvc.perform(get("/api/slide-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(slideTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
    
    @Test
    @Transactional
    public void getSlideTemplate() throws Exception {
        // Initialize the database
        slideTemplateRepository.saveAndFlush(slideTemplate);

        // Get the slideTemplate
        restSlideTemplateMockMvc.perform(get("/api/slide-templates/{id}", slideTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(slideTemplate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    public void getNonExistingSlideTemplate() throws Exception {
        // Get the slideTemplate
        restSlideTemplateMockMvc.perform(get("/api/slide-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSlideTemplate() throws Exception {
        // Initialize the database
        slideTemplateRepository.saveAndFlush(slideTemplate);

        int databaseSizeBeforeUpdate = slideTemplateRepository.findAll().size();

        // Update the slideTemplate
        SlideTemplate updatedSlideTemplate = slideTemplateRepository.findById(slideTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedSlideTemplate are not directly saved in db
        em.detach(updatedSlideTemplate);
        updatedSlideTemplate.setName(UPDATED_NAME);
        updatedSlideTemplate.setDescription(UPDATED_DESCRIPTION);
        updatedSlideTemplate.setContent(UPDATED_CONTENT);

        restSlideTemplateMockMvc.perform(put("/api/slide-templates")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSlideTemplate)))
            .andExpect(status().isOk());

        // Validate the SlideTemplate in the database
        List<SlideTemplate> slideTemplateList = slideTemplateRepository.findAll();
        assertThat(slideTemplateList).hasSize(databaseSizeBeforeUpdate);
        SlideTemplate testSlideTemplate = slideTemplateList.get(slideTemplateList.size() - 1);
        assertThat(testSlideTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSlideTemplate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSlideTemplate.getContent()).isEqualTo(UPDATED_CONTENT);

        // Validate the SlideTemplate in Elasticsearch
        verify(mockSlideTemplateSearchRepository, times(1)).save(testSlideTemplate);
    }

    @Test
    @Transactional
    public void updateNonExistingSlideTemplate() throws Exception {
        int databaseSizeBeforeUpdate = slideTemplateRepository.findAll().size();

        // Create the SlideTemplate

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSlideTemplateMockMvc.perform(put("/api/slide-templates")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(slideTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the SlideTemplate in the database
        List<SlideTemplate> slideTemplateList = slideTemplateRepository.findAll();
        assertThat(slideTemplateList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SlideTemplate in Elasticsearch
        verify(mockSlideTemplateSearchRepository, times(0)).save(slideTemplate);
    }

    @Test
    @Transactional
    public void deleteSlideTemplate() throws Exception {
        // Initialize the database
        slideTemplateRepository.saveAndFlush(slideTemplate);

        int databaseSizeBeforeDelete = slideTemplateRepository.findAll().size();

        // Delete the slideTemplate
        restSlideTemplateMockMvc.perform(delete("/api/slide-templates/{id}", slideTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SlideTemplate> slideTemplateList = slideTemplateRepository.findAll();
        assertThat(slideTemplateList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SlideTemplate in Elasticsearch
        verify(mockSlideTemplateSearchRepository, times(1)).deleteById(slideTemplate.getId());
    }

    @Test
    @Transactional
    public void searchSlideTemplate() throws Exception {
        // Initialize the database
        slideTemplateRepository.saveAndFlush(slideTemplate);
        when(mockSlideTemplateSearchRepository.search(queryStringQuery("id:" + slideTemplate.getId())))
            .thenReturn(Collections.singletonList(slideTemplate));
        // Search the slideTemplate
        restSlideTemplateMockMvc.perform(get("/api/_search/slide-templates?query=id:" + slideTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(slideTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
}
