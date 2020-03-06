package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.SecurityRatApp;
import org.appsec.securityrat.domain.TrainingRequirementNode;
import org.appsec.securityrat.repository.TrainingRequirementNodeRepository;
import org.appsec.securityrat.repository.search.TrainingRequirementNodeSearchRepository;
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
 * Integration tests for the {@link TrainingRequirementNodeResource} REST controller.
 */
@SpringBootTest(classes = SecurityRatApp.class)
public class TrainingRequirementNodeResourceIT {

    @Autowired
    private TrainingRequirementNodeRepository trainingRequirementNodeRepository;

    /**
     * This repository is mocked in the org.appsec.securityrat.repository.search test package.
     *
     * @see org.appsec.securityrat.repository.search.TrainingRequirementNodeSearchRepositoryMockConfiguration
     */
    @Autowired
    private TrainingRequirementNodeSearchRepository mockTrainingRequirementNodeSearchRepository;

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

    private MockMvc restTrainingRequirementNodeMockMvc;

    private TrainingRequirementNode trainingRequirementNode;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrainingRequirementNodeResource trainingRequirementNodeResource = new TrainingRequirementNodeResource(trainingRequirementNodeRepository, mockTrainingRequirementNodeSearchRepository);
        this.restTrainingRequirementNodeMockMvc = MockMvcBuilders.standaloneSetup(trainingRequirementNodeResource)
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
    public static TrainingRequirementNode createEntity(EntityManager em) {
        TrainingRequirementNode trainingRequirementNode = new TrainingRequirementNode();
        return trainingRequirementNode;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainingRequirementNode createUpdatedEntity(EntityManager em) {
        TrainingRequirementNode trainingRequirementNode = new TrainingRequirementNode();
        return trainingRequirementNode;
    }

    @BeforeEach
    public void initTest() {
        trainingRequirementNode = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrainingRequirementNode() throws Exception {
        int databaseSizeBeforeCreate = trainingRequirementNodeRepository.findAll().size();

        // Create the TrainingRequirementNode
        restTrainingRequirementNodeMockMvc.perform(post("/api/training-requirement-nodes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingRequirementNode)))
            .andExpect(status().isCreated());

        // Validate the TrainingRequirementNode in the database
        List<TrainingRequirementNode> trainingRequirementNodeList = trainingRequirementNodeRepository.findAll();
        assertThat(trainingRequirementNodeList).hasSize(databaseSizeBeforeCreate + 1);
        TrainingRequirementNode testTrainingRequirementNode = trainingRequirementNodeList.get(trainingRequirementNodeList.size() - 1);

        // Validate the TrainingRequirementNode in Elasticsearch
        verify(mockTrainingRequirementNodeSearchRepository, times(1)).save(testTrainingRequirementNode);
    }

    @Test
    @Transactional
    public void createTrainingRequirementNodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trainingRequirementNodeRepository.findAll().size();

        // Create the TrainingRequirementNode with an existing ID
        trainingRequirementNode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingRequirementNodeMockMvc.perform(post("/api/training-requirement-nodes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingRequirementNode)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingRequirementNode in the database
        List<TrainingRequirementNode> trainingRequirementNodeList = trainingRequirementNodeRepository.findAll();
        assertThat(trainingRequirementNodeList).hasSize(databaseSizeBeforeCreate);

        // Validate the TrainingRequirementNode in Elasticsearch
        verify(mockTrainingRequirementNodeSearchRepository, times(0)).save(trainingRequirementNode);
    }


    @Test
    @Transactional
    public void getAllTrainingRequirementNodes() throws Exception {
        // Initialize the database
        trainingRequirementNodeRepository.saveAndFlush(trainingRequirementNode);

        // Get all the trainingRequirementNodeList
        restTrainingRequirementNodeMockMvc.perform(get("/api/training-requirement-nodes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingRequirementNode.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getTrainingRequirementNode() throws Exception {
        // Initialize the database
        trainingRequirementNodeRepository.saveAndFlush(trainingRequirementNode);

        // Get the trainingRequirementNode
        restTrainingRequirementNodeMockMvc.perform(get("/api/training-requirement-nodes/{id}", trainingRequirementNode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trainingRequirementNode.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTrainingRequirementNode() throws Exception {
        // Get the trainingRequirementNode
        restTrainingRequirementNodeMockMvc.perform(get("/api/training-requirement-nodes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrainingRequirementNode() throws Exception {
        // Initialize the database
        trainingRequirementNodeRepository.saveAndFlush(trainingRequirementNode);

        int databaseSizeBeforeUpdate = trainingRequirementNodeRepository.findAll().size();

        // Update the trainingRequirementNode
        TrainingRequirementNode updatedTrainingRequirementNode = trainingRequirementNodeRepository.findById(trainingRequirementNode.getId()).get();
        // Disconnect from session so that the updates on updatedTrainingRequirementNode are not directly saved in db
        em.detach(updatedTrainingRequirementNode);

        restTrainingRequirementNodeMockMvc.perform(put("/api/training-requirement-nodes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrainingRequirementNode)))
            .andExpect(status().isOk());

        // Validate the TrainingRequirementNode in the database
        List<TrainingRequirementNode> trainingRequirementNodeList = trainingRequirementNodeRepository.findAll();
        assertThat(trainingRequirementNodeList).hasSize(databaseSizeBeforeUpdate);
        TrainingRequirementNode testTrainingRequirementNode = trainingRequirementNodeList.get(trainingRequirementNodeList.size() - 1);

        // Validate the TrainingRequirementNode in Elasticsearch
        verify(mockTrainingRequirementNodeSearchRepository, times(1)).save(testTrainingRequirementNode);
    }

    @Test
    @Transactional
    public void updateNonExistingTrainingRequirementNode() throws Exception {
        int databaseSizeBeforeUpdate = trainingRequirementNodeRepository.findAll().size();

        // Create the TrainingRequirementNode

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingRequirementNodeMockMvc.perform(put("/api/training-requirement-nodes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingRequirementNode)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingRequirementNode in the database
        List<TrainingRequirementNode> trainingRequirementNodeList = trainingRequirementNodeRepository.findAll();
        assertThat(trainingRequirementNodeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TrainingRequirementNode in Elasticsearch
        verify(mockTrainingRequirementNodeSearchRepository, times(0)).save(trainingRequirementNode);
    }

    @Test
    @Transactional
    public void deleteTrainingRequirementNode() throws Exception {
        // Initialize the database
        trainingRequirementNodeRepository.saveAndFlush(trainingRequirementNode);

        int databaseSizeBeforeDelete = trainingRequirementNodeRepository.findAll().size();

        // Delete the trainingRequirementNode
        restTrainingRequirementNodeMockMvc.perform(delete("/api/training-requirement-nodes/{id}", trainingRequirementNode.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrainingRequirementNode> trainingRequirementNodeList = trainingRequirementNodeRepository.findAll();
        assertThat(trainingRequirementNodeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TrainingRequirementNode in Elasticsearch
        verify(mockTrainingRequirementNodeSearchRepository, times(1)).deleteById(trainingRequirementNode.getId());
    }

    @Test
    @Transactional
    public void searchTrainingRequirementNode() throws Exception {
        // Initialize the database
        trainingRequirementNodeRepository.saveAndFlush(trainingRequirementNode);
        when(mockTrainingRequirementNodeSearchRepository.search(queryStringQuery("id:" + trainingRequirementNode.getId())))
            .thenReturn(Collections.singletonList(trainingRequirementNode));
        // Search the trainingRequirementNode
        restTrainingRequirementNodeMockMvc.perform(get("/api/_search/training-requirement-nodes?query=id:" + trainingRequirementNode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingRequirementNode.getId().intValue())));
    }
}
