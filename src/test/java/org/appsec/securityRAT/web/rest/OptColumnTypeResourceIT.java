package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.SecurityRatApp;
import org.appsec.securityrat.domain.OptColumnType;
import org.appsec.securityrat.repository.OptColumnTypeRepository;
import org.appsec.securityrat.repository.search.OptColumnTypeSearchRepository;
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
 * Integration tests for the {@link OptColumnTypeResource} REST controller.
 */
@SpringBootTest(classes = SecurityRatApp.class)
public class OptColumnTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private OptColumnTypeRepository optColumnTypeRepository;

    /**
     * This repository is mocked in the org.appsec.securityrat.repository.search test package.
     *
     * @see org.appsec.securityrat.repository.search.OptColumnTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private OptColumnTypeSearchRepository mockOptColumnTypeSearchRepository;

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

    private MockMvc restOptColumnTypeMockMvc;

    private OptColumnType optColumnType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OptColumnTypeResource optColumnTypeResource = new OptColumnTypeResource(optColumnTypeRepository, mockOptColumnTypeSearchRepository);
        this.restOptColumnTypeMockMvc = MockMvcBuilders.standaloneSetup(optColumnTypeResource)
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
    public static OptColumnType createEntity(EntityManager em) {
        OptColumnType optColumnType = new OptColumnType();
        optColumnType.setName(DEFAULT_NAME);
        optColumnType.setDescription(DEFAULT_DESCRIPTION);
        return optColumnType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OptColumnType createUpdatedEntity(EntityManager em) {
        OptColumnType optColumnType = new OptColumnType();
        optColumnType.setName(UPDATED_NAME);
        optColumnType.setDescription(UPDATED_DESCRIPTION);
        return optColumnType;
    }

    @BeforeEach
    public void initTest() {
        optColumnType = createEntity(em);
    }

    @Test
    @Transactional
    public void createOptColumnType() throws Exception {
        int databaseSizeBeforeCreate = optColumnTypeRepository.findAll().size();

        // Create the OptColumnType
        restOptColumnTypeMockMvc.perform(post("/api/opt-column-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optColumnType)))
            .andExpect(status().isCreated());

        // Validate the OptColumnType in the database
        List<OptColumnType> optColumnTypeList = optColumnTypeRepository.findAll();
        assertThat(optColumnTypeList).hasSize(databaseSizeBeforeCreate + 1);
        OptColumnType testOptColumnType = optColumnTypeList.get(optColumnTypeList.size() - 1);
        assertThat(testOptColumnType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOptColumnType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the OptColumnType in Elasticsearch
        verify(mockOptColumnTypeSearchRepository, times(1)).save(testOptColumnType);
    }

    @Test
    @Transactional
    public void createOptColumnTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = optColumnTypeRepository.findAll().size();

        // Create the OptColumnType with an existing ID
        optColumnType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOptColumnTypeMockMvc.perform(post("/api/opt-column-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optColumnType)))
            .andExpect(status().isBadRequest());

        // Validate the OptColumnType in the database
        List<OptColumnType> optColumnTypeList = optColumnTypeRepository.findAll();
        assertThat(optColumnTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the OptColumnType in Elasticsearch
        verify(mockOptColumnTypeSearchRepository, times(0)).save(optColumnType);
    }


    @Test
    @Transactional
    public void getAllOptColumnTypes() throws Exception {
        // Initialize the database
        optColumnTypeRepository.saveAndFlush(optColumnType);

        // Get all the optColumnTypeList
        restOptColumnTypeMockMvc.perform(get("/api/opt-column-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optColumnType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getOptColumnType() throws Exception {
        // Initialize the database
        optColumnTypeRepository.saveAndFlush(optColumnType);

        // Get the optColumnType
        restOptColumnTypeMockMvc.perform(get("/api/opt-column-types/{id}", optColumnType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(optColumnType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingOptColumnType() throws Exception {
        // Get the optColumnType
        restOptColumnTypeMockMvc.perform(get("/api/opt-column-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOptColumnType() throws Exception {
        // Initialize the database
        optColumnTypeRepository.saveAndFlush(optColumnType);

        int databaseSizeBeforeUpdate = optColumnTypeRepository.findAll().size();

        // Update the optColumnType
        OptColumnType updatedOptColumnType = optColumnTypeRepository.findById(optColumnType.getId()).get();
        // Disconnect from session so that the updates on updatedOptColumnType are not directly saved in db
        em.detach(updatedOptColumnType);
        updatedOptColumnType.setName(UPDATED_NAME);
        updatedOptColumnType.setDescription(UPDATED_DESCRIPTION);

        restOptColumnTypeMockMvc.perform(put("/api/opt-column-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOptColumnType)))
            .andExpect(status().isOk());

        // Validate the OptColumnType in the database
        List<OptColumnType> optColumnTypeList = optColumnTypeRepository.findAll();
        assertThat(optColumnTypeList).hasSize(databaseSizeBeforeUpdate);
        OptColumnType testOptColumnType = optColumnTypeList.get(optColumnTypeList.size() - 1);
        assertThat(testOptColumnType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOptColumnType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the OptColumnType in Elasticsearch
        verify(mockOptColumnTypeSearchRepository, times(1)).save(testOptColumnType);
    }

    @Test
    @Transactional
    public void updateNonExistingOptColumnType() throws Exception {
        int databaseSizeBeforeUpdate = optColumnTypeRepository.findAll().size();

        // Create the OptColumnType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptColumnTypeMockMvc.perform(put("/api/opt-column-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optColumnType)))
            .andExpect(status().isBadRequest());

        // Validate the OptColumnType in the database
        List<OptColumnType> optColumnTypeList = optColumnTypeRepository.findAll();
        assertThat(optColumnTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OptColumnType in Elasticsearch
        verify(mockOptColumnTypeSearchRepository, times(0)).save(optColumnType);
    }

    @Test
    @Transactional
    public void deleteOptColumnType() throws Exception {
        // Initialize the database
        optColumnTypeRepository.saveAndFlush(optColumnType);

        int databaseSizeBeforeDelete = optColumnTypeRepository.findAll().size();

        // Delete the optColumnType
        restOptColumnTypeMockMvc.perform(delete("/api/opt-column-types/{id}", optColumnType.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OptColumnType> optColumnTypeList = optColumnTypeRepository.findAll();
        assertThat(optColumnTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the OptColumnType in Elasticsearch
        verify(mockOptColumnTypeSearchRepository, times(1)).deleteById(optColumnType.getId());
    }

    @Test
    @Transactional
    public void searchOptColumnType() throws Exception {
        // Initialize the database
        optColumnTypeRepository.saveAndFlush(optColumnType);
        when(mockOptColumnTypeSearchRepository.search(queryStringQuery("id:" + optColumnType.getId())))
            .thenReturn(Collections.singletonList(optColumnType));
        // Search the optColumnType
        restOptColumnTypeMockMvc.perform(get("/api/_search/opt-column-types?query=id:" + optColumnType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optColumnType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
