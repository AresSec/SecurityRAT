package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.SecurityRatApp;
import org.appsec.securityrat.domain.StatusColumn;
import org.appsec.securityrat.repository.StatusColumnRepository;
import org.appsec.securityrat.repository.search.StatusColumnSearchRepository;
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
 * Integration tests for the {@link StatusColumnResource} REST controller.
 */
@SpringBootTest(classes = SecurityRatApp.class)
public class StatusColumnResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ENUM = false;
    private static final Boolean UPDATED_IS_ENUM = true;

    private static final Integer DEFAULT_SHOW_ORDER = 1;
    private static final Integer UPDATED_SHOW_ORDER = 2;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private StatusColumnRepository statusColumnRepository;

    /**
     * This repository is mocked in the org.appsec.securityrat.repository.search test package.
     *
     * @see org.appsec.securityrat.repository.search.StatusColumnSearchRepositoryMockConfiguration
     */
    @Autowired
    private StatusColumnSearchRepository mockStatusColumnSearchRepository;

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

    private MockMvc restStatusColumnMockMvc;

    private StatusColumn statusColumn;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StatusColumnResource statusColumnResource = new StatusColumnResource(statusColumnRepository, mockStatusColumnSearchRepository);
        this.restStatusColumnMockMvc = MockMvcBuilders.standaloneSetup(statusColumnResource)
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
    public static StatusColumn createEntity(EntityManager em) {
        StatusColumn statusColumn = new StatusColumn();
        statusColumn.setName(DEFAULT_NAME);
        statusColumn.setDescription(DEFAULT_DESCRIPTION);
        statusColumn.setIsEnum(DEFAULT_IS_ENUM);
        statusColumn.setShowOrder(DEFAULT_SHOW_ORDER);
        statusColumn.setActive(DEFAULT_ACTIVE);
        return statusColumn;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatusColumn createUpdatedEntity(EntityManager em) {
        StatusColumn statusColumn = new StatusColumn();
        statusColumn.setName(UPDATED_NAME);
        statusColumn.setDescription(UPDATED_DESCRIPTION);
        statusColumn.setIsEnum(UPDATED_IS_ENUM);
        statusColumn.setShowOrder(UPDATED_SHOW_ORDER);
        statusColumn.setActive(UPDATED_ACTIVE);
        return statusColumn;
    }

    @BeforeEach
    public void initTest() {
        statusColumn = createEntity(em);
    }

    @Test
    @Transactional
    public void createStatusColumn() throws Exception {
        int databaseSizeBeforeCreate = statusColumnRepository.findAll().size();

        // Create the StatusColumn
        restStatusColumnMockMvc.perform(post("/api/status-columns")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statusColumn)))
            .andExpect(status().isCreated());

        // Validate the StatusColumn in the database
        List<StatusColumn> statusColumnList = statusColumnRepository.findAll();
        assertThat(statusColumnList).hasSize(databaseSizeBeforeCreate + 1);
        StatusColumn testStatusColumn = statusColumnList.get(statusColumnList.size() - 1);
        assertThat(testStatusColumn.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStatusColumn.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStatusColumn.isIsEnum()).isEqualTo(DEFAULT_IS_ENUM);
        assertThat(testStatusColumn.getShowOrder()).isEqualTo(DEFAULT_SHOW_ORDER);
        assertThat(testStatusColumn.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the StatusColumn in Elasticsearch
        verify(mockStatusColumnSearchRepository, times(1)).save(testStatusColumn);
    }

    @Test
    @Transactional
    public void createStatusColumnWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statusColumnRepository.findAll().size();

        // Create the StatusColumn with an existing ID
        statusColumn.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatusColumnMockMvc.perform(post("/api/status-columns")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statusColumn)))
            .andExpect(status().isBadRequest());

        // Validate the StatusColumn in the database
        List<StatusColumn> statusColumnList = statusColumnRepository.findAll();
        assertThat(statusColumnList).hasSize(databaseSizeBeforeCreate);

        // Validate the StatusColumn in Elasticsearch
        verify(mockStatusColumnSearchRepository, times(0)).save(statusColumn);
    }


    @Test
    @Transactional
    public void getAllStatusColumns() throws Exception {
        // Initialize the database
        statusColumnRepository.saveAndFlush(statusColumn);

        // Get all the statusColumnList
        restStatusColumnMockMvc.perform(get("/api/status-columns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statusColumn.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isEnum").value(hasItem(DEFAULT_IS_ENUM.booleanValue())))
            .andExpect(jsonPath("$.[*].showOrder").value(hasItem(DEFAULT_SHOW_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getStatusColumn() throws Exception {
        // Initialize the database
        statusColumnRepository.saveAndFlush(statusColumn);

        // Get the statusColumn
        restStatusColumnMockMvc.perform(get("/api/status-columns/{id}", statusColumn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(statusColumn.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isEnum").value(DEFAULT_IS_ENUM.booleanValue()))
            .andExpect(jsonPath("$.showOrder").value(DEFAULT_SHOW_ORDER))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStatusColumn() throws Exception {
        // Get the statusColumn
        restStatusColumnMockMvc.perform(get("/api/status-columns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatusColumn() throws Exception {
        // Initialize the database
        statusColumnRepository.saveAndFlush(statusColumn);

        int databaseSizeBeforeUpdate = statusColumnRepository.findAll().size();

        // Update the statusColumn
        StatusColumn updatedStatusColumn = statusColumnRepository.findById(statusColumn.getId()).get();
        // Disconnect from session so that the updates on updatedStatusColumn are not directly saved in db
        em.detach(updatedStatusColumn);
        updatedStatusColumn.setName(UPDATED_NAME);
        updatedStatusColumn.setDescription(UPDATED_DESCRIPTION);
        updatedStatusColumn.setIsEnum(UPDATED_IS_ENUM);
        updatedStatusColumn.setShowOrder(UPDATED_SHOW_ORDER);
        updatedStatusColumn.setActive(UPDATED_ACTIVE);

        restStatusColumnMockMvc.perform(put("/api/status-columns")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStatusColumn)))
            .andExpect(status().isOk());

        // Validate the StatusColumn in the database
        List<StatusColumn> statusColumnList = statusColumnRepository.findAll();
        assertThat(statusColumnList).hasSize(databaseSizeBeforeUpdate);
        StatusColumn testStatusColumn = statusColumnList.get(statusColumnList.size() - 1);
        assertThat(testStatusColumn.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStatusColumn.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStatusColumn.isIsEnum()).isEqualTo(UPDATED_IS_ENUM);
        assertThat(testStatusColumn.getShowOrder()).isEqualTo(UPDATED_SHOW_ORDER);
        assertThat(testStatusColumn.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the StatusColumn in Elasticsearch
        verify(mockStatusColumnSearchRepository, times(1)).save(testStatusColumn);
    }

    @Test
    @Transactional
    public void updateNonExistingStatusColumn() throws Exception {
        int databaseSizeBeforeUpdate = statusColumnRepository.findAll().size();

        // Create the StatusColumn

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusColumnMockMvc.perform(put("/api/status-columns")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statusColumn)))
            .andExpect(status().isBadRequest());

        // Validate the StatusColumn in the database
        List<StatusColumn> statusColumnList = statusColumnRepository.findAll();
        assertThat(statusColumnList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StatusColumn in Elasticsearch
        verify(mockStatusColumnSearchRepository, times(0)).save(statusColumn);
    }

    @Test
    @Transactional
    public void deleteStatusColumn() throws Exception {
        // Initialize the database
        statusColumnRepository.saveAndFlush(statusColumn);

        int databaseSizeBeforeDelete = statusColumnRepository.findAll().size();

        // Delete the statusColumn
        restStatusColumnMockMvc.perform(delete("/api/status-columns/{id}", statusColumn.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StatusColumn> statusColumnList = statusColumnRepository.findAll();
        assertThat(statusColumnList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StatusColumn in Elasticsearch
        verify(mockStatusColumnSearchRepository, times(1)).deleteById(statusColumn.getId());
    }

    @Test
    @Transactional
    public void searchStatusColumn() throws Exception {
        // Initialize the database
        statusColumnRepository.saveAndFlush(statusColumn);
        when(mockStatusColumnSearchRepository.search(queryStringQuery("id:" + statusColumn.getId())))
            .thenReturn(Collections.singletonList(statusColumn));
        // Search the statusColumn
        restStatusColumnMockMvc.perform(get("/api/_search/status-columns?query=id:" + statusColumn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statusColumn.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isEnum").value(hasItem(DEFAULT_IS_ENUM.booleanValue())))
            .andExpect(jsonPath("$.[*].showOrder").value(hasItem(DEFAULT_SHOW_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
}
