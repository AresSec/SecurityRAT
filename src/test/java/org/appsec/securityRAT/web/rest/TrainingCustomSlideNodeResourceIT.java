package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.SecurityRatApp;
import org.appsec.securityrat.domain.TrainingCustomSlideNode;
import org.appsec.securityrat.repository.TrainingCustomSlideNodeRepository;
import org.appsec.securityrat.repository.search.TrainingCustomSlideNodeSearchRepository;
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
 * Integration tests for the {@link TrainingCustomSlideNodeResource} REST controller.
 */
@SpringBootTest(classes = SecurityRatApp.class)
public class TrainingCustomSlideNodeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANCHOR = 1;
    private static final Integer UPDATED_ANCHOR = 2;

    @Autowired
    private TrainingCustomSlideNodeRepository trainingCustomSlideNodeRepository;

    /**
     * This repository is mocked in the org.appsec.securityrat.repository.search test package.
     *
     * @see org.appsec.securityrat.repository.search.TrainingCustomSlideNodeSearchRepositoryMockConfiguration
     */
    @Autowired
    private TrainingCustomSlideNodeSearchRepository mockTrainingCustomSlideNodeSearchRepository;

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

    private MockMvc restTrainingCustomSlideNodeMockMvc;

    private TrainingCustomSlideNode trainingCustomSlideNode;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrainingCustomSlideNodeResource trainingCustomSlideNodeResource = new TrainingCustomSlideNodeResource(trainingCustomSlideNodeRepository, mockTrainingCustomSlideNodeSearchRepository);
        this.restTrainingCustomSlideNodeMockMvc = MockMvcBuilders.standaloneSetup(trainingCustomSlideNodeResource)
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
    public static TrainingCustomSlideNode createEntity(EntityManager em) {
        TrainingCustomSlideNode trainingCustomSlideNode = new TrainingCustomSlideNode();
        trainingCustomSlideNode.setName(DEFAULT_NAME);
        trainingCustomSlideNode.setContent(DEFAULT_CONTENT);
        trainingCustomSlideNode.setAnchor(DEFAULT_ANCHOR);
        return trainingCustomSlideNode;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainingCustomSlideNode createUpdatedEntity(EntityManager em) {
        TrainingCustomSlideNode trainingCustomSlideNode = new TrainingCustomSlideNode();
        trainingCustomSlideNode.setName(UPDATED_NAME);
        trainingCustomSlideNode.setContent(UPDATED_CONTENT);
        trainingCustomSlideNode.setAnchor(UPDATED_ANCHOR);
        return trainingCustomSlideNode;
    }

    @BeforeEach
    public void initTest() {
        trainingCustomSlideNode = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrainingCustomSlideNode() throws Exception {
        int databaseSizeBeforeCreate = trainingCustomSlideNodeRepository.findAll().size();

        // Create the TrainingCustomSlideNode
        restTrainingCustomSlideNodeMockMvc.perform(post("/api/training-custom-slide-nodes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingCustomSlideNode)))
            .andExpect(status().isCreated());

        // Validate the TrainingCustomSlideNode in the database
        List<TrainingCustomSlideNode> trainingCustomSlideNodeList = trainingCustomSlideNodeRepository.findAll();
        assertThat(trainingCustomSlideNodeList).hasSize(databaseSizeBeforeCreate + 1);
        TrainingCustomSlideNode testTrainingCustomSlideNode = trainingCustomSlideNodeList.get(trainingCustomSlideNodeList.size() - 1);
        assertThat(testTrainingCustomSlideNode.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTrainingCustomSlideNode.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testTrainingCustomSlideNode.getAnchor()).isEqualTo(DEFAULT_ANCHOR);

        // Validate the TrainingCustomSlideNode in Elasticsearch
        verify(mockTrainingCustomSlideNodeSearchRepository, times(1)).save(testTrainingCustomSlideNode);
    }

    @Test
    @Transactional
    public void createTrainingCustomSlideNodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trainingCustomSlideNodeRepository.findAll().size();

        // Create the TrainingCustomSlideNode with an existing ID
        trainingCustomSlideNode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingCustomSlideNodeMockMvc.perform(post("/api/training-custom-slide-nodes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingCustomSlideNode)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingCustomSlideNode in the database
        List<TrainingCustomSlideNode> trainingCustomSlideNodeList = trainingCustomSlideNodeRepository.findAll();
        assertThat(trainingCustomSlideNodeList).hasSize(databaseSizeBeforeCreate);

        // Validate the TrainingCustomSlideNode in Elasticsearch
        verify(mockTrainingCustomSlideNodeSearchRepository, times(0)).save(trainingCustomSlideNode);
    }


    @Test
    @Transactional
    public void getAllTrainingCustomSlideNodes() throws Exception {
        // Initialize the database
        trainingCustomSlideNodeRepository.saveAndFlush(trainingCustomSlideNode);

        // Get all the trainingCustomSlideNodeList
        restTrainingCustomSlideNodeMockMvc.perform(get("/api/training-custom-slide-nodes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingCustomSlideNode.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].anchor").value(hasItem(DEFAULT_ANCHOR)));
    }
    
    @Test
    @Transactional
    public void getTrainingCustomSlideNode() throws Exception {
        // Initialize the database
        trainingCustomSlideNodeRepository.saveAndFlush(trainingCustomSlideNode);

        // Get the trainingCustomSlideNode
        restTrainingCustomSlideNodeMockMvc.perform(get("/api/training-custom-slide-nodes/{id}", trainingCustomSlideNode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trainingCustomSlideNode.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.anchor").value(DEFAULT_ANCHOR));
    }

    @Test
    @Transactional
    public void getNonExistingTrainingCustomSlideNode() throws Exception {
        // Get the trainingCustomSlideNode
        restTrainingCustomSlideNodeMockMvc.perform(get("/api/training-custom-slide-nodes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrainingCustomSlideNode() throws Exception {
        // Initialize the database
        trainingCustomSlideNodeRepository.saveAndFlush(trainingCustomSlideNode);

        int databaseSizeBeforeUpdate = trainingCustomSlideNodeRepository.findAll().size();

        // Update the trainingCustomSlideNode
        TrainingCustomSlideNode updatedTrainingCustomSlideNode = trainingCustomSlideNodeRepository.findById(trainingCustomSlideNode.getId()).get();
        // Disconnect from session so that the updates on updatedTrainingCustomSlideNode are not directly saved in db
        em.detach(updatedTrainingCustomSlideNode);
        updatedTrainingCustomSlideNode.setName(UPDATED_NAME);
        updatedTrainingCustomSlideNode.setContent(UPDATED_CONTENT);
        updatedTrainingCustomSlideNode.setAnchor(UPDATED_ANCHOR);

        restTrainingCustomSlideNodeMockMvc.perform(put("/api/training-custom-slide-nodes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrainingCustomSlideNode)))
            .andExpect(status().isOk());

        // Validate the TrainingCustomSlideNode in the database
        List<TrainingCustomSlideNode> trainingCustomSlideNodeList = trainingCustomSlideNodeRepository.findAll();
        assertThat(trainingCustomSlideNodeList).hasSize(databaseSizeBeforeUpdate);
        TrainingCustomSlideNode testTrainingCustomSlideNode = trainingCustomSlideNodeList.get(trainingCustomSlideNodeList.size() - 1);
        assertThat(testTrainingCustomSlideNode.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTrainingCustomSlideNode.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testTrainingCustomSlideNode.getAnchor()).isEqualTo(UPDATED_ANCHOR);

        // Validate the TrainingCustomSlideNode in Elasticsearch
        verify(mockTrainingCustomSlideNodeSearchRepository, times(1)).save(testTrainingCustomSlideNode);
    }

    @Test
    @Transactional
    public void updateNonExistingTrainingCustomSlideNode() throws Exception {
        int databaseSizeBeforeUpdate = trainingCustomSlideNodeRepository.findAll().size();

        // Create the TrainingCustomSlideNode

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingCustomSlideNodeMockMvc.perform(put("/api/training-custom-slide-nodes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingCustomSlideNode)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingCustomSlideNode in the database
        List<TrainingCustomSlideNode> trainingCustomSlideNodeList = trainingCustomSlideNodeRepository.findAll();
        assertThat(trainingCustomSlideNodeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TrainingCustomSlideNode in Elasticsearch
        verify(mockTrainingCustomSlideNodeSearchRepository, times(0)).save(trainingCustomSlideNode);
    }

    @Test
    @Transactional
    public void deleteTrainingCustomSlideNode() throws Exception {
        // Initialize the database
        trainingCustomSlideNodeRepository.saveAndFlush(trainingCustomSlideNode);

        int databaseSizeBeforeDelete = trainingCustomSlideNodeRepository.findAll().size();

        // Delete the trainingCustomSlideNode
        restTrainingCustomSlideNodeMockMvc.perform(delete("/api/training-custom-slide-nodes/{id}", trainingCustomSlideNode.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrainingCustomSlideNode> trainingCustomSlideNodeList = trainingCustomSlideNodeRepository.findAll();
        assertThat(trainingCustomSlideNodeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TrainingCustomSlideNode in Elasticsearch
        verify(mockTrainingCustomSlideNodeSearchRepository, times(1)).deleteById(trainingCustomSlideNode.getId());
    }

    @Test
    @Transactional
    public void searchTrainingCustomSlideNode() throws Exception {
        // Initialize the database
        trainingCustomSlideNodeRepository.saveAndFlush(trainingCustomSlideNode);
        when(mockTrainingCustomSlideNodeSearchRepository.search(queryStringQuery("id:" + trainingCustomSlideNode.getId())))
            .thenReturn(Collections.singletonList(trainingCustomSlideNode));
        // Search the trainingCustomSlideNode
        restTrainingCustomSlideNodeMockMvc.perform(get("/api/_search/training-custom-slide-nodes?query=id:" + trainingCustomSlideNode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingCustomSlideNode.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].anchor").value(hasItem(DEFAULT_ANCHOR)));
    }
}
