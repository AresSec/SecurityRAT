package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.SecurityRatApp;
import org.appsec.securityrat.domain.ProjectType;
import org.appsec.securityrat.repository.ProjectTypeRepository;
import org.appsec.securityrat.repository.search.ProjectTypeSearchRepository;
import org.appsec.securityrat.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
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
 * Integration tests for the {@link ProjectTypeResource} REST controller.
 */
@SpringBootTest(classes = SecurityRatApp.class)
public class ProjectTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SHOW_ORDER = 1;
    private static final Integer UPDATED_SHOW_ORDER = 2;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ProjectTypeRepository projectTypeRepository;

    @Mock
    private ProjectTypeRepository projectTypeRepositoryMock;

    /**
     * This repository is mocked in the org.appsec.securityrat.repository.search test package.
     *
     * @see org.appsec.securityrat.repository.search.ProjectTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProjectTypeSearchRepository mockProjectTypeSearchRepository;

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

    private MockMvc restProjectTypeMockMvc;

    private ProjectType projectType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectTypeResource projectTypeResource = new ProjectTypeResource(projectTypeRepository, mockProjectTypeSearchRepository);
        this.restProjectTypeMockMvc = MockMvcBuilders.standaloneSetup(projectTypeResource)
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
    public static ProjectType createEntity(EntityManager em) {
        ProjectType projectType = new ProjectType();
        projectType.setName(DEFAULT_NAME);
        projectType.setDescription(DEFAULT_DESCRIPTION);
        projectType.setShowOrder(DEFAULT_SHOW_ORDER);
        projectType.setActive(DEFAULT_ACTIVE);
        return projectType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectType createUpdatedEntity(EntityManager em) {
        ProjectType projectType = new ProjectType();
        projectType.setName(UPDATED_NAME);
        projectType.setDescription(UPDATED_DESCRIPTION);
        projectType.setShowOrder(UPDATED_SHOW_ORDER);
        projectType.setActive(UPDATED_ACTIVE);
        return projectType;
    }

    @BeforeEach
    public void initTest() {
        projectType = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectType() throws Exception {
        int databaseSizeBeforeCreate = projectTypeRepository.findAll().size();

        // Create the ProjectType
        restProjectTypeMockMvc.perform(post("/api/project-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectType)))
            .andExpect(status().isCreated());

        // Validate the ProjectType in the database
        List<ProjectType> projectTypeList = projectTypeRepository.findAll();
        assertThat(projectTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectType testProjectType = projectTypeList.get(projectTypeList.size() - 1);
        assertThat(testProjectType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProjectType.getShowOrder()).isEqualTo(DEFAULT_SHOW_ORDER);
        assertThat(testProjectType.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the ProjectType in Elasticsearch
        verify(mockProjectTypeSearchRepository, times(1)).save(testProjectType);
    }

    @Test
    @Transactional
    public void createProjectTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectTypeRepository.findAll().size();

        // Create the ProjectType with an existing ID
        projectType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectTypeMockMvc.perform(post("/api/project-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectType)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectType in the database
        List<ProjectType> projectTypeList = projectTypeRepository.findAll();
        assertThat(projectTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProjectType in Elasticsearch
        verify(mockProjectTypeSearchRepository, times(0)).save(projectType);
    }


    @Test
    @Transactional
    public void getAllProjectTypes() throws Exception {
        // Initialize the database
        projectTypeRepository.saveAndFlush(projectType);

        // Get all the projectTypeList
        restProjectTypeMockMvc.perform(get("/api/project-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].showOrder").value(hasItem(DEFAULT_SHOW_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllProjectTypesWithEagerRelationshipsIsEnabled() throws Exception {
        ProjectTypeResource projectTypeResource = new ProjectTypeResource(projectTypeRepositoryMock, mockProjectTypeSearchRepository);
        when(projectTypeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restProjectTypeMockMvc = MockMvcBuilders.standaloneSetup(projectTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProjectTypeMockMvc.perform(get("/api/project-types?eagerload=true"))
        .andExpect(status().isOk());

        verify(projectTypeRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllProjectTypesWithEagerRelationshipsIsNotEnabled() throws Exception {
        ProjectTypeResource projectTypeResource = new ProjectTypeResource(projectTypeRepositoryMock, mockProjectTypeSearchRepository);
            when(projectTypeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restProjectTypeMockMvc = MockMvcBuilders.standaloneSetup(projectTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProjectTypeMockMvc.perform(get("/api/project-types?eagerload=true"))
        .andExpect(status().isOk());

            verify(projectTypeRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getProjectType() throws Exception {
        // Initialize the database
        projectTypeRepository.saveAndFlush(projectType);

        // Get the projectType
        restProjectTypeMockMvc.perform(get("/api/project-types/{id}", projectType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.showOrder").value(DEFAULT_SHOW_ORDER))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProjectType() throws Exception {
        // Get the projectType
        restProjectTypeMockMvc.perform(get("/api/project-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectType() throws Exception {
        // Initialize the database
        projectTypeRepository.saveAndFlush(projectType);

        int databaseSizeBeforeUpdate = projectTypeRepository.findAll().size();

        // Update the projectType
        ProjectType updatedProjectType = projectTypeRepository.findById(projectType.getId()).get();
        // Disconnect from session so that the updates on updatedProjectType are not directly saved in db
        em.detach(updatedProjectType);
        updatedProjectType.setName(UPDATED_NAME);
        updatedProjectType.setDescription(UPDATED_DESCRIPTION);
        updatedProjectType.setShowOrder(UPDATED_SHOW_ORDER);
        updatedProjectType.setActive(UPDATED_ACTIVE);

        restProjectTypeMockMvc.perform(put("/api/project-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectType)))
            .andExpect(status().isOk());

        // Validate the ProjectType in the database
        List<ProjectType> projectTypeList = projectTypeRepository.findAll();
        assertThat(projectTypeList).hasSize(databaseSizeBeforeUpdate);
        ProjectType testProjectType = projectTypeList.get(projectTypeList.size() - 1);
        assertThat(testProjectType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjectType.getShowOrder()).isEqualTo(UPDATED_SHOW_ORDER);
        assertThat(testProjectType.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the ProjectType in Elasticsearch
        verify(mockProjectTypeSearchRepository, times(1)).save(testProjectType);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectType() throws Exception {
        int databaseSizeBeforeUpdate = projectTypeRepository.findAll().size();

        // Create the ProjectType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectTypeMockMvc.perform(put("/api/project-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectType)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectType in the database
        List<ProjectType> projectTypeList = projectTypeRepository.findAll();
        assertThat(projectTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProjectType in Elasticsearch
        verify(mockProjectTypeSearchRepository, times(0)).save(projectType);
    }

    @Test
    @Transactional
    public void deleteProjectType() throws Exception {
        // Initialize the database
        projectTypeRepository.saveAndFlush(projectType);

        int databaseSizeBeforeDelete = projectTypeRepository.findAll().size();

        // Delete the projectType
        restProjectTypeMockMvc.perform(delete("/api/project-types/{id}", projectType.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectType> projectTypeList = projectTypeRepository.findAll();
        assertThat(projectTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProjectType in Elasticsearch
        verify(mockProjectTypeSearchRepository, times(1)).deleteById(projectType.getId());
    }

    @Test
    @Transactional
    public void searchProjectType() throws Exception {
        // Initialize the database
        projectTypeRepository.saveAndFlush(projectType);
        when(mockProjectTypeSearchRepository.search(queryStringQuery("id:" + projectType.getId())))
            .thenReturn(Collections.singletonList(projectType));
        // Search the projectType
        restProjectTypeMockMvc.perform(get("/api/_search/project-types?query=id:" + projectType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].showOrder").value(hasItem(DEFAULT_SHOW_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
}
