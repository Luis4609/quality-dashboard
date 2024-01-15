package com.quality.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.quality.app.IntegrationTest;
import com.quality.app.domain.MetricThreshold;
import com.quality.app.repository.MetricThresholdRepository;
import com.quality.app.service.dto.MetricThresholdDTO;
import com.quality.app.service.mapper.MetricThresholdMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MetricThresholdResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MetricThresholdResourceIT {

    private static final String DEFAULT_ENTITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_METRIC_NAME = "AAAAAAAAAA";
    private static final String UPDATED_METRIC_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_SUCCESS_PERCENTAGE = 1D;
    private static final Double UPDATED_SUCCESS_PERCENTAGE = 2D;
    private static final Double SMALLER_SUCCESS_PERCENTAGE = 1D - 1D;

    private static final Double DEFAULT_DANGER_PERCENTAGE = 1D;
    private static final Double UPDATED_DANGER_PERCENTAGE = 2D;
    private static final Double SMALLER_DANGER_PERCENTAGE = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/metric-thresholds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MetricThresholdRepository metricThresholdRepository;

    @Autowired
    private MetricThresholdMapper metricThresholdMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMetricThresholdMockMvc;

    private MetricThreshold metricThreshold;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MetricThreshold createEntity(EntityManager em) {
        MetricThreshold metricThreshold = new MetricThreshold()
            .entityName(DEFAULT_ENTITY_NAME)
            .metricName(DEFAULT_METRIC_NAME)
            .successPercentage(DEFAULT_SUCCESS_PERCENTAGE)
            .dangerPercentage(DEFAULT_DANGER_PERCENTAGE);
        return metricThreshold;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MetricThreshold createUpdatedEntity(EntityManager em) {
        MetricThreshold metricThreshold = new MetricThreshold()
            .entityName(UPDATED_ENTITY_NAME)
            .metricName(UPDATED_METRIC_NAME)
            .successPercentage(UPDATED_SUCCESS_PERCENTAGE)
            .dangerPercentage(UPDATED_DANGER_PERCENTAGE);
        return metricThreshold;
    }

    @BeforeEach
    public void initTest() {
        metricThreshold = createEntity(em);
    }

    @Test
    @Transactional
    void createMetricThreshold() throws Exception {
        int databaseSizeBeforeCreate = metricThresholdRepository.findAll().size();
        // Create the MetricThreshold
        MetricThresholdDTO metricThresholdDTO = metricThresholdMapper.toDto(metricThreshold);
        restMetricThresholdMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metricThresholdDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MetricThreshold in the database
        List<MetricThreshold> metricThresholdList = metricThresholdRepository.findAll();
        assertThat(metricThresholdList).hasSize(databaseSizeBeforeCreate + 1);
        MetricThreshold testMetricThreshold = metricThresholdList.get(metricThresholdList.size() - 1);
        assertThat(testMetricThreshold.getEntityName()).isEqualTo(DEFAULT_ENTITY_NAME);
        assertThat(testMetricThreshold.getMetricName()).isEqualTo(DEFAULT_METRIC_NAME);
        assertThat(testMetricThreshold.getSuccessPercentage()).isEqualTo(DEFAULT_SUCCESS_PERCENTAGE);
        assertThat(testMetricThreshold.getDangerPercentage()).isEqualTo(DEFAULT_DANGER_PERCENTAGE);
    }

    @Test
    @Transactional
    void createMetricThresholdWithExistingId() throws Exception {
        // Create the MetricThreshold with an existing ID
        metricThreshold.setId(1L);
        MetricThresholdDTO metricThresholdDTO = metricThresholdMapper.toDto(metricThreshold);

        int databaseSizeBeforeCreate = metricThresholdRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMetricThresholdMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metricThresholdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetricThreshold in the database
        List<MetricThreshold> metricThresholdList = metricThresholdRepository.findAll();
        assertThat(metricThresholdList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEntityNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = metricThresholdRepository.findAll().size();
        // set the field null
        metricThreshold.setEntityName(null);

        // Create the MetricThreshold, which fails.
        MetricThresholdDTO metricThresholdDTO = metricThresholdMapper.toDto(metricThreshold);

        restMetricThresholdMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metricThresholdDTO))
            )
            .andExpect(status().isBadRequest());

        List<MetricThreshold> metricThresholdList = metricThresholdRepository.findAll();
        assertThat(metricThresholdList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMetricNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = metricThresholdRepository.findAll().size();
        // set the field null
        metricThreshold.setMetricName(null);

        // Create the MetricThreshold, which fails.
        MetricThresholdDTO metricThresholdDTO = metricThresholdMapper.toDto(metricThreshold);

        restMetricThresholdMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metricThresholdDTO))
            )
            .andExpect(status().isBadRequest());

        List<MetricThreshold> metricThresholdList = metricThresholdRepository.findAll();
        assertThat(metricThresholdList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMetricThresholds() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList
        restMetricThresholdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(metricThreshold.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].metricName").value(hasItem(DEFAULT_METRIC_NAME)))
            .andExpect(jsonPath("$.[*].successPercentage").value(hasItem(DEFAULT_SUCCESS_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].dangerPercentage").value(hasItem(DEFAULT_DANGER_PERCENTAGE.doubleValue())));
    }

    @Test
    @Transactional
    void getMetricThreshold() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get the metricThreshold
        restMetricThresholdMockMvc
            .perform(get(ENTITY_API_URL_ID, metricThreshold.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(metricThreshold.getId().intValue()))
            .andExpect(jsonPath("$.entityName").value(DEFAULT_ENTITY_NAME))
            .andExpect(jsonPath("$.metricName").value(DEFAULT_METRIC_NAME))
            .andExpect(jsonPath("$.successPercentage").value(DEFAULT_SUCCESS_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.dangerPercentage").value(DEFAULT_DANGER_PERCENTAGE.doubleValue()));
    }

    @Test
    @Transactional
    void getMetricThresholdsByIdFiltering() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        Long id = metricThreshold.getId();

        defaultMetricThresholdShouldBeFound("id.equals=" + id);
        defaultMetricThresholdShouldNotBeFound("id.notEquals=" + id);

        defaultMetricThresholdShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMetricThresholdShouldNotBeFound("id.greaterThan=" + id);

        defaultMetricThresholdShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMetricThresholdShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMetricThresholdsByEntityNameIsEqualToSomething() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList where entityName equals to DEFAULT_ENTITY_NAME
        defaultMetricThresholdShouldBeFound("entityName.equals=" + DEFAULT_ENTITY_NAME);

        // Get all the metricThresholdList where entityName equals to UPDATED_ENTITY_NAME
        defaultMetricThresholdShouldNotBeFound("entityName.equals=" + UPDATED_ENTITY_NAME);
    }

    @Test
    @Transactional
    void getAllMetricThresholdsByEntityNameIsInShouldWork() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList where entityName in DEFAULT_ENTITY_NAME or UPDATED_ENTITY_NAME
        defaultMetricThresholdShouldBeFound("entityName.in=" + DEFAULT_ENTITY_NAME + "," + UPDATED_ENTITY_NAME);

        // Get all the metricThresholdList where entityName equals to UPDATED_ENTITY_NAME
        defaultMetricThresholdShouldNotBeFound("entityName.in=" + UPDATED_ENTITY_NAME);
    }

    @Test
    @Transactional
    void getAllMetricThresholdsByEntityNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList where entityName is not null
        defaultMetricThresholdShouldBeFound("entityName.specified=true");

        // Get all the metricThresholdList where entityName is null
        defaultMetricThresholdShouldNotBeFound("entityName.specified=false");
    }

    @Test
    @Transactional
    void getAllMetricThresholdsByEntityNameContainsSomething() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList where entityName contains DEFAULT_ENTITY_NAME
        defaultMetricThresholdShouldBeFound("entityName.contains=" + DEFAULT_ENTITY_NAME);

        // Get all the metricThresholdList where entityName contains UPDATED_ENTITY_NAME
        defaultMetricThresholdShouldNotBeFound("entityName.contains=" + UPDATED_ENTITY_NAME);
    }

    @Test
    @Transactional
    void getAllMetricThresholdsByEntityNameNotContainsSomething() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList where entityName does not contain DEFAULT_ENTITY_NAME
        defaultMetricThresholdShouldNotBeFound("entityName.doesNotContain=" + DEFAULT_ENTITY_NAME);

        // Get all the metricThresholdList where entityName does not contain UPDATED_ENTITY_NAME
        defaultMetricThresholdShouldBeFound("entityName.doesNotContain=" + UPDATED_ENTITY_NAME);
    }

    @Test
    @Transactional
    void getAllMetricThresholdsByMetricNameIsEqualToSomething() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList where metricName equals to DEFAULT_METRIC_NAME
        defaultMetricThresholdShouldBeFound("metricName.equals=" + DEFAULT_METRIC_NAME);

        // Get all the metricThresholdList where metricName equals to UPDATED_METRIC_NAME
        defaultMetricThresholdShouldNotBeFound("metricName.equals=" + UPDATED_METRIC_NAME);
    }

    @Test
    @Transactional
    void getAllMetricThresholdsByMetricNameIsInShouldWork() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList where metricName in DEFAULT_METRIC_NAME or UPDATED_METRIC_NAME
        defaultMetricThresholdShouldBeFound("metricName.in=" + DEFAULT_METRIC_NAME + "," + UPDATED_METRIC_NAME);

        // Get all the metricThresholdList where metricName equals to UPDATED_METRIC_NAME
        defaultMetricThresholdShouldNotBeFound("metricName.in=" + UPDATED_METRIC_NAME);
    }

    @Test
    @Transactional
    void getAllMetricThresholdsByMetricNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList where metricName is not null
        defaultMetricThresholdShouldBeFound("metricName.specified=true");

        // Get all the metricThresholdList where metricName is null
        defaultMetricThresholdShouldNotBeFound("metricName.specified=false");
    }

    @Test
    @Transactional
    void getAllMetricThresholdsByMetricNameContainsSomething() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList where metricName contains DEFAULT_METRIC_NAME
        defaultMetricThresholdShouldBeFound("metricName.contains=" + DEFAULT_METRIC_NAME);

        // Get all the metricThresholdList where metricName contains UPDATED_METRIC_NAME
        defaultMetricThresholdShouldNotBeFound("metricName.contains=" + UPDATED_METRIC_NAME);
    }

    @Test
    @Transactional
    void getAllMetricThresholdsByMetricNameNotContainsSomething() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList where metricName does not contain DEFAULT_METRIC_NAME
        defaultMetricThresholdShouldNotBeFound("metricName.doesNotContain=" + DEFAULT_METRIC_NAME);

        // Get all the metricThresholdList where metricName does not contain UPDATED_METRIC_NAME
        defaultMetricThresholdShouldBeFound("metricName.doesNotContain=" + UPDATED_METRIC_NAME);
    }

    @Test
    @Transactional
    void getAllMetricThresholdsBySuccessPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList where successPercentage equals to DEFAULT_SUCCESS_PERCENTAGE
        defaultMetricThresholdShouldBeFound("successPercentage.equals=" + DEFAULT_SUCCESS_PERCENTAGE);

        // Get all the metricThresholdList where successPercentage equals to UPDATED_SUCCESS_PERCENTAGE
        defaultMetricThresholdShouldNotBeFound("successPercentage.equals=" + UPDATED_SUCCESS_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllMetricThresholdsBySuccessPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList where successPercentage in DEFAULT_SUCCESS_PERCENTAGE or UPDATED_SUCCESS_PERCENTAGE
        defaultMetricThresholdShouldBeFound("successPercentage.in=" + DEFAULT_SUCCESS_PERCENTAGE + "," + UPDATED_SUCCESS_PERCENTAGE);

        // Get all the metricThresholdList where successPercentage equals to UPDATED_SUCCESS_PERCENTAGE
        defaultMetricThresholdShouldNotBeFound("successPercentage.in=" + UPDATED_SUCCESS_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllMetricThresholdsBySuccessPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList where successPercentage is not null
        defaultMetricThresholdShouldBeFound("successPercentage.specified=true");

        // Get all the metricThresholdList where successPercentage is null
        defaultMetricThresholdShouldNotBeFound("successPercentage.specified=false");
    }

    @Test
    @Transactional
    void getAllMetricThresholdsBySuccessPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList where successPercentage is greater than or equal to DEFAULT_SUCCESS_PERCENTAGE
        defaultMetricThresholdShouldBeFound("successPercentage.greaterThanOrEqual=" + DEFAULT_SUCCESS_PERCENTAGE);

        // Get all the metricThresholdList where successPercentage is greater than or equal to UPDATED_SUCCESS_PERCENTAGE
        defaultMetricThresholdShouldNotBeFound("successPercentage.greaterThanOrEqual=" + UPDATED_SUCCESS_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllMetricThresholdsBySuccessPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList where successPercentage is less than or equal to DEFAULT_SUCCESS_PERCENTAGE
        defaultMetricThresholdShouldBeFound("successPercentage.lessThanOrEqual=" + DEFAULT_SUCCESS_PERCENTAGE);

        // Get all the metricThresholdList where successPercentage is less than or equal to SMALLER_SUCCESS_PERCENTAGE
        defaultMetricThresholdShouldNotBeFound("successPercentage.lessThanOrEqual=" + SMALLER_SUCCESS_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllMetricThresholdsBySuccessPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList where successPercentage is less than DEFAULT_SUCCESS_PERCENTAGE
        defaultMetricThresholdShouldNotBeFound("successPercentage.lessThan=" + DEFAULT_SUCCESS_PERCENTAGE);

        // Get all the metricThresholdList where successPercentage is less than UPDATED_SUCCESS_PERCENTAGE
        defaultMetricThresholdShouldBeFound("successPercentage.lessThan=" + UPDATED_SUCCESS_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllMetricThresholdsBySuccessPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList where successPercentage is greater than DEFAULT_SUCCESS_PERCENTAGE
        defaultMetricThresholdShouldNotBeFound("successPercentage.greaterThan=" + DEFAULT_SUCCESS_PERCENTAGE);

        // Get all the metricThresholdList where successPercentage is greater than SMALLER_SUCCESS_PERCENTAGE
        defaultMetricThresholdShouldBeFound("successPercentage.greaterThan=" + SMALLER_SUCCESS_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllMetricThresholdsByDangerPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList where dangerPercentage equals to DEFAULT_DANGER_PERCENTAGE
        defaultMetricThresholdShouldBeFound("dangerPercentage.equals=" + DEFAULT_DANGER_PERCENTAGE);

        // Get all the metricThresholdList where dangerPercentage equals to UPDATED_DANGER_PERCENTAGE
        defaultMetricThresholdShouldNotBeFound("dangerPercentage.equals=" + UPDATED_DANGER_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllMetricThresholdsByDangerPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList where dangerPercentage in DEFAULT_DANGER_PERCENTAGE or UPDATED_DANGER_PERCENTAGE
        defaultMetricThresholdShouldBeFound("dangerPercentage.in=" + DEFAULT_DANGER_PERCENTAGE + "," + UPDATED_DANGER_PERCENTAGE);

        // Get all the metricThresholdList where dangerPercentage equals to UPDATED_DANGER_PERCENTAGE
        defaultMetricThresholdShouldNotBeFound("dangerPercentage.in=" + UPDATED_DANGER_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllMetricThresholdsByDangerPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList where dangerPercentage is not null
        defaultMetricThresholdShouldBeFound("dangerPercentage.specified=true");

        // Get all the metricThresholdList where dangerPercentage is null
        defaultMetricThresholdShouldNotBeFound("dangerPercentage.specified=false");
    }

    @Test
    @Transactional
    void getAllMetricThresholdsByDangerPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList where dangerPercentage is greater than or equal to DEFAULT_DANGER_PERCENTAGE
        defaultMetricThresholdShouldBeFound("dangerPercentage.greaterThanOrEqual=" + DEFAULT_DANGER_PERCENTAGE);

        // Get all the metricThresholdList where dangerPercentage is greater than or equal to UPDATED_DANGER_PERCENTAGE
        defaultMetricThresholdShouldNotBeFound("dangerPercentage.greaterThanOrEqual=" + UPDATED_DANGER_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllMetricThresholdsByDangerPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList where dangerPercentage is less than or equal to DEFAULT_DANGER_PERCENTAGE
        defaultMetricThresholdShouldBeFound("dangerPercentage.lessThanOrEqual=" + DEFAULT_DANGER_PERCENTAGE);

        // Get all the metricThresholdList where dangerPercentage is less than or equal to SMALLER_DANGER_PERCENTAGE
        defaultMetricThresholdShouldNotBeFound("dangerPercentage.lessThanOrEqual=" + SMALLER_DANGER_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllMetricThresholdsByDangerPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList where dangerPercentage is less than DEFAULT_DANGER_PERCENTAGE
        defaultMetricThresholdShouldNotBeFound("dangerPercentage.lessThan=" + DEFAULT_DANGER_PERCENTAGE);

        // Get all the metricThresholdList where dangerPercentage is less than UPDATED_DANGER_PERCENTAGE
        defaultMetricThresholdShouldBeFound("dangerPercentage.lessThan=" + UPDATED_DANGER_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllMetricThresholdsByDangerPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        // Get all the metricThresholdList where dangerPercentage is greater than DEFAULT_DANGER_PERCENTAGE
        defaultMetricThresholdShouldNotBeFound("dangerPercentage.greaterThan=" + DEFAULT_DANGER_PERCENTAGE);

        // Get all the metricThresholdList where dangerPercentage is greater than SMALLER_DANGER_PERCENTAGE
        defaultMetricThresholdShouldBeFound("dangerPercentage.greaterThan=" + SMALLER_DANGER_PERCENTAGE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMetricThresholdShouldBeFound(String filter) throws Exception {
        restMetricThresholdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(metricThreshold.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].metricName").value(hasItem(DEFAULT_METRIC_NAME)))
            .andExpect(jsonPath("$.[*].successPercentage").value(hasItem(DEFAULT_SUCCESS_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].dangerPercentage").value(hasItem(DEFAULT_DANGER_PERCENTAGE.doubleValue())));

        // Check, that the count call also returns 1
        restMetricThresholdMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMetricThresholdShouldNotBeFound(String filter) throws Exception {
        restMetricThresholdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMetricThresholdMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMetricThreshold() throws Exception {
        // Get the metricThreshold
        restMetricThresholdMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMetricThreshold() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        int databaseSizeBeforeUpdate = metricThresholdRepository.findAll().size();

        // Update the metricThreshold
        MetricThreshold updatedMetricThreshold = metricThresholdRepository.findById(metricThreshold.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMetricThreshold are not directly saved in db
        em.detach(updatedMetricThreshold);
        updatedMetricThreshold
            .entityName(UPDATED_ENTITY_NAME)
            .metricName(UPDATED_METRIC_NAME)
            .successPercentage(UPDATED_SUCCESS_PERCENTAGE)
            .dangerPercentage(UPDATED_DANGER_PERCENTAGE);
        MetricThresholdDTO metricThresholdDTO = metricThresholdMapper.toDto(updatedMetricThreshold);

        restMetricThresholdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metricThresholdDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metricThresholdDTO))
            )
            .andExpect(status().isOk());

        // Validate the MetricThreshold in the database
        List<MetricThreshold> metricThresholdList = metricThresholdRepository.findAll();
        assertThat(metricThresholdList).hasSize(databaseSizeBeforeUpdate);
        MetricThreshold testMetricThreshold = metricThresholdList.get(metricThresholdList.size() - 1);
        assertThat(testMetricThreshold.getEntityName()).isEqualTo(UPDATED_ENTITY_NAME);
        assertThat(testMetricThreshold.getMetricName()).isEqualTo(UPDATED_METRIC_NAME);
        assertThat(testMetricThreshold.getSuccessPercentage()).isEqualTo(UPDATED_SUCCESS_PERCENTAGE);
        assertThat(testMetricThreshold.getDangerPercentage()).isEqualTo(UPDATED_DANGER_PERCENTAGE);
    }

    @Test
    @Transactional
    void putNonExistingMetricThreshold() throws Exception {
        int databaseSizeBeforeUpdate = metricThresholdRepository.findAll().size();
        metricThreshold.setId(longCount.incrementAndGet());

        // Create the MetricThreshold
        MetricThresholdDTO metricThresholdDTO = metricThresholdMapper.toDto(metricThreshold);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetricThresholdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metricThresholdDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metricThresholdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetricThreshold in the database
        List<MetricThreshold> metricThresholdList = metricThresholdRepository.findAll();
        assertThat(metricThresholdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMetricThreshold() throws Exception {
        int databaseSizeBeforeUpdate = metricThresholdRepository.findAll().size();
        metricThreshold.setId(longCount.incrementAndGet());

        // Create the MetricThreshold
        MetricThresholdDTO metricThresholdDTO = metricThresholdMapper.toDto(metricThreshold);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetricThresholdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metricThresholdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetricThreshold in the database
        List<MetricThreshold> metricThresholdList = metricThresholdRepository.findAll();
        assertThat(metricThresholdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMetricThreshold() throws Exception {
        int databaseSizeBeforeUpdate = metricThresholdRepository.findAll().size();
        metricThreshold.setId(longCount.incrementAndGet());

        // Create the MetricThreshold
        MetricThresholdDTO metricThresholdDTO = metricThresholdMapper.toDto(metricThreshold);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetricThresholdMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metricThresholdDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MetricThreshold in the database
        List<MetricThreshold> metricThresholdList = metricThresholdRepository.findAll();
        assertThat(metricThresholdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMetricThresholdWithPatch() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        int databaseSizeBeforeUpdate = metricThresholdRepository.findAll().size();

        // Update the metricThreshold using partial update
        MetricThreshold partialUpdatedMetricThreshold = new MetricThreshold();
        partialUpdatedMetricThreshold.setId(metricThreshold.getId());

        partialUpdatedMetricThreshold.successPercentage(UPDATED_SUCCESS_PERCENTAGE).dangerPercentage(UPDATED_DANGER_PERCENTAGE);

        restMetricThresholdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetricThreshold.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMetricThreshold))
            )
            .andExpect(status().isOk());

        // Validate the MetricThreshold in the database
        List<MetricThreshold> metricThresholdList = metricThresholdRepository.findAll();
        assertThat(metricThresholdList).hasSize(databaseSizeBeforeUpdate);
        MetricThreshold testMetricThreshold = metricThresholdList.get(metricThresholdList.size() - 1);
        assertThat(testMetricThreshold.getEntityName()).isEqualTo(DEFAULT_ENTITY_NAME);
        assertThat(testMetricThreshold.getMetricName()).isEqualTo(DEFAULT_METRIC_NAME);
        assertThat(testMetricThreshold.getSuccessPercentage()).isEqualTo(UPDATED_SUCCESS_PERCENTAGE);
        assertThat(testMetricThreshold.getDangerPercentage()).isEqualTo(UPDATED_DANGER_PERCENTAGE);
    }

    @Test
    @Transactional
    void fullUpdateMetricThresholdWithPatch() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        int databaseSizeBeforeUpdate = metricThresholdRepository.findAll().size();

        // Update the metricThreshold using partial update
        MetricThreshold partialUpdatedMetricThreshold = new MetricThreshold();
        partialUpdatedMetricThreshold.setId(metricThreshold.getId());

        partialUpdatedMetricThreshold
            .entityName(UPDATED_ENTITY_NAME)
            .metricName(UPDATED_METRIC_NAME)
            .successPercentage(UPDATED_SUCCESS_PERCENTAGE)
            .dangerPercentage(UPDATED_DANGER_PERCENTAGE);

        restMetricThresholdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetricThreshold.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMetricThreshold))
            )
            .andExpect(status().isOk());

        // Validate the MetricThreshold in the database
        List<MetricThreshold> metricThresholdList = metricThresholdRepository.findAll();
        assertThat(metricThresholdList).hasSize(databaseSizeBeforeUpdate);
        MetricThreshold testMetricThreshold = metricThresholdList.get(metricThresholdList.size() - 1);
        assertThat(testMetricThreshold.getEntityName()).isEqualTo(UPDATED_ENTITY_NAME);
        assertThat(testMetricThreshold.getMetricName()).isEqualTo(UPDATED_METRIC_NAME);
        assertThat(testMetricThreshold.getSuccessPercentage()).isEqualTo(UPDATED_SUCCESS_PERCENTAGE);
        assertThat(testMetricThreshold.getDangerPercentage()).isEqualTo(UPDATED_DANGER_PERCENTAGE);
    }

    @Test
    @Transactional
    void patchNonExistingMetricThreshold() throws Exception {
        int databaseSizeBeforeUpdate = metricThresholdRepository.findAll().size();
        metricThreshold.setId(longCount.incrementAndGet());

        // Create the MetricThreshold
        MetricThresholdDTO metricThresholdDTO = metricThresholdMapper.toDto(metricThreshold);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetricThresholdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, metricThresholdDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metricThresholdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetricThreshold in the database
        List<MetricThreshold> metricThresholdList = metricThresholdRepository.findAll();
        assertThat(metricThresholdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMetricThreshold() throws Exception {
        int databaseSizeBeforeUpdate = metricThresholdRepository.findAll().size();
        metricThreshold.setId(longCount.incrementAndGet());

        // Create the MetricThreshold
        MetricThresholdDTO metricThresholdDTO = metricThresholdMapper.toDto(metricThreshold);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetricThresholdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metricThresholdDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetricThreshold in the database
        List<MetricThreshold> metricThresholdList = metricThresholdRepository.findAll();
        assertThat(metricThresholdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMetricThreshold() throws Exception {
        int databaseSizeBeforeUpdate = metricThresholdRepository.findAll().size();
        metricThreshold.setId(longCount.incrementAndGet());

        // Create the MetricThreshold
        MetricThresholdDTO metricThresholdDTO = metricThresholdMapper.toDto(metricThreshold);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetricThresholdMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metricThresholdDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MetricThreshold in the database
        List<MetricThreshold> metricThresholdList = metricThresholdRepository.findAll();
        assertThat(metricThresholdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMetricThreshold() throws Exception {
        // Initialize the database
        metricThresholdRepository.saveAndFlush(metricThreshold);

        int databaseSizeBeforeDelete = metricThresholdRepository.findAll().size();

        // Delete the metricThreshold
        restMetricThresholdMockMvc
            .perform(delete(ENTITY_API_URL_ID, metricThreshold.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MetricThreshold> metricThresholdList = metricThresholdRepository.findAll();
        assertThat(metricThresholdList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
