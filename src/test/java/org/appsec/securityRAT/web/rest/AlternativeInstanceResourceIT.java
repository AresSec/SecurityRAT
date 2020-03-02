package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.SecurityRatApp;
import org.appsec.securityrat.domain.AlternativeInstance;
import org.appsec.securityrat.repository.AlternativeInstanceRepository;
import org.appsec.securityrat.repository.search.AlternativeInstanceSearchRepository;
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
 * Integration tests for the {@link AlternativeInstanceResource} REST controller.
 */
@SpringBootTest(classes = SecurityRatApp.class)
public class AlternativeInstanceResourceIT {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private AlternativeInstanceRepository alternativeInstanceRepository;

    /**
     * This repository is mocked in the org.appsec.securityrat.repository.search test package.
     *
     * @see org.appsec.securityrat.repository.search.AlternativeInstanceSearchRepositoryMockConfiguration
     */
    @Autowired
    private AlternativeInstanceSearchRepository mockAlternativeInstanceSearchRepository;

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

    private MockMvc restAlternativeInstanceMockMvc;

    private AlternativeInstance alternativeInstance;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlternativeInstanceResource alternativeInstanceResource = new AlternativeInstanceResource(alternativeInstanceRepository, mockAlternativeInstanceSearchRepository);
        this.restAlternativeInstanceMockMvc = MockMvcBuilders.standaloneSetup(alternativeInstanceResource)
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
    public static AlternativeInstance createEntity(EntityManager em) {
        AlternativeInstance alternativeInstance = new AlternativeInstance();
        alternativeInstance.setContent(DEFAULT_CONTENT);
        return alternativeInstance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlternativeInstance createUpdatedEntity(EntityManager em) {
        AlternativeInstance alternativeInstance = new AlternativeInstance();
        alternativeInstance.setContent(UPDATED_CONTENT);
        return alternativeInstance;
    }

    @BeforeEach
    public void initTest() {
        alternativeInstance = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlternativeInstance() throws Exception {
        int databaseSizeBeforeCreate = alternativeInstanceRepository.findAll().size();

        // Create the AlternativeInstance
        restAlternativeInstanceMockMvc.perform(post("/api/alternative-instances")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alternativeInstance)))
            .andExpect(status().isCreated());

        // Validate the AlternativeInstance in the database
        List<AlternativeInstance> alternativeInstanceList = alternativeInstanceRepository.findAll();
        assertThat(alternativeInstanceList).hasSize(databaseSizeBeforeCreate + 1);
        AlternativeInstance testAlternativeInstance = alternativeInstanceList.get(alternativeInstanceList.size() - 1);
        assertThat(testAlternativeInstance.getContent()).isEqualTo(DEFAULT_CONTENT);

        // Validate the AlternativeInstance in Elasticsearch
        verify(mockAlternativeInstanceSearchRepository, times(1)).save(testAlternativeInstance);
    }

    @Test
    @Transactional
    public void createAlternativeInstanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alternativeInstanceRepository.findAll().size();

