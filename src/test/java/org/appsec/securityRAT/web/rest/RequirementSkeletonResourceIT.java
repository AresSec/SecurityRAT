package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.SecurityRatApp;
import org.appsec.securityrat.domain.RequirementSkeleton;
import org.appsec.securityrat.repository.RequirementSkeletonRepository;
import org.appsec.securityrat.repository.search.RequirementSkeletonSearchRepository;
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
 * Integration tests for the {@link RequirementSkeletonResource} REST controller.
 */
@SpringBootTest(classes = SecurityRatApp.class)
public class RequirementSkeletonResourceIT {

    private static final String DEFAULT_UNIVERSAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_UNIVERSAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SHOW_ORDER = 1;
    private static final Integer UPDATED_SHOW_ORDER = 2;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private RequirementSkeletonRepository requirementSkeletonRepository;

    @Mock
    private RequirementSkeletonRepository requirementSkeletonRepositoryMock;

    /**
     * This repository is mocked in the org.appsec.securityrat.repository.search test package.
     *
     * @see org.appsec.securityrat.repository.search.RequirementSkeletonSearchRepositoryMockConfiguration
     */
    @Autowired
    private RequirementSkeletonSearchRepository mockRequirementSkeletonSearchRepository;

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

    private MockMvc restRequirementSkeletonMockMvc;

