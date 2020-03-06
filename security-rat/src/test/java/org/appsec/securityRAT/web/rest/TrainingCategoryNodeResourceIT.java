package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.SecurityRatApp;
import org.appsec.securityrat.domain.TrainingCategoryNode;
import org.appsec.securityrat.repository.TrainingCategoryNodeRepository;
import org.appsec.securityrat.repository.search.TrainingCategoryNodeSearchRepository;
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
 * Integration tests for the {@link TrainingCategoryNodeResource} REST controller.
 */
@SpringBootTest(classes = SecurityRatApp.class)
public class TrainingCategoryNodeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TrainingCategoryNodeRepository trainingCategoryNodeRepository;

    /**
     * This repository is mocked in the org.appsec.securityrat.repository.search test package.
     *
     * @see org.appsec.securityrat.repository.search.TrainingCategoryNodeSearchRepositoryMockConfiguration
     */
    @Autowired
    private TrainingCategoryNodeSearchRepository mockTrainingCategoryNodeSearchRepository;

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

    private MockMvc restTrainingCategoryNodeMockMvc;

    private TrainingCategoryNode trainingCategoryNode;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrainingCategoryNodeResource trainingCategoryNodeResource = new TrainingCategoryNodeResource(trainingCategoryNodeRepository, mockTrainingCategoryNodeSearchRepository);
        this.restTrainingCategoryNodeMockMvc = MockMvcBuilders.standaloneSetup(trainingCategoryNodeResource)
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
    public static TrainingCategoryNode createEntity(EntityManager em) {
        TrainingCategoryNode trainingCategoryNode = new TrainingCategoryNode();
        trainingCategoryNode.setName(DEFAULT_NAME);
        return trainingCategoryNode;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainingCategoryNode createUpdatedEntity(EntityManager em) {
        TrainingCategoryNode trainingCategoryNode = new TrainingCategoryNode();
        trainingCategoryNode.setName(UPDATED_NAME);
        return trainingCategoryNode;
    }

    @BeforeEach
    public void initTest() {
        trainingCategoryNode = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrainingCategoryNode() throws Exception {
        int databaseSizeBeforeCreate = trainingCategoryNodeRepository.findAll().size();

        // Create the TrainingCategoryNode
        restTrainingCategoryNodeMockMvc.perform(post("/api/training-category-nodes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingCategoryNode)))
            .andExpect(status().isCreated());

        // Validate the TrainingCategoryNode in the database
        List<TrainingCategoryNode> trainingCategoryNodeList = trainingCategoryNodeRepository.findAll();
        assertThat(trainingCategoryNodeList).hasSize(databaseSizeBeforeCreate + 1);
        TrainingCategoryNode testTrainingCategoryNode = trainingCategoryNodeList.get(trainingCategoryNodeList.size() - 1);
        assertThat(testTrainingCategoryNode.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the TrainingCategoryNode in Elasticsearch
        verify(mockTrainingCategoryNodeSearchRepository, times(1)).save(testTrainingCategoryNode);
    }

    @Test
    @Transactional
    public void createTrainingCategoryNodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trainingCategoryNodeRepository.findAll().size();

        // Create the TrainingCategoryNode with an existing ID
        trainingCategoryNode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingCategoryNodeMockMvc.perform(post("/api/training-category-nodes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingCategoryNode)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingCategoryNode in the database
        List<TrainingCategoryNode> trainingCategoryNodeList = trainingCategoryNodeRepository.findAll();
        assertThat(trainingCategoryNodeList).hasSize(databaseSizeBeforeCreate);

        // Validate the TrainingCategoryNode in Elasticsearch
        verify(mockTrainingCategoryNodeSearchRepository, times(0)).save(trainingCategoryNode);
    }


    @Test
    @Transactional
    public void getAllTrainingCategoryNodes() throws Exception {
        // Initialize the database
        trainingCategoryNodeRepository.saveAndFlush(trainingCategoryNode);

        // Get all the trainingCategoryNodeList
        restTrainingCategoryNodeMockMvc.perform(get("/api/training-category-nodes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingCategoryNode.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getTrainingCategoryNode() throws Exception {
        // Initialize the database
        trainingCategoryNodeRepository.saveAndFlush(trainingCategoryNode);

        // Get the trainingCategoryNode
        restTrainingCategoryNodeMockMvc.perform(get("/api/training-category-nodes/{id}", trainingCategoryNode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trainingCategoryNode.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingTrainingCategoryNode() throws Exception {
        // Get the trainingCategoryNode
        restTrainingCategoryNodeMockMvc.perform(get("/api/training-category-nodes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrainingCategoryNode() throws Exception {
        // Initialize the database
        trainingCategoryNodeRepository.saveAndFlush(trainingCategoryNode);

        int databaseSizeBeforeUpdate = trainingCategoryNodeRepository.findAll().size();

        // Update the trainingCategoryNode
        TrainingCategoryNode updatedTrainingCategoryNode = trainingCategoryNodeRepository.findById(trainingCategoryNode.getId()).get();
        // Disconnect from session so that the updates on updatedTrainingCategoryNode are not directly saved in db
        em.detach(updatedTrainingCategoryNode);
        updatedTrainingCategoryNode.setName(UPDATED_NAME);

        restTrainingCategoryNodeMockMvc.perform(put("/api/training-category-nodes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrainingCategoryNode)))
            .andExpect(status().isOk());

        // Validate the TrainingCategoryNode in the database
        List<TrainingCategoryNode> trainingCategoryNodeList = trainingCategoryNodeRepository.findAll();
        assertThat(trainingCategoryNodeList).hasSize(databaseSizeBeforeUpdate);
        TrainingCategoryNode testTrainingCategoryNode = trainingCategoryNodeList.get(trainingCategoryNodeList.size() - 1);
        assertThat(testTrainingCategoryNode.getName()).isEqualTo(UPDATED_NAME);

        // Validate the TrainingCategoryNode in Elasticsearch
        verify(mockTrainingCategoryNodeSearchRepository, times(1)).save(testTrainingCategoryNode);
    }

    @Test
    @Transactional
    public void updateNonExistingTrainingCategoryNode() throws Exception {
        int databaseSizeBeforeUpdate = trainingCategoryNodeRepository.findAll().size();

        // Create the TrainingCategoryNode

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingCategoryNodeMockMvc.perform(put("/api/training-category-nodes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingCategoryNode)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingCategoryNode in the database
        List<TrainingCategoryNode> trainingCategoryNodeList = trainingCategoryNodeRepository.findAll();
        assertThat(trainingCategoryNodeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TrainingCategoryNode in Elasticsearch
        verify(mockTrainingCategoryNodeSearchRepository, times(0)).save(trainingCategoryNode);
    }

    @Test
    @Transactional
    public void deleteTrainingCategoryNode() throws Exception {
        // Initialize the database
        trainingCategoryNodeRepository.saveAndFlush(trainingCategoryNode);

        int databaseSizeBeforeDelete = trainingCategoryNodeRepository.findAll().size();

        // Delete the trainingCategoryNode
        restTrainingCategoryNodeMockMvc.perform(delete("/api/training-category-nodes/{id}", trainingCategoryNode.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrainingCategoryNode> trainingCategoryNodeList = trainingCategoryNodeRepository.findAll();
        assertThat(trainingCategoryNodeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TrainingCategoryNode in Elasticsearch
        verify(mockTrainingCategoryNodeSearchRepository, times(1)).deleteById(trainingCategoryNode.getId());
    }

    @Test
    @Transactional
    public void searchTrainingCategoryNode() throws Exception {
        // Initialize the database
        trainingCategoryNodeRepository.saveAndFlush(trainingCategoryNode);
        when(mockTrainingCategoryNodeSearchRepository.search(queryStringQuery("id:" + trainingCategoryNode.getId())))
            .thenReturn(Collections.singletonList(trainingCategoryNode));
        // Search the trainingCategoryNode
        restTrainingCategoryNodeMockMvc.perform(get("/api/_search/training-category-nodes?query=id:" + trainingCategoryNode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingCategoryNode.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
}
