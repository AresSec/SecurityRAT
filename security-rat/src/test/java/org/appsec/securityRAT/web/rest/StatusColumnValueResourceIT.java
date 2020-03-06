package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.SecurityRatApp;
import org.appsec.securityrat.domain.StatusColumnValue;
import org.appsec.securityrat.repository.StatusColumnValueRepository;
import org.appsec.securityrat.repository.search.StatusColumnValueSearchRepository;
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
 * Integration tests for the {@link StatusColumnValueResource} REST controller.
 */
@SpringBootTest(classes = SecurityRatApp.class)
public class StatusColumnValueResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SHOW_ORDER = 1;
    private static final Integer UPDATED_SHOW_ORDER = 2;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private StatusColumnValueRepository statusColumnValueRepository;

    /**
     * This repository is mocked in the org.appsec.securityrat.repository.search test package.
     *
     * @see org.appsec.securityrat.repository.search.StatusColumnValueSearchRepositoryMockConfiguration
     */
    @Autowired
    private StatusColumnValueSearchRepository mockStatusColumnValueSearchRepository;

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

    private MockMvc restStatusColumnValueMockMvc;

    private StatusColumnValue statusColumnValue;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StatusColumnValueResource statusColumnValueResource = new StatusColumnValueResource(statusColumnValueRepository, mockStatusColumnValueSearchRepository);
        this.restStatusColumnValueMockMvc = MockMvcBuilders.standaloneSetup(statusColumnValueResource)
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
    public static StatusColumnValue createEntity(EntityManager em) {
        StatusColumnValue statusColumnValue = new StatusColumnValue();
        statusColumnValue.setName(DEFAULT_NAME);
        statusColumnValue.setDescription(DEFAULT_DESCRIPTION);
        statusColumnValue.setShowOrder(DEFAULT_SHOW_ORDER);
        statusColumnValue.setActive(DEFAULT_ACTIVE);
        return statusColumnValue;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatusColumnValue createUpdatedEntity(EntityManager em) {
        StatusColumnValue statusColumnValue = new StatusColumnValue();
        statusColumnValue.setName(UPDATED_NAME);
        statusColumnValue.setDescription(UPDATED_DESCRIPTION);
        statusColumnValue.setShowOrder(UPDATED_SHOW_ORDER);
        statusColumnValue.setActive(UPDATED_ACTIVE);
        return statusColumnValue;
    }

    @BeforeEach
    public void initTest() {
        statusColumnValue = createEntity(em);
    }

    @Test
    @Transactional
    public void createStatusColumnValue() throws Exception {
        int databaseSizeBeforeCreate = statusColumnValueRepository.findAll().size();

        // Create the StatusColumnValue
        restStatusColumnValueMockMvc.perform(post("/api/status-column-values")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statusColumnValue)))
            .andExpect(status().isCreated());

        // Validate the StatusColumnValue in the database
        List<StatusColumnValue> statusColumnValueList = statusColumnValueRepository.findAll();
        assertThat(statusColumnValueList).hasSize(databaseSizeBeforeCreate + 1);
        StatusColumnValue testStatusColumnValue = statusColumnValueList.get(statusColumnValueList.size() - 1);
        assertThat(testStatusColumnValue.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStatusColumnValue.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStatusColumnValue.getShowOrder()).isEqualTo(DEFAULT_SHOW_ORDER);
        assertThat(testStatusColumnValue.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the StatusColumnValue in Elasticsearch
        verify(mockStatusColumnValueSearchRepository, times(1)).save(testStatusColumnValue);
    }

    @Test
    @Transactional
    public void createStatusColumnValueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statusColumnValueRepository.findAll().size();

        // Create the StatusColumnValue with an existing ID
        statusColumnValue.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatusColumnValueMockMvc.perform(post("/api/status-column-values")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statusColumnValue)))
            .andExpect(status().isBadRequest());

        // Validate the StatusColumnValue in the database
        List<StatusColumnValue> statusColumnValueList = statusColumnValueRepository.findAll();
        assertThat(statusColumnValueList).hasSize(databaseSizeBeforeCreate);

        // Validate the StatusColumnValue in Elasticsearch
        verify(mockStatusColumnValueSearchRepository, times(0)).save(statusColumnValue);
    }


    @Test
    @Transactional
    public void getAllStatusColumnValues() throws Exception {
        // Initialize the database
        statusColumnValueRepository.saveAndFlush(statusColumnValue);

        // Get all the statusColumnValueList
        restStatusColumnValueMockMvc.perform(get("/api/status-column-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statusColumnValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].showOrder").value(hasItem(DEFAULT_SHOW_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getStatusColumnValue() throws Exception {
        // Initialize the database
        statusColumnValueRepository.saveAndFlush(statusColumnValue);

        // Get the statusColumnValue
        restStatusColumnValueMockMvc.perform(get("/api/status-column-values/{id}", statusColumnValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(statusColumnValue.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.showOrder").value(DEFAULT_SHOW_ORDER))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStatusColumnValue() throws Exception {
        // Get the statusColumnValue
        restStatusColumnValueMockMvc.perform(get("/api/status-column-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatusColumnValue() throws Exception {
        // Initialize the database
        statusColumnValueRepository.saveAndFlush(statusColumnValue);

        int databaseSizeBeforeUpdate = statusColumnValueRepository.findAll().size();

        // Update the statusColumnValue
        StatusColumnValue updatedStatusColumnValue = statusColumnValueRepository.findById(statusColumnValue.getId()).get();
        // Disconnect from session so that the updates on updatedStatusColumnValue are not directly saved in db
        em.detach(updatedStatusColumnValue);
        updatedStatusColumnValue.setName(UPDATED_NAME);
        updatedStatusColumnValue.setDescription(UPDATED_DESCRIPTION);
        updatedStatusColumnValue.setShowOrder(UPDATED_SHOW_ORDER);
        updatedStatusColumnValue.setActive(UPDATED_ACTIVE);

        restStatusColumnValueMockMvc.perform(put("/api/status-column-values")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStatusColumnValue)))
            .andExpect(status().isOk());

        // Validate the StatusColumnValue in the database
        List<StatusColumnValue> statusColumnValueList = statusColumnValueRepository.findAll();
        assertThat(statusColumnValueList).hasSize(databaseSizeBeforeUpdate);
        StatusColumnValue testStatusColumnValue = statusColumnValueList.get(statusColumnValueList.size() - 1);
        assertThat(testStatusColumnValue.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStatusColumnValue.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStatusColumnValue.getShowOrder()).isEqualTo(UPDATED_SHOW_ORDER);
        assertThat(testStatusColumnValue.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the StatusColumnValue in Elasticsearch
        verify(mockStatusColumnValueSearchRepository, times(1)).save(testStatusColumnValue);
    }

    @Test
    @Transactional
    public void updateNonExistingStatusColumnValue() throws Exception {
        int databaseSizeBeforeUpdate = statusColumnValueRepository.findAll().size();

        // Create the StatusColumnValue

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusColumnValueMockMvc.perform(put("/api/status-column-values")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statusColumnValue)))
            .andExpect(status().isBadRequest());

        // Validate the StatusColumnValue in the database
        List<StatusColumnValue> statusColumnValueList = statusColumnValueRepository.findAll();
        assertThat(statusColumnValueList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StatusColumnValue in Elasticsearch
        verify(mockStatusColumnValueSearchRepository, times(0)).save(statusColumnValue);
    }

    @Test
    @Transactional
    public void deleteStatusColumnValue() throws Exception {
        // Initialize the database
        statusColumnValueRepository.saveAndFlush(statusColumnValue);

        int databaseSizeBeforeDelete = statusColumnValueRepository.findAll().size();

        // Delete the statusColumnValue
        restStatusColumnValueMockMvc.perform(delete("/api/status-column-values/{id}", statusColumnValue.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StatusColumnValue> statusColumnValueList = statusColumnValueRepository.findAll();
        assertThat(statusColumnValueList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StatusColumnValue in Elasticsearch
        verify(mockStatusColumnValueSearchRepository, times(1)).deleteById(statusColumnValue.getId());
    }

    @Test
    @Transactional
    public void searchStatusColumnValue() throws Exception {
        // Initialize the database
        statusColumnValueRepository.saveAndFlush(statusColumnValue);
        when(mockStatusColumnValueSearchRepository.search(queryStringQuery("id:" + statusColumnValue.getId())))
            .thenReturn(Collections.singletonList(statusColumnValue));
        // Search the statusColumnValue
        restStatusColumnValueMockMvc.perform(get("/api/_search/status-column-values?query=id:" + statusColumnValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statusColumnValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].showOrder").value(hasItem(DEFAULT_SHOW_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
}
