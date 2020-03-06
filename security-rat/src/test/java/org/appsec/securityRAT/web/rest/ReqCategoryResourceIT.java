package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.SecurityRatApp;
import org.appsec.securityrat.domain.ReqCategory;
import org.appsec.securityrat.repository.ReqCategoryRepository;
import org.appsec.securityrat.repository.search.ReqCategorySearchRepository;
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
 * Integration tests for the {@link ReqCategoryResource} REST controller.
 */
@SpringBootTest(classes = SecurityRatApp.class)
public class ReqCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORTCUT = "AAAAAAAAAA";
    private static final String UPDATED_SHORTCUT = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SHOW_ORDER = 1;
    private static final Integer UPDATED_SHOW_ORDER = 2;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ReqCategoryRepository reqCategoryRepository;

    /**
     * This repository is mocked in the org.appsec.securityrat.repository.search test package.
     *
     * @see org.appsec.securityrat.repository.search.ReqCategorySearchRepositoryMockConfiguration
     */
    @Autowired
    private ReqCategorySearchRepository mockReqCategorySearchRepository;

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

    private MockMvc restReqCategoryMockMvc;

    private ReqCategory reqCategory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReqCategoryResource reqCategoryResource = new ReqCategoryResource(reqCategoryRepository, mockReqCategorySearchRepository);
        this.restReqCategoryMockMvc = MockMvcBuilders.standaloneSetup(reqCategoryResource)
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
    public static ReqCategory createEntity(EntityManager em) {
        ReqCategory reqCategory = new ReqCategory();
        reqCategory.setName(DEFAULT_NAME);
        reqCategory.setShortcut(DEFAULT_SHORTCUT);
        reqCategory.setDescription(DEFAULT_DESCRIPTION);
        reqCategory.setShowOrder(DEFAULT_SHOW_ORDER);
        reqCategory.setActive(DEFAULT_ACTIVE);
        return reqCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReqCategory createUpdatedEntity(EntityManager em) {
        ReqCategory reqCategory = new ReqCategory();
        reqCategory.setName(UPDATED_NAME);
        reqCategory.setShortcut(UPDATED_SHORTCUT);
        reqCategory.setDescription(UPDATED_DESCRIPTION);
        reqCategory.setShowOrder(UPDATED_SHOW_ORDER);
        reqCategory.setActive(UPDATED_ACTIVE);
        return reqCategory;
    }

    @BeforeEach
    public void initTest() {
        reqCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createReqCategory() throws Exception {
        int databaseSizeBeforeCreate = reqCategoryRepository.findAll().size();

        // Create the ReqCategory
        restReqCategoryMockMvc.perform(post("/api/req-categories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reqCategory)))
            .andExpect(status().isCreated());

        // Validate the ReqCategory in the database
        List<ReqCategory> reqCategoryList = reqCategoryRepository.findAll();
        assertThat(reqCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        ReqCategory testReqCategory = reqCategoryList.get(reqCategoryList.size() - 1);
        assertThat(testReqCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReqCategory.getShortcut()).isEqualTo(DEFAULT_SHORTCUT);
        assertThat(testReqCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testReqCategory.getShowOrder()).isEqualTo(DEFAULT_SHOW_ORDER);
        assertThat(testReqCategory.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the ReqCategory in Elasticsearch
        verify(mockReqCategorySearchRepository, times(1)).save(testReqCategory);
    }

    @Test
    @Transactional
    public void createReqCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reqCategoryRepository.findAll().size();

        // Create the ReqCategory with an existing ID
        reqCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReqCategoryMockMvc.perform(post("/api/req-categories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reqCategory)))
            .andExpect(status().isBadRequest());

        // Validate the ReqCategory in the database
        List<ReqCategory> reqCategoryList = reqCategoryRepository.findAll();
        assertThat(reqCategoryList).hasSize(databaseSizeBeforeCreate);

        // Validate the ReqCategory in Elasticsearch
        verify(mockReqCategorySearchRepository, times(0)).save(reqCategory);
    }


    @Test
    @Transactional
    public void getAllReqCategories() throws Exception {
        // Initialize the database
        reqCategoryRepository.saveAndFlush(reqCategory);

        // Get all the reqCategoryList
        restReqCategoryMockMvc.perform(get("/api/req-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reqCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortcut").value(hasItem(DEFAULT_SHORTCUT)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].showOrder").value(hasItem(DEFAULT_SHOW_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getReqCategory() throws Exception {
        // Initialize the database
        reqCategoryRepository.saveAndFlush(reqCategory);

        // Get the reqCategory
        restReqCategoryMockMvc.perform(get("/api/req-categories/{id}", reqCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reqCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.shortcut").value(DEFAULT_SHORTCUT))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.showOrder").value(DEFAULT_SHOW_ORDER))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingReqCategory() throws Exception {
        // Get the reqCategory
        restReqCategoryMockMvc.perform(get("/api/req-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReqCategory() throws Exception {
        // Initialize the database
        reqCategoryRepository.saveAndFlush(reqCategory);

        int databaseSizeBeforeUpdate = reqCategoryRepository.findAll().size();

        // Update the reqCategory
        ReqCategory updatedReqCategory = reqCategoryRepository.findById(reqCategory.getId()).get();
        // Disconnect from session so that the updates on updatedReqCategory are not directly saved in db
        em.detach(updatedReqCategory);
        updatedReqCategory.setName(UPDATED_NAME);
        updatedReqCategory.setShortcut(UPDATED_SHORTCUT);
        updatedReqCategory.setDescription(UPDATED_DESCRIPTION);
        updatedReqCategory.setShowOrder(UPDATED_SHOW_ORDER);
        updatedReqCategory.setActive(UPDATED_ACTIVE);

        restReqCategoryMockMvc.perform(put("/api/req-categories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedReqCategory)))
            .andExpect(status().isOk());

        // Validate the ReqCategory in the database
        List<ReqCategory> reqCategoryList = reqCategoryRepository.findAll();
        assertThat(reqCategoryList).hasSize(databaseSizeBeforeUpdate);
        ReqCategory testReqCategory = reqCategoryList.get(reqCategoryList.size() - 1);
        assertThat(testReqCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReqCategory.getShortcut()).isEqualTo(UPDATED_SHORTCUT);
        assertThat(testReqCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testReqCategory.getShowOrder()).isEqualTo(UPDATED_SHOW_ORDER);
        assertThat(testReqCategory.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the ReqCategory in Elasticsearch
        verify(mockReqCategorySearchRepository, times(1)).save(testReqCategory);
    }

    @Test
    @Transactional
    public void updateNonExistingReqCategory() throws Exception {
        int databaseSizeBeforeUpdate = reqCategoryRepository.findAll().size();

        // Create the ReqCategory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReqCategoryMockMvc.perform(put("/api/req-categories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reqCategory)))
            .andExpect(status().isBadRequest());

        // Validate the ReqCategory in the database
        List<ReqCategory> reqCategoryList = reqCategoryRepository.findAll();
        assertThat(reqCategoryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ReqCategory in Elasticsearch
        verify(mockReqCategorySearchRepository, times(0)).save(reqCategory);
    }

    @Test
    @Transactional
    public void deleteReqCategory() throws Exception {
        // Initialize the database
        reqCategoryRepository.saveAndFlush(reqCategory);

        int databaseSizeBeforeDelete = reqCategoryRepository.findAll().size();

        // Delete the reqCategory
        restReqCategoryMockMvc.perform(delete("/api/req-categories/{id}", reqCategory.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReqCategory> reqCategoryList = reqCategoryRepository.findAll();
        assertThat(reqCategoryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ReqCategory in Elasticsearch
        verify(mockReqCategorySearchRepository, times(1)).deleteById(reqCategory.getId());
    }

    @Test
    @Transactional
    public void searchReqCategory() throws Exception {
        // Initialize the database
        reqCategoryRepository.saveAndFlush(reqCategory);
        when(mockReqCategorySearchRepository.search(queryStringQuery("id:" + reqCategory.getId())))
            .thenReturn(Collections.singletonList(reqCategory));
        // Search the reqCategory
        restReqCategoryMockMvc.perform(get("/api/_search/req-categories?query=id:" + reqCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reqCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortcut").value(hasItem(DEFAULT_SHORTCUT)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].showOrder").value(hasItem(DEFAULT_SHOW_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
}