    private RequirementSkeleton requirementSkeleton;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RequirementSkeletonResource requirementSkeletonResource = new RequirementSkeletonResource(requirementSkeletonRepository, mockRequirementSkeletonSearchRepository);
        this.restRequirementSkeletonMockMvc = MockMvcBuilders.standaloneSetup(requirementSkeletonResource)
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
    public static RequirementSkeleton createEntity(EntityManager em) {
        RequirementSkeleton requirementSkeleton = new RequirementSkeleton();
        requirementSkeleton.setUniversalId(DEFAULT_UNIVERSAL_ID);
        requirementSkeleton.setShortName(DEFAULT_SHORT_NAME);
        requirementSkeleton.setDescription(DEFAULT_DESCRIPTION);
        requirementSkeleton.setShowOrder(DEFAULT_SHOW_ORDER);
        requirementSkeleton.setActive(DEFAULT_ACTIVE);
        return requirementSkeleton;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequirementSkeleton createUpdatedEntity(EntityManager em) {
        RequirementSkeleton requirementSkeleton = new RequirementSkeleton();
        requirementSkeleton.setUniversalId(UPDATED_UNIVERSAL_ID);
        requirementSkeleton.setShortName(UPDATED_SHORT_NAME);
        requirementSkeleton.setDescription(UPDATED_DESCRIPTION);
        requirementSkeleton.setShowOrder(UPDATED_SHOW_ORDER);
        requirementSkeleton.setActive(UPDATED_ACTIVE);
        return requirementSkeleton;
    }

    @BeforeEach
    public void initTest() {
        requirementSkeleton = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequirementSkeleton() throws Exception {
        int databaseSizeBeforeCreate = requirementSkeletonRepository.findAll().size();

        // Create the RequirementSkeleton
        restRequirementSkeletonMockMvc.perform(post("/api/requirement-skeletons")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requirementSkeleton)))
            .andExpect(status().isCreated());

        // Validate the RequirementSkeleton in the database
        List<RequirementSkeleton> requirementSkeletonList = requirementSkeletonRepository.findAll();
        assertThat(requirementSkeletonList).hasSize(databaseSizeBeforeCreate + 1);
        RequirementSkeleton testRequirementSkeleton = requirementSkeletonList.get(requirementSkeletonList.size() - 1);
        assertThat(testRequirementSkeleton.getUniversalId()).isEqualTo(DEFAULT_UNIVERSAL_ID);
        assertThat(testRequirementSkeleton.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testRequirementSkeleton.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRequirementSkeleton.getShowOrder()).isEqualTo(DEFAULT_SHOW_ORDER);
        assertThat(testRequirementSkeleton.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the RequirementSkeleton in Elasticsearch
        verify(mockRequirementSkeletonSearchRepository, times(1)).save(testRequirementSkeleton);
    }

    @Test
    @Transactional
    public void createRequirementSkeletonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requirementSkeletonRepository.findAll().size();

        // Create the RequirementSkeleton with an existing ID
        requirementSkeleton.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequirementSkeletonMockMvc.perform(post("/api/requirement-skeletons")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requirementSkeleton)))
            .andExpect(status().isBadRequest());

        // Validate the RequirementSkeleton in the database
        List<RequirementSkeleton> requirementSkeletonList = requirementSkeletonRepository.findAll();
        assertThat(requirementSkeletonList).hasSize(databaseSizeBeforeCreate);

        // Validate the RequirementSkeleton in Elasticsearch
        verify(mockRequirementSkeletonSearchRepository, times(0)).save(requirementSkeleton);
    }


    @Test
    @Transactional
    public void getAllRequirementSkeletons() throws Exception {
        // Initialize the database
        requirementSkeletonRepository.saveAndFlush(requirementSkeleton);

        // Get all the requirementSkeletonList
        restRequirementSkeletonMockMvc.perform(get("/api/requirement-skeletons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requirementSkeleton.getId().intValue())))
            .andExpect(jsonPath("$.[*].universalId").value(hasItem(DEFAULT_UNIVERSAL_ID)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].showOrder").value(hasItem(DEFAULT_SHOW_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllRequirementSkeletonsWithEagerRelationshipsIsEnabled() throws Exception {
        RequirementSkeletonResource requirementSkeletonResource = new RequirementSkeletonResource(requirementSkeletonRepositoryMock, mockRequirementSkeletonSearchRepository);
        when(requirementSkeletonRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restRequirementSkeletonMockMvc = MockMvcBuilders.standaloneSetup(requirementSkeletonResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRequirementSkeletonMockMvc.perform(get("/api/requirement-skeletons?eagerload=true"))
        .andExpect(status().isOk());

        verify(requirementSkeletonRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllRequirementSkeletonsWithEagerRelationshipsIsNotEnabled() throws Exception {
        RequirementSkeletonResource requirementSkeletonResource = new RequirementSkeletonResource(requirementSkeletonRepositoryMock, mockRequirementSkeletonSearchRepository);
            when(requirementSkeletonRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restRequirementSkeletonMockMvc = MockMvcBuilders.standaloneSetup(requirementSkeletonResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRequirementSkeletonMockMvc.perform(get("/api/requirement-skeletons?eagerload=true"))
        .andExpect(status().isOk());

            verify(requirementSkeletonRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRequirementSkeleton() throws Exception {
        // Initialize the database
        requirementSkeletonRepository.saveAndFlush(requirementSkeleton);

        // Get the requirementSkeleton
        restRequirementSkeletonMockMvc.perform(get("/api/requirement-skeletons/{id}", requirementSkeleton.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requirementSkeleton.getId().intValue()))
            .andExpect(jsonPath("$.universalId").value(DEFAULT_UNIVERSAL_ID))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.showOrder").value(DEFAULT_SHOW_ORDER))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRequirementSkeleton() throws Exception {
        // Get the requirementSkeleton
        restRequirementSkeletonMockMvc.perform(get("/api/requirement-skeletons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequirementSkeleton() throws Exception {
        // Initialize the database
        requirementSkeletonRepository.saveAndFlush(requirementSkeleton);

        int databaseSizeBeforeUpdate = requirementSkeletonRepository.findAll().size();

        // Update the requirementSkeleton
        RequirementSkeleton updatedRequirementSkeleton = requirementSkeletonRepository.findById(requirementSkeleton.getId()).get();
        // Disconnect from session so that the updates on updatedRequirementSkeleton are not directly saved in db
        em.detach(updatedRequirementSkeleton);
        updatedRequirementSkeleton.setUniversalId(UPDATED_UNIVERSAL_ID);
        updatedRequirementSkeleton.setShortName(UPDATED_SHORT_NAME);
        updatedRequirementSkeleton.setDescription(UPDATED_DESCRIPTION);
        updatedRequirementSkeleton.setShowOrder(UPDATED_SHOW_ORDER);
        updatedRequirementSkeleton.setActive(UPDATED_ACTIVE);

        restRequirementSkeletonMockMvc.perform(put("/api/requirement-skeletons")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRequirementSkeleton)))
            .andExpect(status().isOk());

        // Validate the RequirementSkeleton in the database
        List<RequirementSkeleton> requirementSkeletonList = requirementSkeletonRepository.findAll();
        assertThat(requirementSkeletonList).hasSize(databaseSizeBeforeUpdate);
        RequirementSkeleton testRequirementSkeleton = requirementSkeletonList.get(requirementSkeletonList.size() - 1);
        assertThat(testRequirementSkeleton.getUniversalId()).isEqualTo(UPDATED_UNIVERSAL_ID);
        assertThat(testRequirementSkeleton.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testRequirementSkeleton.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRequirementSkeleton.getShowOrder()).isEqualTo(UPDATED_SHOW_ORDER);
        assertThat(testRequirementSkeleton.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the RequirementSkeleton in Elasticsearch
        verify(mockRequirementSkeletonSearchRepository, times(1)).save(testRequirementSkeleton);
    }

    @Test
    @Transactional
    public void updateNonExistingRequirementSkeleton() throws Exception {
        int databaseSizeBeforeUpdate = requirementSkeletonRepository.findAll().size();

        // Create the RequirementSkeleton

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequirementSkeletonMockMvc.perform(put("/api/requirement-skeletons")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requirementSkeleton)))
            .andExpect(status().isBadRequest());

        // Validate the RequirementSkeleton in the database
        List<RequirementSkeleton> requirementSkeletonList = requirementSkeletonRepository.findAll();
        assertThat(requirementSkeletonList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RequirementSkeleton in Elasticsearch
        verify(mockRequirementSkeletonSearchRepository, times(0)).save(requirementSkeleton);
    }

    @Test
    @Transactional
    public void deleteRequirementSkeleton() throws Exception {
        // Initialize the database
        requirementSkeletonRepository.saveAndFlush(requirementSkeleton);

        int databaseSizeBeforeDelete = requirementSkeletonRepository.findAll().size();

        // Delete the requirementSkeleton
        restRequirementSkeletonMockMvc.perform(delete("/api/requirement-skeletons/{id}", requirementSkeleton.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RequirementSkeleton> requirementSkeletonList = requirementSkeletonRepository.findAll();
        assertThat(requirementSkeletonList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RequirementSkeleton in Elasticsearch
        verify(mockRequirementSkeletonSearchRepository, times(1)).deleteById(requirementSkeleton.getId());
    }

    @Test
    @Transactional
    public void searchRequirementSkeleton() throws Exception {
        // Initialize the database
        requirementSkeletonRepository.saveAndFlush(requirementSkeleton);
        when(mockRequirementSkeletonSearchRepository.search(queryStringQuery("id:" + requirementSkeleton.getId())))
            .thenReturn(Collections.singletonList(requirementSkeleton));
        // Search the requirementSkeleton
        restRequirementSkeletonMockMvc.perform(get("/api/_search/requirement-skeletons?query=id:" + requirementSkeleton.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requirementSkeleton.getId().intValue())))
            .andExpect(jsonPath("$.[*].universalId").value(hasItem(DEFAULT_UNIVERSAL_ID)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].showOrder").value(hasItem(DEFAULT_SHOW_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
}