        // Create the AlternativeInstance with an existing ID
        alternativeInstance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlternativeInstanceMockMvc.perform(post("/api/alternative-instances")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alternativeInstance)))
            .andExpect(status().isBadRequest());

        // Validate the AlternativeInstance in the database
        List<AlternativeInstance> alternativeInstanceList = alternativeInstanceRepository.findAll();
        assertThat(alternativeInstanceList).hasSize(databaseSizeBeforeCreate);

        // Validate the AlternativeInstance in Elasticsearch
        verify(mockAlternativeInstanceSearchRepository, times(0)).save(alternativeInstance);
    }


    @Test
    @Transactional
    public void getAllAlternativeInstances() throws Exception {
        // Initialize the database
        alternativeInstanceRepository.saveAndFlush(alternativeInstance);

        // Get all the alternativeInstanceList
        restAlternativeInstanceMockMvc.perform(get("/api/alternative-instances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alternativeInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
    
    @Test
    @Transactional
    public void getAlternativeInstance() throws Exception {
        // Initialize the database
        alternativeInstanceRepository.saveAndFlush(alternativeInstance);

        // Get the alternativeInstance
        restAlternativeInstanceMockMvc.perform(get("/api/alternative-instances/{id}", alternativeInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alternativeInstance.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    public void getNonExistingAlternativeInstance() throws Exception {
        // Get the alternativeInstance
        restAlternativeInstanceMockMvc.perform(get("/api/alternative-instances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlternativeInstance() throws Exception {
        // Initialize the database
        alternativeInstanceRepository.saveAndFlush(alternativeInstance);

        int databaseSizeBeforeUpdate = alternativeInstanceRepository.findAll().size();

        // Update the alternativeInstance
        AlternativeInstance updatedAlternativeInstance = alternativeInstanceRepository.findById(alternativeInstance.getId()).get();
        // Disconnect from session so that the updates on updatedAlternativeInstance are not directly saved in db
        em.detach(updatedAlternativeInstance);
        updatedAlternativeInstance.setContent(UPDATED_CONTENT);

        restAlternativeInstanceMockMvc.perform(put("/api/alternative-instances")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAlternativeInstance)))
            .andExpect(status().isOk());

        // Validate the AlternativeInstance in the database
        List<AlternativeInstance> alternativeInstanceList = alternativeInstanceRepository.findAll();
        assertThat(alternativeInstanceList).hasSize(databaseSizeBeforeUpdate);
        AlternativeInstance testAlternativeInstance = alternativeInstanceList.get(alternativeInstanceList.size() - 1);
        assertThat(testAlternativeInstance.getContent()).isEqualTo(UPDATED_CONTENT);

        // Validate the AlternativeInstance in Elasticsearch
        verify(mockAlternativeInstanceSearchRepository, times(1)).save(testAlternativeInstance);
    }

    @Test
    @Transactional
    public void updateNonExistingAlternativeInstance() throws Exception {
        int databaseSizeBeforeUpdate = alternativeInstanceRepository.findAll().size();

        // Create the AlternativeInstance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlternativeInstanceMockMvc.perform(put("/api/alternative-instances")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alternativeInstance)))
            .andExpect(status().isBadRequest());

        // Validate the AlternativeInstance in the database
        List<AlternativeInstance> alternativeInstanceList = alternativeInstanceRepository.findAll();
        assertThat(alternativeInstanceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AlternativeInstance in Elasticsearch
        verify(mockAlternativeInstanceSearchRepository, times(0)).save(alternativeInstance);
    }

    @Test
    @Transactional
    public void deleteAlternativeInstance() throws Exception {
        // Initialize the database
        alternativeInstanceRepository.saveAndFlush(alternativeInstance);

        int databaseSizeBeforeDelete = alternativeInstanceRepository.findAll().size();

        // Delete the alternativeInstance
        restAlternativeInstanceMockMvc.perform(delete("/api/alternative-instances/{id}", alternativeInstance.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlternativeInstance> alternativeInstanceList = alternativeInstanceRepository.findAll();
        assertThat(alternativeInstanceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AlternativeInstance in Elasticsearch
        verify(mockAlternativeInstanceSearchRepository, times(1)).deleteById(alternativeInstance.getId());
    }

    @Test
    @Transactional
    public void searchAlternativeInstance() throws Exception {
        // Initialize the database
        alternativeInstanceRepository.saveAndFlush(alternativeInstance);
        when(mockAlternativeInstanceSearchRepository.search(queryStringQuery("id:" + alternativeInstance.getId())))
            .thenReturn(Collections.singletonList(alternativeInstance));
        // Search the alternativeInstance
        restAlternativeInstanceMockMvc.perform(get("/api/_search/alternative-instances?query=id:" + alternativeInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alternativeInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
}
