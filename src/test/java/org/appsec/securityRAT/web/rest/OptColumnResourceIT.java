package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.SecurityRatApp;
import org.appsec.securityrat.domain.OptColumn;
import org.appsec.securityrat.repository.OptColumnRepository;
import org.appsec.securityrat.repository.search.OptColumnSearchRepository;
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
 * Integration tests for the {@link OptColumnResource} REST controller.
 */
@SpringBootTest(classes = SecurityRatApp.class)
public class OptColumnResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SHOW_ORDER = 1;
    private static final Integer UPDATED_SHOW_ORDER = 2;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private OptColumnRepository optColumnRepository;

    /**
     * This repository is mocked in the org.appsec.securityrat.repository.search test package.
     *
     * @see org.appsec.securityrat.repository.search.OptColumnSearchRepositoryMockConfiguration
     */
    @Autowired
    private OptColumnSearchRepository mockOptColumnSearchRepository;

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

    private MockMvc restOptColumnMockMvc;

    private OptColumn optColumn;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OptColumnResource optColumnResource = new OptColumnResource(optColumnRepository, mockOptColumnSearchRepository);
        this.restOptColumnMockMvc = MockMvcBuilders.standaloneSetup(optColumnResource)
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
    public static OptColumn createEntity(EntityManager em) {
        OptColumn optColumn = new OptColumn();
        optColumn.setName(DEFAULT_NAME);
        optColumn.setDescription(DEFAULT_DESCRIPTION);
        optColumn.setShowOrder(DEFAULT_SHOW_ORDER);
        optColumn.setActive(DEFAULT_ACTIVE);
        return optColumn;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OptColumn createUpdatedEntity(EntityManager em) {
        OptColumn optColumn = new OptColumn();
        optColumn.setName(UPDATED_NAME);
        optColumn.setDescription(UPDATED_DESCRIPTION);
        optColumn.setShowOrder(UPDATED_SHOW_ORDER);
        optColumn.setActive(UPDATED_ACTIVE);
        return optColumn;
    }

    @BeforeEach
    public void initTest() {
        optColumn = createEntity(em);
    }

    @Test
    @Transactional
    public void createOptColumn() throws Exception {
        int databaseSizeBeforeCreate = optColumnRepository.findAll().size();

        // Create the OptColumn
        restOptColumnMockMvc.perform(post("/api/opt-columns")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optColumn)))
            .andExpect(status().isCreated());

        // Validate the OptColumn in the database
        List<OptColumn> optColumnList = optColumnRepository.findAll();
        assertThat(optColumnList).hasSize(databaseSizeBeforeCreate + 1);
        OptColumn testOptColumn = optColumnList.get(optColumnList.size() - 1);
        assertThat(testOptColumn.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOptColumn.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOptColumn.getShowOrder()).isEqualTo(DEFAULT_SHOW_ORDER);
        assertThat(testOptColumn.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the OptColumn in Elasticsearch
        verify(mockOptColumnSearchRepository, times(1)).save(testOptColumn);
    }

    @Test
    @Transactional
    public void createOptColumnWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = optColumnRepository.findAll().size();

        // Create the OptColumn with an existing ID
        optColumn.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOptColumnMockMvc.perform(post("/api/opt-columns")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optColumn)))
            .andExpect(status().isBadRequest());

        // Validate the OptColumn in the database
        List<OptColumn> optColumnList = optColumnRepository.findAll();
        assertThat(optColumnList).hasSize(databaseSizeBeforeCreate);

        // Validate the OptColumn in Elasticsearch
        verify(mockOptColumnSearchRepository, times(0)).save(optColumn);
    }


    @Test
    @Transactional
    public void getAllOptColumns() throws Exception {
        // Initialize the database
        optColumnRepository.saveAndFlush(optColumn);

        // Get all the optColumnList
        restOptColumnMockMvc.perform(get("/api/opt-columns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optColumn.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].showOrder").value(hasItem(DEFAULT_SHOW_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getOptColumn() throws Exception {
        // Initialize the database
        optColumnRepository.saveAndFlush(optColumn);

        // Get the optColumn
        restOptColumnMockMvc.perform(get("/api/opt-columns/{id}", optColumn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(optColumn.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.showOrder").value(DEFAULT_SHOW_ORDER))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOptColumn() throws Exception {
        // Get the optColumn
        restOptColumnMockMvc.perform(get("/api/opt-columns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOptColumn() throws Exception {
        // Initialize the database
        optColumnRepository.saveAndFlush(optColumn);

        int databaseSizeBeforeUpdate = optColumnRepository.findAll().size();

        // Update the optColumn
        OptColumn updatedOptColumn = optColumnRepository.findById(optColumn.getId()).get();
        // Disconnect from session so that the updates on updatedOptColumn are not directly saved in db
        em.detach(updatedOptColumn);
        updatedOptColumn.setName(UPDATED_NAME);
        updatedOptColumn.setDescription(UPDATED_DESCRIPTION);
        updatedOptColumn.setShowOrder(UPDATED_SHOW_ORDER);
        updatedOptColumn.setActive(UPDATED_ACTIVE);

        restOptColumnMockMvc.perform(put("/api/opt-columns")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOptColumn)))
            .andExpect(status().isOk());

        // Validate the OptColumn in the database
        List<OptColumn> optColumnList = optColumnRepository.findAll();
        assertThat(optColumnList).hasSize(databaseSizeBeforeUpdate);
        OptColumn testOptColumn = optColumnList.get(optColumnList.size() - 1);
        assertThat(testOptColumn.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOptColumn.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOptColumn.getShowOrder()).isEqualTo(UPDATED_SHOW_ORDER);
        assertThat(testOptColumn.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the OptColumn in Elasticsearch
        verify(mockOptColumnSearchRepository, times(1)).save(testOptColumn);
    }

    @Test
    @Transactional
    public void updateNonExistingOptColumn() throws Exception {
        int databaseSizeBeforeUpdate = optColumnRepository.findAll().size();

        // Create the OptColumn

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptColumnMockMvc.perform(put("/api/opt-columns")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optColumn)))
            .andExpect(status().isBadRequest());

        // Validate the OptColumn in the database
        List<OptColumn> optColumnList = optColumnRepository.findAll();
        assertThat(optColumnList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OptColumn in Elasticsearch
        verify(mockOptColumnSearchRepository, times(0)).save(optColumn);
    }

    @Test
    @Transactional
    public void deleteOptColumn() throws Exception {
        // Initialize the database
        optColumnRepository.saveAndFlush(optColumn);

        int databaseSizeBeforeDelete = optColumnRepository.findAll().size();

        // Delete the optColumn
        restOptColumnMockMvc.perform(delete("/api/opt-columns/{id}", optColumn.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OptColumn> optColumnList = optColumnRepository.findAll();
        assertThat(optColumnList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the OptColumn in Elasticsearch
        verify(mockOptColumnSearchRepository, times(1)).deleteById(optColumn.getId());
    }

    @Test
    @Transactional
    public void searchOptColumn() throws Exception {
        // Initialize the database
        optColumnRepository.saveAndFlush(optColumn);
        when(mockOptColumnSearchRepository.search(queryStringQuery("id:" + optColumn.getId())))
            .thenReturn(Collections.singletonList(optColumn));
        // Search the optColumn
        restOptColumnMockMvc.perform(get("/api/_search/opt-columns?query=id:" + optColumn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optColumn.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].showOrder").value(hasItem(DEFAULT_SHOW_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
}
