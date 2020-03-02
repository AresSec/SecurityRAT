package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.SecurityRatApp;
import org.appsec.securityrat.domain.TagInstance;
import org.appsec.securityrat.repository.TagInstanceRepository;
import org.appsec.securityrat.repository.search.TagInstanceSearchRepository;
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
 * Integration tests for the {@link TagInstanceResource} REST controller.
 */
@SpringBootTest(classes = SecurityRatApp.class)
public class TagInstanceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SHOW_ORDER = 1;
    private static final Integer UPDATED_SHOW_ORDER = 2;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private TagInstanceRepository tagInstanceRepository;

    /**
     * This repository is mocked in the org.appsec.securityrat.repository.search test package.
     *
     * @see org.appsec.securityrat.repository.search.TagInstanceSearchRepositoryMockConfiguration
     */
    @Autowired
    private TagInstanceSearchRepository mockTagInstanceSearchRepository;

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

    private MockMvc restTagInstanceMockMvc;

    private TagInstance tagInstance;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TagInstanceResource tagInstanceResource = new TagInstanceResource(tagInstanceRepository, mockTagInstanceSearchRepository);
        this.restTagInstanceMockMvc = MockMvcBuilders.standaloneSetup(tagInstanceResource)
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
    public static TagInstance createEntity(EntityManager em) {
        TagInstance tagInstance = new TagInstance();
        tagInstance.setName(DEFAULT_NAME);
        tagInstance.setDescription(DEFAULT_DESCRIPTION);
        tagInstance.setShowOrder(DEFAULT_SHOW_ORDER);
        tagInstance.setActive(DEFAULT_ACTIVE);
        return tagInstance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TagInstance createUpdatedEntity(EntityManager em) {
        TagInstance tagInstance = new TagInstance();
        tagInstance.setName(UPDATED_NAME);
        tagInstance.setDescription(UPDATED_DESCRIPTION);
        tagInstance.setShowOrder(UPDATED_SHOW_ORDER);
        tagInstance.setActive(UPDATED_ACTIVE);
        return tagInstance;
    }

    @BeforeEach
    public void initTest() {
        tagInstance = createEntity(em);
    }

    @Test
    @Transactional
    public void createTagInstance() throws Exception {
        int databaseSizeBeforeCreate = tagInstanceRepository.findAll().size();

        // Create the TagInstance
        restTagInstanceMockMvc.perform(post("/api/tag-instances")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagInstance)))
            .andExpect(status().isCreated());

        // Validate the TagInstance in the database
        List<TagInstance> tagInstanceList = tagInstanceRepository.findAll();
        assertThat(tagInstanceList).hasSize(databaseSizeBeforeCreate + 1);
        TagInstance testTagInstance = tagInstanceList.get(tagInstanceList.size() - 1);
        assertThat(testTagInstance.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTagInstance.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTagInstance.getShowOrder()).isEqualTo(DEFAULT_SHOW_ORDER);
        assertThat(testTagInstance.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the TagInstance in Elasticsearch
        verify(mockTagInstanceSearchRepository, times(1)).save(testTagInstance);
    }

    @Test
    @Transactional
    public void createTagInstanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tagInstanceRepository.findAll().size();

        // Create the TagInstance with an existing ID
        tagInstance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTagInstanceMockMvc.perform(post("/api/tag-instances")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagInstance)))
            .andExpect(status().isBadRequest());

        // Validate the TagInstance in the database
        List<TagInstance> tagInstanceList = tagInstanceRepository.findAll();
        assertThat(tagInstanceList).hasSize(databaseSizeBeforeCreate);

        // Validate the TagInstance in Elasticsearch
        verify(mockTagInstanceSearchRepository, times(0)).save(tagInstance);
    }


    @Test
    @Transactional
    public void getAllTagInstances() throws Exception {
        // Initialize the database
        tagInstanceRepository.saveAndFlush(tagInstance);

        // Get all the tagInstanceList
        restTagInstanceMockMvc.perform(get("/api/tag-instances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tagInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].showOrder").value(hasItem(DEFAULT_SHOW_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTagInstance() throws Exception {
        // Initialize the database
        tagInstanceRepository.saveAndFlush(tagInstance);

        // Get the tagInstance
        restTagInstanceMockMvc.perform(get("/api/tag-instances/{id}", tagInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tagInstance.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.showOrder").value(DEFAULT_SHOW_ORDER))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTagInstance() throws Exception {
        // Get the tagInstance
        restTagInstanceMockMvc.perform(get("/api/tag-instances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTagInstance() throws Exception {
        // Initialize the database
        tagInstanceRepository.saveAndFlush(tagInstance);

        int databaseSizeBeforeUpdate = tagInstanceRepository.findAll().size();

        // Update the tagInstance
        TagInstance updatedTagInstance = tagInstanceRepository.findById(tagInstance.getId()).get();
        // Disconnect from session so that the updates on updatedTagInstance are not directly saved in db
        em.detach(updatedTagInstance);
        updatedTagInstance.setName(UPDATED_NAME);
        updatedTagInstance.setDescription(UPDATED_DESCRIPTION);
        updatedTagInstance.setShowOrder(UPDATED_SHOW_ORDER);
        updatedTagInstance.setActive(UPDATED_ACTIVE);

        restTagInstanceMockMvc.perform(put("/api/tag-instances")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTagInstance)))
            .andExpect(status().isOk());

        // Validate the TagInstance in the database
        List<TagInstance> tagInstanceList = tagInstanceRepository.findAll();
        assertThat(tagInstanceList).hasSize(databaseSizeBeforeUpdate);
        TagInstance testTagInstance = tagInstanceList.get(tagInstanceList.size() - 1);
        assertThat(testTagInstance.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTagInstance.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTagInstance.getShowOrder()).isEqualTo(UPDATED_SHOW_ORDER);
        assertThat(testTagInstance.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the TagInstance in Elasticsearch
        verify(mockTagInstanceSearchRepository, times(1)).save(testTagInstance);
    }

    @Test
    @Transactional
    public void updateNonExistingTagInstance() throws Exception {
        int databaseSizeBeforeUpdate = tagInstanceRepository.findAll().size();

        // Create the TagInstance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTagInstanceMockMvc.perform(put("/api/tag-instances")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagInstance)))
            .andExpect(status().isBadRequest());

        // Validate the TagInstance in the database
        List<TagInstance> tagInstanceList = tagInstanceRepository.findAll();
        assertThat(tagInstanceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TagInstance in Elasticsearch
        verify(mockTagInstanceSearchRepository, times(0)).save(tagInstance);
    }

    @Test
    @Transactional
    public void deleteTagInstance() throws Exception {
        // Initialize the database
        tagInstanceRepository.saveAndFlush(tagInstance);

        int databaseSizeBeforeDelete = tagInstanceRepository.findAll().size();

        // Delete the tagInstance
        restTagInstanceMockMvc.perform(delete("/api/tag-instances/{id}", tagInstance.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TagInstance> tagInstanceList = tagInstanceRepository.findAll();
        assertThat(tagInstanceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TagInstance in Elasticsearch
        verify(mockTagInstanceSearchRepository, times(1)).deleteById(tagInstance.getId());
    }

    @Test
    @Transactional
    public void searchTagInstance() throws Exception {
        // Initialize the database
        tagInstanceRepository.saveAndFlush(tagInstance);
        when(mockTagInstanceSearchRepository.search(queryStringQuery("id:" + tagInstance.getId())))
            .thenReturn(Collections.singletonList(tagInstance));
        // Search the tagInstance
        restTagInstanceMockMvc.perform(get("/api/_search/tag-instances?query=id:" + tagInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tagInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].showOrder").value(hasItem(DEFAULT_SHOW_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
}
