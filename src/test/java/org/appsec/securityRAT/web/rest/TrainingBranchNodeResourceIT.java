package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.SecurityRatApp;
import org.appsec.securityrat.domain.TrainingBranchNode;
import org.appsec.securityrat.repository.TrainingBranchNodeRepository;
import org.appsec.securityrat.repository.search.TrainingBranchNodeSearchRepository;
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
 * Integration tests for the {@link TrainingBranchNodeResource} REST controller.
 */
@SpringBootTest(classes = SecurityRatApp.class)
public class TrainingBranchNodeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANCHOR = 1;
    private static final Integer UPDATED_ANCHOR = 2;

    @Autowired
    private TrainingBranchNodeRepository trainingBranchNodeRepository;

    /**
     * This repository is mocked in the org.appsec.securityrat.repository.search test package.
     *
     * @see org.appsec.securityrat.repository.search.TrainingBranchNodeSearchRepositoryMockConfiguration
     */
    @Autowired
    private TrainingBranchNodeSearchRepository mockTrainingBranchNodeSearchRepository;

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

    private MockMvc restTrainingBranchNodeMockMvc;

    private TrainingBranchNode trainingBranchNode;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrainingBranchNodeResource trainingBranchNodeResource = new TrainingBranchNodeResource(trainingBranchNodeRepository, mockTrainingBranchNodeSearchRepository);
        this.restTrainingBranchNodeMockMvc = MockMvcBuilders.standaloneSetup(trainingBranchNodeResource)
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
    public static TrainingBranchNode createEntity(EntityManager em) {
        TrainingBranchNode trainingBranchNode = new TrainingBranchNode();
        trainingBranchNode.setName(DEFAULT_NAME);
        trainingBranchNode.setAnchor(DEFAULT_ANCHOR);
        return trainingBranchNode;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainingBranchNode createUpdatedEntity(EntityManager em) {
        TrainingBranchNode trainingBranchNode = new TrainingBranchNode();
        trainingBranchNode.setName(UPDATED_NAME);
        trainingBranchNode.setAnchor(UPDATED_ANCHOR);
        return trainingBranchNode;
    }

    @BeforeEach
    public void initTest() {
        trainingBranchNode = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrainingBranchNode() throws Exception {
        int databaseSizeBeforeCreate = trainingBranchNodeRepository.findAll().size();

        // Create the TrainingBranchNode
        restTrainingBranchNodeMockMvc.perform(post("/api/training-branch-nodes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingBranchNode)))
            .andExpect(status().isCreated());

        // Validate the TrainingBranchNode in the database
        List<TrainingBranchNode> trainingBranchNodeList = trainingBranchNodeRepository.findAll();
        assertThat(trainingBranchNodeList).hasSize(databaseSizeBeforeCreate + 1);
        TrainingBranchNode testTrainingBranchNode = trainingBranchNodeList.get(trainingBranchNodeList.size() - 1);
        assertThat(testTrainingBranchNode.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTrainingBranchNode.getAnchor()).isEqualTo(DEFAULT_ANCHOR);

        // Validate the TrainingBranchNode in Elasticsearch
        verify(mockTrainingBranchNodeSearchRepository, times(1)).save(testTrainingBranchNode);
    }

    @Test
    @Transactional
    public void createTrainingBranchNodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trainingBranchNodeRepository.findAll().size();

        // Create the TrainingBranchNode with an existing ID
        trainingBranchNode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingBranchNodeMockMvc.perform(post("/api/training-branch-nodes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingBranchNode)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingBranchNode in the database
        List<TrainingBranchNode> trainingBranchNodeList = trainingBranchNodeRepository.findAll();
        assertThat(trainingBranchNodeList).hasSize(databaseSizeBeforeCreate);

        // Validate the TrainingBranchNode in Elasticsearch
        verify(mockTrainingBranchNodeSearchRepository, times(0)).save(trainingBranchNode);
    }


    @Test
    @Transactional
    public void getAllTrainingBranchNodes() throws Exception {
        // Initialize the database
        trainingBranchNodeRepository.saveAndFlush(trainingBranchNode);

        // Get all the trainingBranchNodeList
        restTrainingBranchNodeMockMvc.perform(get("/api/training-branch-nodes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingBranchNode.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].anchor").value(hasItem(DEFAULT_ANCHOR)));
    }
    
    @Test
    @Transactional
    public void getTrainingBranchNode() throws Exception {
        // Initialize the database
        trainingBranchNodeRepository.saveAndFlush(trainingBranchNode);

        // Get the trainingBranchNode
        restTrainingBranchNodeMockMvc.perform(get("/api/training-branch-nodes/{id}", trainingBranchNode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trainingBranchNode.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.anchor").value(DEFAULT_ANCHOR));
    }

    @Test
    @Transactional
    public void getNonExistingTrainingBranchNode() throws Exception {
        // Get the trainingBranchNode
        restTrainingBranchNodeMockMvc.perform(get("/api/training-branch-nodes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrainingBranchNode() throws Exception {
        // Initialize the database
        trainingBranchNodeRepository.saveAndFlush(trainingBranchNode);

        int databaseSizeBeforeUpdate = trainingBranchNodeRepository.findAll().size();

        // Update the trainingBranchNode
        TrainingBranchNode updatedTrainingBranchNode = trainingBranchNodeRepository.findById(trainingBranchNode.getId()).get();
        // Disconnect from session so that the updates on updatedTrainingBranchNode are not directly saved in db
        em.detach(updatedTrainingBranchNode);
        updatedTrainingBranchNode.setName(UPDATED_NAME);
        updatedTrainingBranchNode.setAnchor(UPDATED_ANCHOR);

        restTrainingBranchNodeMockMvc.perform(put("/api/training-branch-nodes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrainingBranchNode)))
            .andExpect(status().isOk());

        // Validate the TrainingBranchNode in the database
        List<TrainingBranchNode> trainingBranchNodeList = trainingBranchNodeRepository.findAll();
        assertThat(trainingBranchNodeList).hasSize(databaseSizeBeforeUpdate);
        TrainingBranchNode testTrainingBranchNode = trainingBranchNodeList.get(trainingBranchNodeList.size() - 1);
        assertThat(testTrainingBranchNode.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTrainingBranchNode.getAnchor()).isEqualTo(UPDATED_ANCHOR);

        // Validate the TrainingBranchNode in Elasticsearch
        verify(mockTrainingBranchNodeSearchRepository, times(1)).save(testTrainingBranchNode);
    }

    @Test
    @Transactional
    public void updateNonExistingTrainingBranchNode() throws Exception {
        int databaseSizeBeforeUpdate = trainingBranchNodeRepository.findAll().size();

        // Create the TrainingBranchNode

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingBranchNodeMockMvc.perform(put("/api/training-branch-nodes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingBranchNode)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingBranchNode in the database
        List<TrainingBranchNode> trainingBranchNodeList = trainingBranchNodeRepository.findAll();
        assertThat(trainingBranchNodeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TrainingBranchNode in Elasticsearch
        verify(mockTrainingBranchNodeSearchRepository, times(0)).save(trainingBranchNode);
    }

    @Test
    @Transactional
    public void deleteTrainingBranchNode() throws Exception {
        // Initialize the database
        trainingBranchNodeRepository.saveAndFlush(trainingBranchNode);

        int databaseSizeBeforeDelete = trainingBranchNodeRepository.findAll().size();

        // Delete the trainingBranchNode
        restTrainingBranchNodeMockMvc.perform(delete("/api/training-branch-nodes/{id}", trainingBranchNode.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrainingBranchNode> trainingBranchNodeList = trainingBranchNodeRepository.findAll();
        assertThat(trainingBranchNodeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TrainingBranchNode in Elasticsearch
        verify(mockTrainingBranchNodeSearchRepository, times(1)).deleteById(trainingBranchNode.getId());
    }

    @Test
    @Transactional
    public void searchTrainingBranchNode() throws Exception {
        // Initialize the database
        trainingBranchNodeRepository.saveAndFlush(trainingBranchNode);
        when(mockTrainingBranchNodeSearchRepository.search(queryStringQuery("id:" + trainingBranchNode.getId())))
            .thenReturn(Collections.singletonList(trainingBranchNode));
        // Search the trainingBranchNode
        restTrainingBranchNodeMockMvc.perform(get("/api/_search/training-branch-nodes?query=id:" + trainingBranchNode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingBranchNode.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].anchor").value(hasItem(DEFAULT_ANCHOR)));
    }
}
