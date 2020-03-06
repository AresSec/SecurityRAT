package org.appsec.securityrat.web.rest;

import org.appsec.securityrat.SecurityRatApp;
import org.appsec.securityrat.domain.TagCategory;
import org.appsec.securityrat.repository.TagCategoryRepository;
import org.appsec.securityrat.repository.search.TagCategorySearchRepository;
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
 * Integration tests for the {@link TagCategoryResource} REST controller.
 */
@SpringBootTest(classes = SecurityRatApp.class)
public class TagCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SHOW_ORDER = 1;
    private static final Integer UPDATED_SHOW_ORDER = 2;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private TagCategoryRepository tagCategoryRepository;

    /**
     * This repository is mocked in the org.appsec.securityrat.repository.search test package.
     *
     * @see org.appsec.securityrat.repository.search.TagCategorySearchRepositoryMockConfiguration
     */
    @Autowired
    private TagCategorySearchRepository mockTagCategorySearchRepository;

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

    private MockMvc restTagCategoryMockMvc;

    private TagCategory tagCategory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TagCategoryResource tagCategoryResource = new TagCategoryResource(tagCategoryRepository, mockTagCategorySearchRepository);
        this.restTagCategoryMockMvc = MockMvcBuilders.standaloneSetup(tagCategoryResource)
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
    public static TagCategory createEntity(EntityManager em) {
        TagCategory tagCategory = new TagCategory();
        tagCategory.setName(DEFAULT_NAME);
        tagCategory.setDescription(DEFAULT_DESCRIPTION);
        tagCategory.setShowOrder(DEFAULT_SHOW_ORDER);
        tagCategory.setActive(DEFAULT_ACTIVE);
        return tagCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TagCategory createUpdatedEntity(EntityManager em) {
        TagCategory tagCategory = new TagCategory();
        tagCategory.setName(UPDATED_NAME);
        tagCategory.setDescription(UPDATED_DESCRIPTION);
        tagCategory.setShowOrder(UPDATED_SHOW_ORDER);
        tagCategory.setActive(UPDATED_ACTIVE);
        return tagCategory;
    }

    @BeforeEach
    public void initTest() {
        tagCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createTagCategory() throws Exception {
        int databaseSizeBeforeCreate = tagCategoryRepository.findAll().size();

        // Create the TagCategory
        restTagCategoryMockMvc.perform(post("/api/tag-categories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagCategory)))
            .andExpect(status().isCreated());

        // Validate the TagCategory in the database
        List<TagCategory> tagCategoryList = tagCategoryRepository.findAll();
        assertThat(tagCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        TagCategory testTagCategory = tagCategoryList.get(tagCategoryList.size() - 1);
        assertThat(testTagCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTagCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTagCategory.getShowOrder()).isEqualTo(DEFAULT_SHOW_ORDER);
        assertThat(testTagCategory.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the TagCategory in Elasticsearch
        verify(mockTagCategorySearchRepository, times(1)).save(testTagCategory);
    }

    @Test
    @Transactional
    public void createTagCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tagCategoryRepository.findAll().size();

        // Create the TagCategory with an existing ID
        tagCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTagCategoryMockMvc.perform(post("/api/tag-categories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagCategory)))
            .andExpect(status().isBadRequest());

        // Validate the TagCategory in the database
        List<TagCategory> tagCategoryList = tagCategoryRepository.findAll();
        assertThat(tagCategoryList).hasSize(databaseSizeBeforeCreate);

        // Validate the TagCategory in Elasticsearch
        verify(mockTagCategorySearchRepository, times(0)).save(tagCategory);
    }


    @Test
    @Transactional
    public void getAllTagCategories() throws Exception {
        // Initialize the database
        tagCategoryRepository.saveAndFlush(tagCategory);

        // Get all the tagCategoryList
        restTagCategoryMockMvc.perform(get("/api/tag-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tagCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].showOrder").value(hasItem(DEFAULT_SHOW_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTagCategory() throws Exception {
        // Initialize the database
        tagCategoryRepository.saveAndFlush(tagCategory);

        // Get the tagCategory
        restTagCategoryMockMvc.perform(get("/api/tag-categories/{id}", tagCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tagCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.showOrder").value(DEFAULT_SHOW_ORDER))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTagCategory() throws Exception {
        // Get the tagCategory
        restTagCategoryMockMvc.perform(get("/api/tag-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTagCategory() throws Exception {
        // Initialize the database
        tagCategoryRepository.saveAndFlush(tagCategory);

        int databaseSizeBeforeUpdate = tagCategoryRepository.findAll().size();

        // Update the tagCategory
        TagCategory updatedTagCategory = tagCategoryRepository.findById(tagCategory.getId()).get();
        // Disconnect from session so that the updates on updatedTagCategory are not directly saved in db
        em.detach(updatedTagCategory);
        updatedTagCategory.setName(UPDATED_NAME);
        updatedTagCategory.setDescription(UPDATED_DESCRIPTION);
        updatedTagCategory.setShowOrder(UPDATED_SHOW_ORDER);
        updatedTagCategory.setActive(UPDATED_ACTIVE);

        restTagCategoryMockMvc.perform(put("/api/tag-categories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTagCategory)))
            .andExpect(status().isOk());

        // Validate the TagCategory in the database
        List<TagCategory> tagCategoryList = tagCategoryRepository.findAll();
        assertThat(tagCategoryList).hasSize(databaseSizeBeforeUpdate);
        TagCategory testTagCategory = tagCategoryList.get(tagCategoryList.size() - 1);
        assertThat(testTagCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTagCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTagCategory.getShowOrder()).isEqualTo(UPDATED_SHOW_ORDER);
        assertThat(testTagCategory.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the TagCategory in Elasticsearch
        verify(mockTagCategorySearchRepository, times(1)).save(testTagCategory);
    }

    @Test
    @Transactional
    public void updateNonExistingTagCategory() throws Exception {
        int databaseSizeBeforeUpdate = tagCategoryRepository.findAll().size();

        // Create the TagCategory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTagCategoryMockMvc.perform(put("/api/tag-categories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagCategory)))
            .andExpect(status().isBadRequest());

        // Validate the TagCategory in the database
        List<TagCategory> tagCategoryList = tagCategoryRepository.findAll();
        assertThat(tagCategoryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TagCategory in Elasticsearch
        verify(mockTagCategorySearchRepository, times(0)).save(tagCategory);
    }

    @Test
    @Transactional
    public void deleteTagCategory() throws Exception {
        // Initialize the database
        tagCategoryRepository.saveAndFlush(tagCategory);

        int databaseSizeBeforeDelete = tagCategoryRepository.findAll().size();

        // Delete the tagCategory
        restTagCategoryMockMvc.perform(delete("/api/tag-categories/{id}", tagCategory.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TagCategory> tagCategoryList = tagCategoryRepository.findAll();
        assertThat(tagCategoryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TagCategory in Elasticsearch
        verify(mockTagCategorySearchRepository, times(1)).deleteById(tagCategory.getId());
    }

    @Test
    @Transactional
    public void searchTagCategory() throws Exception {
        // Initialize the database
        tagCategoryRepository.saveAndFlush(tagCategory);
        when(mockTagCategorySearchRepository.search(queryStringQuery("id:" + tagCategory.getId())))
            .thenReturn(Collections.singletonList(tagCategory));
        // Search the tagCategory
        restTagCategoryMockMvc.perform(get("/api/_search/tag-categories?query=id:" + tagCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tagCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].showOrder").value(hasItem(DEFAULT_SHOW_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
}
