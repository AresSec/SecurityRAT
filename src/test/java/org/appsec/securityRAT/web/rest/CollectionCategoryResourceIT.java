package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.SecurityRatApp;
import org.appsec.securityrat.domain.CollectionCategory;
import org.appsec.securityrat.repository.CollectionCategoryRepository;
import org.appsec.securityrat.repository.search.CollectionCategorySearchRepository;
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
 * Integration tests for the {@link CollectionCategoryResource} REST controller.
 */
@SpringBootTest(classes = SecurityRatApp.class)
public class CollectionCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SHOW_ORDER = 1;
    private static final Integer UPDATED_SHOW_ORDER = 2;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CollectionCategoryRepository collectionCategoryRepository;

    /**
     * This repository is mocked in the org.appsec.securityrat.repository.search test package.
     *
     * @see org.appsec.securityrat.repository.search.CollectionCategorySearchRepositoryMockConfiguration
     */
    @Autowired
    private CollectionCategorySearchRepository mockCollectionCategorySearchRepository;

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

    private MockMvc restCollectionCategoryMockMvc;

    private CollectionCategory collectionCategory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CollectionCategoryResource collectionCategoryResource = new CollectionCategoryResource(collectionCategoryRepository, mockCollectionCategorySearchRepository);
        this.restCollectionCategoryMockMvc = MockMvcBuilders.standaloneSetup(collectionCategoryResource)
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
    public static CollectionCategory createEntity(EntityManager em) {
        CollectionCategory collectionCategory = new CollectionCategory();
        collectionCategory.setName(DEFAULT_NAME);
        collectionCategory.setDescription(DEFAULT_DESCRIPTION);
        collectionCategory.setShowOrder(DEFAULT_SHOW_ORDER);
        collectionCategory.setActive(DEFAULT_ACTIVE);
        return collectionCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CollectionCategory createUpdatedEntity(EntityManager em) {
        CollectionCategory collectionCategory = new CollectionCategory();
        collectionCategory.setName(UPDATED_NAME);
        collectionCategory.setDescription(UPDATED_DESCRIPTION);
        collectionCategory.setShowOrder(UPDATED_SHOW_ORDER);
        collectionCategory.setActive(UPDATED_ACTIVE);
        return collectionCategory;
    }

    @BeforeEach
    public void initTest() {
        collectionCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createCollectionCategory() throws Exception {
        int databaseSizeBeforeCreate = collectionCategoryRepository.findAll().size();

        // Create the CollectionCategory
        restCollectionCategoryMockMvc.perform(post("/api/collection-categories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collectionCategory)))
            .andExpect(status().isCreated());

        // Validate the CollectionCategory in the database
        List<CollectionCategory> collectionCategoryList = collectionCategoryRepository.findAll();
        assertThat(collectionCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        CollectionCategory testCollectionCategory = collectionCategoryList.get(collectionCategoryList.size() - 1);
        assertThat(testCollectionCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCollectionCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCollectionCategory.getShowOrder()).isEqualTo(DEFAULT_SHOW_ORDER);
        assertThat(testCollectionCategory.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the CollectionCategory in Elasticsearch
        verify(mockCollectionCategorySearchRepository, times(1)).save(testCollectionCategory);
    }

    @Test
    @Transactional
    public void createCollectionCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collectionCategoryRepository.findAll().size();

        // Create the CollectionCategory with an existing ID
        collectionCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollectionCategoryMockMvc.perform(post("/api/collection-categories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collectionCategory)))
            .andExpect(status().isBadRequest());

        // Validate the CollectionCategory in the database
        List<CollectionCategory> collectionCategoryList = collectionCategoryRepository.findAll();
        assertThat(collectionCategoryList).hasSize(databaseSizeBeforeCreate);

        // Validate the CollectionCategory in Elasticsearch
        verify(mockCollectionCategorySearchRepository, times(0)).save(collectionCategory);
    }


    @Test
    @Transactional
    public void getAllCollectionCategories() throws Exception {
        // Initialize the database
        collectionCategoryRepository.saveAndFlush(collectionCategory);

        // Get all the collectionCategoryList
        restCollectionCategoryMockMvc.perform(get("/api/collection-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collectionCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].showOrder").value(hasItem(DEFAULT_SHOW_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCollectionCategory() throws Exception {
        // Initialize the database
        collectionCategoryRepository.saveAndFlush(collectionCategory);

        // Get the collectionCategory
        restCollectionCategoryMockMvc.perform(get("/api/collection-categories/{id}", collectionCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(collectionCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.showOrder").value(DEFAULT_SHOW_ORDER))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCollectionCategory() throws Exception {
        // Get the collectionCategory
        restCollectionCategoryMockMvc.perform(get("/api/collection-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollectionCategory() throws Exception {
        // Initialize the database
        collectionCategoryRepository.saveAndFlush(collectionCategory);

        int databaseSizeBeforeUpdate = collectionCategoryRepository.findAll().size();

        // Update the collectionCategory
        CollectionCategory updatedCollectionCategory = collectionCategoryRepository.findById(collectionCategory.getId()).get();
        // Disconnect from session so that the updates on updatedCollectionCategory are not directly saved in db
        em.detach(updatedCollectionCategory);
        updatedCollectionCategory.setName(UPDATED_NAME);
        updatedCollectionCategory.setDescription(UPDATED_DESCRIPTION);
        updatedCollectionCategory.setShowOrder(UPDATED_SHOW_ORDER);
        updatedCollectionCategory.setActive(UPDATED_ACTIVE);

        restCollectionCategoryMockMvc.perform(put("/api/collection-categories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCollectionCategory)))
            .andExpect(status().isOk());

        // Validate the CollectionCategory in the database
        List<CollectionCategory> collectionCategoryList = collectionCategoryRepository.findAll();
        assertThat(collectionCategoryList).hasSize(databaseSizeBeforeUpdate);
        CollectionCategory testCollectionCategory = collectionCategoryList.get(collectionCategoryList.size() - 1);
        assertThat(testCollectionCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCollectionCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCollectionCategory.getShowOrder()).isEqualTo(UPDATED_SHOW_ORDER);
        assertThat(testCollectionCategory.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the CollectionCategory in Elasticsearch
        verify(mockCollectionCategorySearchRepository, times(1)).save(testCollectionCategory);
    }

    @Test
    @Transactional
    public void updateNonExistingCollectionCategory() throws Exception {
        int databaseSizeBeforeUpdate = collectionCategoryRepository.findAll().size();

        // Create the CollectionCategory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollectionCategoryMockMvc.perform(put("/api/collection-categories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collectionCategory)))
            .andExpect(status().isBadRequest());

        // Validate the CollectionCategory in the database
        List<CollectionCategory> collectionCategoryList = collectionCategoryRepository.findAll();
        assertThat(collectionCategoryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CollectionCategory in Elasticsearch
        verify(mockCollectionCategorySearchRepository, times(0)).save(collectionCategory);
    }

    @Test
    @Transactional
    public void deleteCollectionCategory() throws Exception {
        // Initialize the database
        collectionCategoryRepository.saveAndFlush(collectionCategory);

        int databaseSizeBeforeDelete = collectionCategoryRepository.findAll().size();

        // Delete the collectionCategory
        restCollectionCategoryMockMvc.perform(delete("/api/collection-categories/{id}", collectionCategory.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CollectionCategory> collectionCategoryList = collectionCategoryRepository.findAll();
        assertThat(collectionCategoryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CollectionCategory in Elasticsearch
        verify(mockCollectionCategorySearchRepository, times(1)).deleteById(collectionCategory.getId());
    }

    @Test
    @Transactional
    public void searchCollectionCategory() throws Exception {
        // Initialize the database
        collectionCategoryRepository.saveAndFlush(collectionCategory);
        when(mockCollectionCategorySearchRepository.search(queryStringQuery("id:" + collectionCategory.getId())))
            .thenReturn(Collections.singletonList(collectionCategory));
        // Search the collectionCategory
        restCollectionCategoryMockMvc.perform(get("/api/_search/collection-categories?query=id:" + collectionCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collectionCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].showOrder").value(hasItem(DEFAULT_SHOW_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
}
