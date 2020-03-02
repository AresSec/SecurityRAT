package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.SecurityRatApp;
import org.appsec.securityrat.domain.CollectionInstance;
import org.appsec.securityrat.repository.CollectionInstanceRepository;
import org.appsec.securityrat.repository.search.CollectionInstanceSearchRepository;
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
 * Integration tests for the {@link CollectionInstanceResource} REST controller.
 */
@SpringBootTest(classes = SecurityRatApp.class)
public class CollectionInstanceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SHOW_ORDER = 1;
    private static final Integer UPDATED_SHOW_ORDER = 2;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CollectionInstanceRepository collectionInstanceRepository;

    /**
     * This repository is mocked in the org.appsec.securityrat.repository.search test package.
     *
     * @see org.appsec.securityrat.repository.search.CollectionInstanceSearchRepositoryMockConfiguration
     */
    @Autowired
    private CollectionInstanceSearchRepository mockCollectionInstanceSearchRepository;

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

    private MockMvc restCollectionInstanceMockMvc;

    private CollectionInstance collectionInstance;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CollectionInstanceResource collectionInstanceResource = new CollectionInstanceResource(collectionInstanceRepository, mockCollectionInstanceSearchRepository);
        this.restCollectionInstanceMockMvc = MockMvcBuilders.standaloneSetup(collectionInstanceResource)
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
    public static CollectionInstance createEntity(EntityManager em) {
        CollectionInstance collectionInstance = new CollectionInstance();
        collectionInstance.setName(DEFAULT_NAME);
        collectionInstance.setDescription(DEFAULT_DESCRIPTION);
        collectionInstance.setShowOrder(DEFAULT_SHOW_ORDER);
        collectionInstance.setActive(DEFAULT_ACTIVE);
        return collectionInstance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CollectionInstance createUpdatedEntity(EntityManager em) {
        CollectionInstance collectionInstance = new CollectionInstance();
        collectionInstance.setName(UPDATED_NAME);
        collectionInstance.setDescription(UPDATED_DESCRIPTION);
        collectionInstance.setShowOrder(UPDATED_SHOW_ORDER);
        collectionInstance.setActive(UPDATED_ACTIVE);
        return collectionInstance;
    }

    @BeforeEach
    public void initTest() {
        collectionInstance = createEntity(em);
    }

    @Test
    @Transactional
    public void createCollectionInstance() throws Exception {
        int databaseSizeBeforeCreate = collectionInstanceRepository.findAll().size();

        // Create the CollectionInstance
        restCollectionInstanceMockMvc.perform(post("/api/collection-instances")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collectionInstance)))
            .andExpect(status().isCreated());

        // Validate the CollectionInstance in the database
        List<CollectionInstance> collectionInstanceList = collectionInstanceRepository.findAll();
        assertThat(collectionInstanceList).hasSize(databaseSizeBeforeCreate + 1);
        CollectionInstance testCollectionInstance = collectionInstanceList.get(collectionInstanceList.size() - 1);
        assertThat(testCollectionInstance.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCollectionInstance.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCollectionInstance.getShowOrder()).isEqualTo(DEFAULT_SHOW_ORDER);
        assertThat(testCollectionInstance.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the CollectionInstance in Elasticsearch
        verify(mockCollectionInstanceSearchRepository, times(1)).save(testCollectionInstance);
    }

    @Test
    @Transactional
    public void createCollectionInstanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collectionInstanceRepository.findAll().size();

        // Create the CollectionInstance with an existing ID
        collectionInstance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollectionInstanceMockMvc.perform(post("/api/collection-instances")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collectionInstance)))
            .andExpect(status().isBadRequest());

        // Validate the CollectionInstance in the database
        List<CollectionInstance> collectionInstanceList = collectionInstanceRepository.findAll();
        assertThat(collectionInstanceList).hasSize(databaseSizeBeforeCreate);

        // Validate the CollectionInstance in Elasticsearch
        verify(mockCollectionInstanceSearchRepository, times(0)).save(collectionInstance);
    }


    @Test
    @Transactional
    public void getAllCollectionInstances() throws Exception {
        // Initialize the database
        collectionInstanceRepository.saveAndFlush(collectionInstance);

        // Get all the collectionInstanceList
        restCollectionInstanceMockMvc.perform(get("/api/collection-instances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collectionInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].showOrder").value(hasItem(DEFAULT_SHOW_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCollectionInstance() throws Exception {
        // Initialize the database
        collectionInstanceRepository.saveAndFlush(collectionInstance);

        // Get the collectionInstance
        restCollectionInstanceMockMvc.perform(get("/api/collection-instances/{id}", collectionInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(collectionInstance.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.showOrder").value(DEFAULT_SHOW_ORDER))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCollectionInstance() throws Exception {
        // Get the collectionInstance
        restCollectionInstanceMockMvc.perform(get("/api/collection-instances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollectionInstance() throws Exception {
        // Initialize the database
        collectionInstanceRepository.saveAndFlush(collectionInstance);

        int databaseSizeBeforeUpdate = collectionInstanceRepository.findAll().size();

        // Update the collectionInstance
        CollectionInstance updatedCollectionInstance = collectionInstanceRepository.findById(collectionInstance.getId()).get();
        // Disconnect from session so that the updates on updatedCollectionInstance are not directly saved in db
        em.detach(updatedCollectionInstance);
        updatedCollectionInstance.setName(UPDATED_NAME);
        updatedCollectionInstance.setDescription(UPDATED_DESCRIPTION);
        updatedCollectionInstance.setShowOrder(UPDATED_SHOW_ORDER);
        updatedCollectionInstance.setActive(UPDATED_ACTIVE);

        restCollectionInstanceMockMvc.perform(put("/api/collection-instances")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCollectionInstance)))
            .andExpect(status().isOk());

        // Validate the CollectionInstance in the database
        List<CollectionInstance> collectionInstanceList = collectionInstanceRepository.findAll();
        assertThat(collectionInstanceList).hasSize(databaseSizeBeforeUpdate);
        CollectionInstance testCollectionInstance = collectionInstanceList.get(collectionInstanceList.size() - 1);
        assertThat(testCollectionInstance.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCollectionInstance.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCollectionInstance.getShowOrder()).isEqualTo(UPDATED_SHOW_ORDER);
        assertThat(testCollectionInstance.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the CollectionInstance in Elasticsearch
        verify(mockCollectionInstanceSearchRepository, times(1)).save(testCollectionInstance);
    }

    @Test
    @Transactional
    public void updateNonExistingCollectionInstance() throws Exception {
        int databaseSizeBeforeUpdate = collectionInstanceRepository.findAll().size();

        // Create the CollectionInstance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollectionInstanceMockMvc.perform(put("/api/collection-instances")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collectionInstance)))
            .andExpect(status().isBadRequest());

        // Validate the CollectionInstance in the database
        List<CollectionInstance> collectionInstanceList = collectionInstanceRepository.findAll();
        assertThat(collectionInstanceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CollectionInstance in Elasticsearch
        verify(mockCollectionInstanceSearchRepository, times(0)).save(collectionInstance);
    }

    @Test
    @Transactional
    public void deleteCollectionInstance() throws Exception {
        // Initialize the database
        collectionInstanceRepository.saveAndFlush(collectionInstance);

        int databaseSizeBeforeDelete = collectionInstanceRepository.findAll().size();

        // Delete the collectionInstance
        restCollectionInstanceMockMvc.perform(delete("/api/collection-instances/{id}", collectionInstance.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CollectionInstance> collectionInstanceList = collectionInstanceRepository.findAll();
        assertThat(collectionInstanceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CollectionInstance in Elasticsearch
        verify(mockCollectionInstanceSearchRepository, times(1)).deleteById(collectionInstance.getId());
    }

    @Test
    @Transactional
    public void searchCollectionInstance() throws Exception {
        // Initialize the database
        collectionInstanceRepository.saveAndFlush(collectionInstance);
        when(mockCollectionInstanceSearchRepository.search(queryStringQuery("id:" + collectionInstance.getId())))
            .thenReturn(Collections.singletonList(collectionInstance));
        // Search the collectionInstance
        restCollectionInstanceMockMvc.perform(get("/api/_search/collection-instances?query=id:" + collectionInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collectionInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].showOrder").value(hasItem(DEFAULT_SHOW_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
}
