package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.SecurityRatApp;
import org.appsec.securityrat.domain.TrainingTreeNode;
import org.appsec.securityrat.repository.TrainingTreeNodeRepository;
import org.appsec.securityrat.repository.search.TrainingTreeNodeSearchRepository;
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

import org.appsec.securityrat.domain.enumeration.TrainingTreeNodeType;
/**
 * Integration tests for the {@link TrainingTreeNodeResource} REST controller.
 */
@SpringBootTest(classes = SecurityRatApp.class)
public class TrainingTreeNodeResourceIT {

    private static final TrainingTreeNodeType DEFAULT_NODE_TYPE = TrainingTreeNodeType.RequirementNode;
    private static final TrainingTreeNodeType UPDATED_NODE_TYPE = TrainingTreeNodeType.GeneratedSlideNode;

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private TrainingTreeNodeRepository trainingTreeNodeRepository;

    /**
     * This repository is mocked in the org.appsec.securityrat.repository.search test package.
     *
     * @see org.appsec.securityrat.repository.search.TrainingTreeNodeSearchRepositoryMockConfiguration
     */
    @Autowired
    private TrainingTreeNodeSearchRepository mockTrainingTreeNodeSearchRepository;

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

    private MockMvc restTrainingTreeNodeMockMvc;

    private TrainingTreeNode trainingTreeNode;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrainingTreeNodeResource trainingTreeNodeResource = new TrainingTreeNodeResource(trainingTreeNodeRepository, mockTrainingTreeNodeSearchRepository);
        this.restTrainingTreeNodeMockMvc = MockMvcBuilders.standaloneSetup(trainingTreeNodeResource)
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
    public static TrainingTreeNode createEntity(EntityManager em) {
        TrainingTreeNode trainingTreeNode = new TrainingTreeNode();
        trainingTreeNode.setNodeType(DEFAULT_NODE_TYPE);
        trainingTreeNode.setSortOrder(DEFAULT_SORT_ORDER);
        trainingTreeNode.setActive(DEFAULT_ACTIVE);
        return trainingTreeNode;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainingTreeNode createUpdatedEntity(EntityManager em) {
        TrainingTreeNode trainingTreeNode = new TrainingTreeNode();
        trainingTreeNode.setNodeType(UPDATED_NODE_TYPE);
        trainingTreeNode.setSortOrder(UPDATED_SORT_ORDER);
        trainingTreeNode.setActive(UPDATED_ACTIVE);
        return trainingTreeNode;
    }

    @BeforeEach
    public void initTest() {
        trainingTreeNode = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrainingTreeNode() throws Exception {
        int databaseSizeBeforeCreate = trainingTreeNodeRepository.findAll().size();

        // Create the TrainingTreeNode
        restTrainingTreeNodeMockMvc.perform(post("/api/training-tree-nodes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingTreeNode)))
            .andExpect(status().isCreated());

        // Validate the TrainingTreeNode in the database
        List<TrainingTreeNode> trainingTreeNodeList = trainingTreeNodeRepository.findAll();
        assertThat(trainingTreeNodeList).hasSize(databaseSizeBeforeCreate + 1);
        TrainingTreeNode testTrainingTreeNode = trainingTreeNodeList.get(trainingTreeNodeList.size() - 1);
        assertThat(testTrainingTreeNode.getNodeType()).isEqualTo(DEFAULT_NODE_TYPE);
        assertThat(testTrainingTreeNode.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testTrainingTreeNode.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the TrainingTreeNode in Elasticsearch
        verify(mockTrainingTreeNodeSearchRepository, times(1)).save(testTrainingTreeNode);
    }

    @Test
    @Transactional
    public void createTrainingTreeNodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trainingTreeNodeRepository.findAll().size();

        // Create the TrainingTreeNode with an existing ID
        trainingTreeNode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingTreeNodeMockMvc.perform(post("/api/training-tree-nodes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingTreeNode)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingTreeNode in the database
        List<TrainingTreeNode> trainingTreeNodeList = trainingTreeNodeRepository.findAll();
        assertThat(trainingTreeNodeList).hasSize(databaseSizeBeforeCreate);

        // Validate the TrainingTreeNode in Elasticsearch
        verify(mockTrainingTreeNodeSearchRepository, times(0)).save(trainingTreeNode);
    }


    @Test
    @Transactional
    public void getAllTrainingTreeNodes() throws Exception {
        // Initialize the database
        trainingTreeNodeRepository.saveAndFlush(trainingTreeNode);

        // Get all the trainingTreeNodeList
        restTrainingTreeNodeMockMvc.perform(get("/api/training-tree-nodes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingTreeNode.getId().intValue())))
            .andExpect(jsonPath("$.[*].nodeType").value(hasItem(DEFAULT_NODE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTrainingTreeNode() throws Exception {
        // Initialize the database
        trainingTreeNodeRepository.saveAndFlush(trainingTreeNode);

        // Get the trainingTreeNode
        restTrainingTreeNodeMockMvc.perform(get("/api/training-tree-nodes/{id}", trainingTreeNode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trainingTreeNode.getId().intValue()))
            .andExpect(jsonPath("$.nodeType").value(DEFAULT_NODE_TYPE.toString()))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTrainingTreeNode() throws Exception {
        // Get the trainingTreeNode
        restTrainingTreeNodeMockMvc.perform(get("/api/training-tree-nodes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrainingTreeNode() throws Exception {
        // Initialize the database
        trainingTreeNodeRepository.saveAndFlush(trainingTreeNode);

        int databaseSizeBeforeUpdate = trainingTreeNodeRepository.findAll().size();

        // Update the trainingTreeNode
        TrainingTreeNode updatedTrainingTreeNode = trainingTreeNodeRepository.findById(trainingTreeNode.getId()).get();
        // Disconnect from session so that the updates on updatedTrainingTreeNode are not directly saved in db
        em.detach(updatedTrainingTreeNode);
        updatedTrainingTreeNode.setNodeType(UPDATED_NODE_TYPE);
        updatedTrainingTreeNode.setSortOrder(UPDATED_SORT_ORDER);
        updatedTrainingTreeNode.setActive(UPDATED_ACTIVE);

        restTrainingTreeNodeMockMvc.perform(put("/api/training-tree-nodes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrainingTreeNode)))
            .andExpect(status().isOk());

        // Validate the TrainingTreeNode in the database
        List<TrainingTreeNode> trainingTreeNodeList = trainingTreeNodeRepository.findAll();
        assertThat(trainingTreeNodeList).hasSize(databaseSizeBeforeUpdate);
        TrainingTreeNode testTrainingTreeNode = trainingTreeNodeList.get(trainingTreeNodeList.size() - 1);
        assertThat(testTrainingTreeNode.getNodeType()).isEqualTo(UPDATED_NODE_TYPE);
        assertThat(testTrainingTreeNode.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testTrainingTreeNode.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the TrainingTreeNode in Elasticsearch
        verify(mockTrainingTreeNodeSearchRepository, times(1)).save(testTrainingTreeNode);
    }

    @Test
    @Transactional
    public void updateNonExistingTrainingTreeNode() throws Exception {
        int databaseSizeBeforeUpdate = trainingTreeNodeRepository.findAll().size();

        // Create the TrainingTreeNode

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingTreeNodeMockMvc.perform(put("/api/training-tree-nodes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingTreeNode)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingTreeNode in the database
        List<TrainingTreeNode> trainingTreeNodeList = trainingTreeNodeRepository.findAll();
        assertThat(trainingTreeNodeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TrainingTreeNode in Elasticsearch
        verify(mockTrainingTreeNodeSearchRepository, times(0)).save(trainingTreeNode);
    }

    @Test
    @Transactional
    public void deleteTrainingTreeNode() throws Exception {
        // Initialize the database
        trainingTreeNodeRepository.saveAndFlush(trainingTreeNode);

        int databaseSizeBeforeDelete = trainingTreeNodeRepository.findAll().size();

        // Delete the trainingTreeNode
        restTrainingTreeNodeMockMvc.perform(delete("/api/training-tree-nodes/{id}", trainingTreeNode.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrainingTreeNode> trainingTreeNodeList = trainingTreeNodeRepository.findAll();
        assertThat(trainingTreeNodeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TrainingTreeNode in Elasticsearch
        verify(mockTrainingTreeNodeSearchRepository, times(1)).deleteById(trainingTreeNode.getId());
    }

    @Test
    @Transactional
    public void searchTrainingTreeNode() throws Exception {
        // Initialize the database
        trainingTreeNodeRepository.saveAndFlush(trainingTreeNode);
        when(mockTrainingTreeNodeSearchRepository.search(queryStringQuery("id:" + trainingTreeNode.getId())))
            .thenReturn(Collections.singletonList(trainingTreeNode));
        // Search the trainingTreeNode
        restTrainingTreeNodeMockMvc.perform(get("/api/_search/training-tree-nodes?query=id:" + trainingTreeNode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingTreeNode.getId().intValue())))
            .andExpect(jsonPath("$.[*].nodeType").value(hasItem(DEFAULT_NODE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
}
