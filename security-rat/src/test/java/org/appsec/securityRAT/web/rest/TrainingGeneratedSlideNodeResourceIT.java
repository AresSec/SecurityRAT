package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.SecurityRatApp;
import org.appsec.securityrat.domain.TrainingGeneratedSlideNode;
import org.appsec.securityrat.repository.TrainingGeneratedSlideNodeRepository;
import org.appsec.securityrat.repository.search.TrainingGeneratedSlideNodeSearchRepository;
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
 * Integration tests for the {@link TrainingGeneratedSlideNodeResource} REST controller.
 */
@SpringBootTest(classes = SecurityRatApp.class)
public class TrainingGeneratedSlideNodeResourceIT {

    @Autowired
    private TrainingGeneratedSlideNodeRepository trainingGeneratedSlideNodeRepository;

    /**
     * This repository is mocked in the org.appsec.securityrat.repository.search test package.
     *
     * @see org.appsec.securityrat.repository.search.TrainingGeneratedSlideNodeSearchRepositoryMockConfiguration
     */
    @Autowired
    private TrainingGeneratedSlideNodeSearchRepository mockTrainingGeneratedSlideNodeSearchRepository;

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

    private MockMvc restTrainingGeneratedSlideNodeMockMvc;

    private TrainingGeneratedSlideNode trainingGeneratedSlideNode;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrainingGeneratedSlideNodeResource trainingGeneratedSlideNodeResource = new TrainingGeneratedSlideNodeResource(trainingGeneratedSlideNodeRepository, mockTrainingGeneratedSlideNodeSearchRepository);
        this.restTrainingGeneratedSlideNodeMockMvc = MockMvcBuilders.standaloneSetup(trainingGeneratedSlideNodeResource)
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
    public static TrainingGeneratedSlideNode createEntity(EntityManager em) {
        TrainingGeneratedSlideNode trainingGeneratedSlideNode = new TrainingGeneratedSlideNode();
        return trainingGeneratedSlideNode;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainingGeneratedSlideNode createUpdatedEntity(EntityManager em) {
        TrainingGeneratedSlideNode trainingGeneratedSlideNode = new TrainingGeneratedSlideNode();
        return trainingGeneratedSlideNode;
    }

    @BeforeEach
    public void initTest() {
        trainingGeneratedSlideNode = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrainingGeneratedSlideNode() throws Exception {
        int databaseSizeBeforeCreate = trainingGeneratedSlideNodeRepository.findAll().size();

        // Create the TrainingGeneratedSlideNode
        restTrainingGeneratedSlideNodeMockMvc.perform(post("/api/training-generated-slide-nodes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingGeneratedSlideNode)))
            .andExpect(status().isCreated());

        // Validate the TrainingGeneratedSlideNode in the database
        List<TrainingGeneratedSlideNode> trainingGeneratedSlideNodeList = trainingGeneratedSlideNodeRepository.findAll();
        assertThat(trainingGeneratedSlideNodeList).hasSize(databaseSizeBeforeCreate + 1);
        TrainingGeneratedSlideNode testTrainingGeneratedSlideNode = trainingGeneratedSlideNodeList.get(trainingGeneratedSlideNodeList.size() - 1);

        // Validate the TrainingGeneratedSlideNode in Elasticsearch
        verify(mockTrainingGeneratedSlideNodeSearchRepository, times(1)).save(testTrainingGeneratedSlideNode);
    }

    @Test
    @Transactional
    public void createTrainingGeneratedSlideNodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trainingGeneratedSlideNodeRepository.findAll().size();

        // Create the TrainingGeneratedSlideNode with an existing ID
        trainingGeneratedSlideNode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingGeneratedSlideNodeMockMvc.perform(post("/api/training-generated-slide-nodes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingGeneratedSlideNode)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingGeneratedSlideNode in the database
        List<TrainingGeneratedSlideNode> trainingGeneratedSlideNodeList = trainingGeneratedSlideNodeRepository.findAll();
        assertThat(trainingGeneratedSlideNodeList).hasSize(databaseSizeBeforeCreate);

        // Validate the TrainingGeneratedSlideNode in Elasticsearch
        verify(mockTrainingGeneratedSlideNodeSearchRepository, times(0)).save(trainingGeneratedSlideNode);
    }


    @Test
    @Transactional
    public void getAllTrainingGeneratedSlideNodes() throws Exception {
        // Initialize the database
        trainingGeneratedSlideNodeRepository.saveAndFlush(trainingGeneratedSlideNode);

        // Get all the trainingGeneratedSlideNodeList
        restTrainingGeneratedSlideNodeMockMvc.perform(get("/api/training-generated-slide-nodes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingGeneratedSlideNode.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getTrainingGeneratedSlideNode() throws Exception {
        // Initialize the database
        trainingGeneratedSlideNodeRepository.saveAndFlush(trainingGeneratedSlideNode);

        // Get the trainingGeneratedSlideNode
        restTrainingGeneratedSlideNodeMockMvc.perform(get("/api/training-generated-slide-nodes/{id}", trainingGeneratedSlideNode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trainingGeneratedSlideNode.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTrainingGeneratedSlideNode() throws Exception {
        // Get the trainingGeneratedSlideNode
        restTrainingGeneratedSlideNodeMockMvc.perform(get("/api/training-generated-slide-nodes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrainingGeneratedSlideNode() throws Exception {
        // Initialize the database
        trainingGeneratedSlideNodeRepository.saveAndFlush(trainingGeneratedSlideNode);

        int databaseSizeBeforeUpdate = trainingGeneratedSlideNodeRepository.findAll().size();

        // Update the trainingGeneratedSlideNode
        TrainingGeneratedSlideNode updatedTrainingGeneratedSlideNode = trainingGeneratedSlideNodeRepository.findById(trainingGeneratedSlideNode.getId()).get();
        // Disconnect from session so that the updates on updatedTrainingGeneratedSlideNode are not directly saved in db
        em.detach(updatedTrainingGeneratedSlideNode);

        restTrainingGeneratedSlideNodeMockMvc.perform(put("/api/training-generated-slide-nodes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrainingGeneratedSlideNode)))
            .andExpect(status().isOk());

        // Validate the TrainingGeneratedSlideNode in the database
        List<TrainingGeneratedSlideNode> trainingGeneratedSlideNodeList = trainingGeneratedSlideNodeRepository.findAll();
        assertThat(trainingGeneratedSlideNodeList).hasSize(databaseSizeBeforeUpdate);
        TrainingGeneratedSlideNode testTrainingGeneratedSlideNode = trainingGeneratedSlideNodeList.get(trainingGeneratedSlideNodeList.size() - 1);

        // Validate the TrainingGeneratedSlideNode in Elasticsearch
        verify(mockTrainingGeneratedSlideNodeSearchRepository, times(1)).save(testTrainingGeneratedSlideNode);
    }

    @Test
    @Transactional
    public void updateNonExistingTrainingGeneratedSlideNode() throws Exception {
        int databaseSizeBeforeUpdate = trainingGeneratedSlideNodeRepository.findAll().size();

        // Create the TrainingGeneratedSlideNode

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingGeneratedSlideNodeMockMvc.perform(put("/api/training-generated-slide-nodes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingGeneratedSlideNode)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingGeneratedSlideNode in the database
        List<TrainingGeneratedSlideNode> trainingGeneratedSlideNodeList = trainingGeneratedSlideNodeRepository.findAll();
        assertThat(trainingGeneratedSlideNodeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TrainingGeneratedSlideNode in Elasticsearch
        verify(mockTrainingGeneratedSlideNodeSearchRepository, times(0)).save(trainingGeneratedSlideNode);
    }

    @Test
    @Transactional
    public void deleteTrainingGeneratedSlideNode() throws Exception {
        // Initialize the database
        trainingGeneratedSlideNodeRepository.saveAndFlush(trainingGeneratedSlideNode);

        int databaseSizeBeforeDelete = trainingGeneratedSlideNodeRepository.findAll().size();

        // Delete the trainingGeneratedSlideNode
        restTrainingGeneratedSlideNodeMockMvc.perform(delete("/api/training-generated-slide-nodes/{id}", trainingGeneratedSlideNode.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrainingGeneratedSlideNode> trainingGeneratedSlideNodeList = trainingGeneratedSlideNodeRepository.findAll();
        assertThat(trainingGeneratedSlideNodeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TrainingGeneratedSlideNode in Elasticsearch
        verify(mockTrainingGeneratedSlideNodeSearchRepository, times(1)).deleteById(trainingGeneratedSlideNode.getId());
    }

    @Test
    @Transactional
    public void searchTrainingGeneratedSlideNode() throws Exception {
        // Initialize the database
        trainingGeneratedSlideNodeRepository.saveAndFlush(trainingGeneratedSlideNode);
        when(mockTrainingGeneratedSlideNodeSearchRepository.search(queryStringQuery("id:" + trainingGeneratedSlideNode.getId())))
            .thenReturn(Collections.singletonList(trainingGeneratedSlideNode));
        // Search the trainingGeneratedSlideNode
        restTrainingGeneratedSlideNodeMockMvc.perform(get("/api/_search/training-generated-slide-nodes?query=id:" + trainingGeneratedSlideNode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingGeneratedSlideNode.getId().intValue())));
    }
}
