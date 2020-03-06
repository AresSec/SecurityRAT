package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.SecurityRatApp;
import org.appsec.securityrat.domain.OptColumnContent;
import org.appsec.securityrat.repository.OptColumnContentRepository;
import org.appsec.securityrat.repository.search.OptColumnContentSearchRepository;
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
 * Integration tests for the {@link OptColumnContentResource} REST controller.
 */
@SpringBootTest(classes = SecurityRatApp.class)
public class OptColumnContentResourceIT {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private OptColumnContentRepository optColumnContentRepository;

    /**
     * This repository is mocked in the org.appsec.securityrat.repository.search test package.
     *
     * @see org.appsec.securityrat.repository.search.OptColumnContentSearchRepositoryMockConfiguration
     */
    @Autowired
    private OptColumnContentSearchRepository mockOptColumnContentSearchRepository;

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

    private MockMvc restOptColumnContentMockMvc;

    private OptColumnContent optColumnContent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OptColumnContentResource optColumnContentResource = new OptColumnContentResource(optColumnContentRepository, mockOptColumnContentSearchRepository);
        this.restOptColumnContentMockMvc = MockMvcBuilders.standaloneSetup(optColumnContentResource)
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
    public static OptColumnContent createEntity(EntityManager em) {
        OptColumnContent optColumnContent = new OptColumnContent();
        optColumnContent.setContent(DEFAULT_CONTENT);
        return optColumnContent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OptColumnContent createUpdatedEntity(EntityManager em) {
        OptColumnContent optColumnContent = new OptColumnContent();
        optColumnContent.setContent(UPDATED_CONTENT);
        return optColumnContent;
    }

    @BeforeEach
    public void initTest() {
        optColumnContent = createEntity(em);
    }

    @Test
    @Transactional
    public void createOptColumnContent() throws Exception {
        int databaseSizeBeforeCreate = optColumnContentRepository.findAll().size();

        // Create the OptColumnContent
        restOptColumnContentMockMvc.perform(post("/api/opt-column-contents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optColumnContent)))
            .andExpect(status().isCreated());

        // Validate the OptColumnContent in the database
        List<OptColumnContent> optColumnContentList = optColumnContentRepository.findAll();
        assertThat(optColumnContentList).hasSize(databaseSizeBeforeCreate + 1);
        OptColumnContent testOptColumnContent = optColumnContentList.get(optColumnContentList.size() - 1);
        assertThat(testOptColumnContent.getContent()).isEqualTo(DEFAULT_CONTENT);

        // Validate the OptColumnContent in Elasticsearch
        verify(mockOptColumnContentSearchRepository, times(1)).save(testOptColumnContent);
    }

    @Test
    @Transactional
    public void createOptColumnContentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = optColumnContentRepository.findAll().size();

        // Create the OptColumnContent with an existing ID
        optColumnContent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOptColumnContentMockMvc.perform(post("/api/opt-column-contents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optColumnContent)))
            .andExpect(status().isBadRequest());

        // Validate the OptColumnContent in the database
        List<OptColumnContent> optColumnContentList = optColumnContentRepository.findAll();
        assertThat(optColumnContentList).hasSize(databaseSizeBeforeCreate);

        // Validate the OptColumnContent in Elasticsearch
        verify(mockOptColumnContentSearchRepository, times(0)).save(optColumnContent);
    }


    @Test
    @Transactional
    public void getAllOptColumnContents() throws Exception {
        // Initialize the database
        optColumnContentRepository.saveAndFlush(optColumnContent);

        // Get all the optColumnContentList
        restOptColumnContentMockMvc.perform(get("/api/opt-column-contents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optColumnContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
    
    @Test
    @Transactional
    public void getOptColumnContent() throws Exception {
        // Initialize the database
        optColumnContentRepository.saveAndFlush(optColumnContent);

        // Get the optColumnContent
        restOptColumnContentMockMvc.perform(get("/api/opt-column-contents/{id}", optColumnContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(optColumnContent.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    public void getNonExistingOptColumnContent() throws Exception {
        // Get the optColumnContent
        restOptColumnContentMockMvc.perform(get("/api/opt-column-contents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOptColumnContent() throws Exception {
        // Initialize the database
        optColumnContentRepository.saveAndFlush(optColumnContent);

        int databaseSizeBeforeUpdate = optColumnContentRepository.findAll().size();

        // Update the optColumnContent
        OptColumnContent updatedOptColumnContent = optColumnContentRepository.findById(optColumnContent.getId()).get();
        // Disconnect from session so that the updates on updatedOptColumnContent are not directly saved in db
        em.detach(updatedOptColumnContent);
        updatedOptColumnContent.setContent(UPDATED_CONTENT);

        restOptColumnContentMockMvc.perform(put("/api/opt-column-contents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOptColumnContent)))
            .andExpect(status().isOk());

        // Validate the OptColumnContent in the database
        List<OptColumnContent> optColumnContentList = optColumnContentRepository.findAll();
        assertThat(optColumnContentList).hasSize(databaseSizeBeforeUpdate);
        OptColumnContent testOptColumnContent = optColumnContentList.get(optColumnContentList.size() - 1);
        assertThat(testOptColumnContent.getContent()).isEqualTo(UPDATED_CONTENT);

        // Validate the OptColumnContent in Elasticsearch
        verify(mockOptColumnContentSearchRepository, times(1)).save(testOptColumnContent);
    }

    @Test
    @Transactional
    public void updateNonExistingOptColumnContent() throws Exception {
        int databaseSizeBeforeUpdate = optColumnContentRepository.findAll().size();

        // Create the OptColumnContent

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptColumnContentMockMvc.perform(put("/api/opt-column-contents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optColumnContent)))
            .andExpect(status().isBadRequest());

        // Validate the OptColumnContent in the database
        List<OptColumnContent> optColumnContentList = optColumnContentRepository.findAll();
        assertThat(optColumnContentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OptColumnContent in Elasticsearch
        verify(mockOptColumnContentSearchRepository, times(0)).save(optColumnContent);
    }

    @Test
    @Transactional
    public void deleteOptColumnContent() throws Exception {
        // Initialize the database
        optColumnContentRepository.saveAndFlush(optColumnContent);

        int databaseSizeBeforeDelete = optColumnContentRepository.findAll().size();

        // Delete the optColumnContent
        restOptColumnContentMockMvc.perform(delete("/api/opt-column-contents/{id}", optColumnContent.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OptColumnContent> optColumnContentList = optColumnContentRepository.findAll();
        assertThat(optColumnContentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the OptColumnContent in Elasticsearch
        verify(mockOptColumnContentSearchRepository, times(1)).deleteById(optColumnContent.getId());
    }

    @Test
    @Transactional
    public void searchOptColumnContent() throws Exception {
        // Initialize the database
        optColumnContentRepository.saveAndFlush(optColumnContent);
        when(mockOptColumnContentSearchRepository.search(queryStringQuery("id:" + optColumnContent.getId())))
            .thenReturn(Collections.singletonList(optColumnContent));
        // Search the optColumnContent
        restOptColumnContentMockMvc.perform(get("/api/_search/opt-column-contents?query=id:" + optColumnContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optColumnContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
}
