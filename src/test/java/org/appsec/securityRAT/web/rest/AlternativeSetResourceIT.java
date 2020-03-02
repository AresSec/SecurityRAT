package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.SecurityRatApp;
import org.appsec.securityrat.domain.AlternativeSet;
import org.appsec.securityrat.repository.AlternativeSetRepository;
import org.appsec.securityrat.repository.search.AlternativeSetSearchRepository;
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
 * Integration tests for the {@link AlternativeSetResource} REST controller.
 */
@SpringBootTest(classes = SecurityRatApp.class)
public class AlternativeSetResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SHOW_ORDER = 1;
    private static final Integer UPDATED_SHOW_ORDER = 2;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private AlternativeSetRepository alternativeSetRepository;

    /**
     * This repository is mocked in the org.appsec.securityrat.repository.search test package.
     *
     * @see org.appsec.securityrat.repository.search.AlternativeSetSearchRepositoryMockConfiguration
     */
    @Autowired
    private AlternativeSetSearchRepository mockAlternativeSetSearchRepository;

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

    private MockMvc restAlternativeSetMockMvc;

    private AlternativeSet alternativeSet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlternativeSetResource alternativeSetResource = new AlternativeSetResource(alternativeSetRepository, mockAlternativeSetSearchRepository);
        this.restAlternativeSetMockMvc = MockMvcBuilders.standaloneSetup(alternativeSetResource)
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
    public static AlternativeSet createEntity(EntityManager em) {
        AlternativeSet alternativeSet = new AlternativeSet();
        alternativeSet.setName(DEFAULT_NAME);
        alternativeSet.setDescription(DEFAULT_DESCRIPTION);
        alternativeSet.setShowOrder(DEFAULT_SHOW_ORDER);
        alternativeSet.setActive(DEFAULT_ACTIVE);
        return alternativeSet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlternativeSet createUpdatedEntity(EntityManager em) {
        AlternativeSet alternativeSet = new AlternativeSet();
        alternativeSet.setName(UPDATED_NAME);
        alternativeSet.setDescription(UPDATED_DESCRIPTION);
        alternativeSet.setShowOrder(UPDATED_SHOW_ORDER);
        alternativeSet.setActive(UPDATED_ACTIVE);
        return alternativeSet;
    }

    @BeforeEach
    public void initTest() {
        alternativeSet = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlternativeSet() throws Exception {
        int databaseSizeBeforeCreate = alternativeSetRepository.findAll().size();

        // Create the AlternativeSet
        restAlternativeSetMockMvc.perform(post("/api/alternative-sets")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alternativeSet)))
            .andExpect(status().isCreated());

        // Validate the AlternativeSet in the database
        List<AlternativeSet> alternativeSetList = alternativeSetRepository.findAll();
        assertThat(alternativeSetList).hasSize(databaseSizeBeforeCreate + 1);
        AlternativeSet testAlternativeSet = alternativeSetList.get(alternativeSetList.size() - 1);
        assertThat(testAlternativeSet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAlternativeSet.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAlternativeSet.getShowOrder()).isEqualTo(DEFAULT_SHOW_ORDER);
        assertThat(testAlternativeSet.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the AlternativeSet in Elasticsearch
        verify(mockAlternativeSetSearchRepository, times(1)).save(testAlternativeSet);
    }

    @Test
    @Transactional
    public void createAlternativeSetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alternativeSetRepository.findAll().size();

        // Create the AlternativeSet with an existing ID
        alternativeSet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlternativeSetMockMvc.perform(post("/api/alternative-sets")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alternativeSet)))
            .andExpect(status().isBadRequest());

        // Validate the AlternativeSet in the database
        List<AlternativeSet> alternativeSetList = alternativeSetRepository.findAll();
        assertThat(alternativeSetList).hasSize(databaseSizeBeforeCreate);

        // Validate the AlternativeSet in Elasticsearch
        verify(mockAlternativeSetSearchRepository, times(0)).save(alternativeSet);
    }


    @Test
    @Transactional
    public void getAllAlternativeSets() throws Exception {
        // Initialize the database
        alternativeSetRepository.saveAndFlush(alternativeSet);

        // Get all the alternativeSetList
        restAlternativeSetMockMvc.perform(get("/api/alternative-sets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alternativeSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].showOrder").value(hasItem(DEFAULT_SHOW_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAlternativeSet() throws Exception {
        // Initialize the database
        alternativeSetRepository.saveAndFlush(alternativeSet);

        // Get the alternativeSet
        restAlternativeSetMockMvc.perform(get("/api/alternative-sets/{id}", alternativeSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alternativeSet.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.showOrder").value(DEFAULT_SHOW_ORDER))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAlternativeSet() throws Exception {
        // Get the alternativeSet
        restAlternativeSetMockMvc.perform(get("/api/alternative-sets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlternativeSet() throws Exception {
        // Initialize the database
        alternativeSetRepository.saveAndFlush(alternativeSet);

        int databaseSizeBeforeUpdate = alternativeSetRepository.findAll().size();

        // Update the alternativeSet
        AlternativeSet updatedAlternativeSet = alternativeSetRepository.findById(alternativeSet.getId()).get();
        // Disconnect from session so that the updates on updatedAlternativeSet are not directly saved in db
        em.detach(updatedAlternativeSet);
        updatedAlternativeSet.setName(UPDATED_NAME);
        updatedAlternativeSet.setDescription(UPDATED_DESCRIPTION);
        updatedAlternativeSet.setShowOrder(UPDATED_SHOW_ORDER);
        updatedAlternativeSet.setActive(UPDATED_ACTIVE);

        restAlternativeSetMockMvc.perform(put("/api/alternative-sets")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAlternativeSet)))
            .andExpect(status().isOk());

        // Validate the AlternativeSet in the database
        List<AlternativeSet> alternativeSetList = alternativeSetRepository.findAll();
        assertThat(alternativeSetList).hasSize(databaseSizeBeforeUpdate);
        AlternativeSet testAlternativeSet = alternativeSetList.get(alternativeSetList.size() - 1);
        assertThat(testAlternativeSet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAlternativeSet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAlternativeSet.getShowOrder()).isEqualTo(UPDATED_SHOW_ORDER);
        assertThat(testAlternativeSet.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the AlternativeSet in Elasticsearch
        verify(mockAlternativeSetSearchRepository, times(1)).save(testAlternativeSet);
    }

    @Test
    @Transactional
    public void updateNonExistingAlternativeSet() throws Exception {
        int databaseSizeBeforeUpdate = alternativeSetRepository.findAll().size();

        // Create the AlternativeSet

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlternativeSetMockMvc.perform(put("/api/alternative-sets")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alternativeSet)))
            .andExpect(status().isBadRequest());

        // Validate the AlternativeSet in the database
        List<AlternativeSet> alternativeSetList = alternativeSetRepository.findAll();
        assertThat(alternativeSetList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AlternativeSet in Elasticsearch
        verify(mockAlternativeSetSearchRepository, times(0)).save(alternativeSet);
    }

    @Test
    @Transactional
    public void deleteAlternativeSet() throws Exception {
        // Initialize the database
        alternativeSetRepository.saveAndFlush(alternativeSet);

        int databaseSizeBeforeDelete = alternativeSetRepository.findAll().size();

        // Delete the alternativeSet
        restAlternativeSetMockMvc.perform(delete("/api/alternative-sets/{id}", alternativeSet.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlternativeSet> alternativeSetList = alternativeSetRepository.findAll();
        assertThat(alternativeSetList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AlternativeSet in Elasticsearch
        verify(mockAlternativeSetSearchRepository, times(1)).deleteById(alternativeSet.getId());
    }

    @Test
    @Transactional
    public void searchAlternativeSet() throws Exception {
        // Initialize the database
        alternativeSetRepository.saveAndFlush(alternativeSet);
        when(mockAlternativeSetSearchRepository.search(queryStringQuery("id:" + alternativeSet.getId())))
            .thenReturn(Collections.singletonList(alternativeSet));
        // Search the alternativeSet
        restAlternativeSetMockMvc.perform(get("/api/_search/alternative-sets?query=id:" + alternativeSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alternativeSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].showOrder").value(hasItem(DEFAULT_SHOW_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
}
