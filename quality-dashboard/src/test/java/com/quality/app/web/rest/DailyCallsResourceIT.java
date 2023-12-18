package com.quality.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.quality.app.IntegrationTest;
import com.quality.app.domain.DailyCalls;
import com.quality.app.repository.DailyCallsRepository;
import com.quality.app.service.dto.DailyCallsDTO;
import com.quality.app.service.mapper.DailyCallsMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link DailyCallsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DailyCallsResourceIT {

    private static final LocalDate DEFAULT_DAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DAY = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DAY = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_TOTAL_DAILY_RECEIVED_CALLS = 1;
    private static final Integer UPDATED_TOTAL_DAILY_RECEIVED_CALLS = 2;
    private static final Integer SMALLER_TOTAL_DAILY_RECEIVED_CALLS = 1 - 1;

    private static final Integer DEFAULT_TOTAL_DAILY_ATTENDED_CALLS = 1;
    private static final Integer UPDATED_TOTAL_DAILY_ATTENDED_CALLS = 2;
    private static final Integer SMALLER_TOTAL_DAILY_ATTENDED_CALLS = 1 - 1;

    private static final Integer DEFAULT_TOTAL_DAILY_MISSED_CALLS = 1;
    private static final Integer UPDATED_TOTAL_DAILY_MISSED_CALLS = 2;
    private static final Integer SMALLER_TOTAL_DAILY_MISSED_CALLS = 1 - 1;

    private static final Integer DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT = 1;
    private static final Integer UPDATED_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT = 2;
    private static final Integer SMALLER_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT = 1 - 1;

    private static final Integer DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT = 1;
    private static final Integer UPDATED_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT = 2;
    private static final Integer SMALLER_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT = 1 - 1;

    private static final Integer DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT = 1;
    private static final Integer UPDATED_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT = 2;
    private static final Integer SMALLER_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT = 1 - 1;

    private static final Integer DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT = 1;
    private static final Integer UPDATED_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT = 2;
    private static final Integer SMALLER_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT = 1 - 1;

    private static final Integer DEFAULT_TOTAL_DAILY_CALLS_TIME_IN_MIN = 1;
    private static final Integer UPDATED_TOTAL_DAILY_CALLS_TIME_IN_MIN = 2;
    private static final Integer SMALLER_TOTAL_DAILY_CALLS_TIME_IN_MIN = 1 - 1;

    private static final Integer DEFAULT_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN = 1;
    private static final Integer UPDATED_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN = 2;
    private static final Integer SMALLER_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN = 1 - 1;

    private static final Integer DEFAULT_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN = 1;
    private static final Integer UPDATED_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN = 2;
    private static final Integer SMALLER_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN = 1 - 1;

    private static final Float DEFAULT_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN = 1F;
    private static final Float UPDATED_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN = 2F;
    private static final Float SMALLER_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN = 1F - 1F;

    private static final Float DEFAULT_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN = 1F;
    private static final Float UPDATED_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN = 2F;
    private static final Float SMALLER_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN = 1F - 1F;

    private static final String ENTITY_API_URL = "/api/daily-calls";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DailyCallsRepository dailyCallsRepository;

    @Autowired
    private DailyCallsMapper dailyCallsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDailyCallsMockMvc;

    private DailyCalls dailyCalls;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DailyCalls createEntity(EntityManager em) {
        DailyCalls dailyCalls = new DailyCalls()
            .day(DEFAULT_DAY)
            .totalDailyReceivedCalls(DEFAULT_TOTAL_DAILY_RECEIVED_CALLS)
            .totalDailyAttendedCalls(DEFAULT_TOTAL_DAILY_ATTENDED_CALLS)
            .totalDailyMissedCalls(DEFAULT_TOTAL_DAILY_MISSED_CALLS)
            .totalDailyReceivedCallsExternalAgent(DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT)
            .totalDailyAttendedCallsExternalAgent(DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT)
            .totalDailyReceivedCallsInternalAgent(DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT)
            .totalDailyAttendedCallsInternalAgent(DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT)
            .totalDailyCallsTimeInMin(DEFAULT_TOTAL_DAILY_CALLS_TIME_IN_MIN)
            .totalDailyCallsTimeExternalAgentInMin(DEFAULT_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN)
            .totalDailyCallsTimeInternalAgentInMin(DEFAULT_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN)
            .avgDailyOperationTimeExternalAgentInMin(DEFAULT_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN)
            .avgDailyOperationTimeInternalAgentInMin(DEFAULT_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN);
        return dailyCalls;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DailyCalls createUpdatedEntity(EntityManager em) {
        DailyCalls dailyCalls = new DailyCalls()
            .day(UPDATED_DAY)
            .totalDailyReceivedCalls(UPDATED_TOTAL_DAILY_RECEIVED_CALLS)
            .totalDailyAttendedCalls(UPDATED_TOTAL_DAILY_ATTENDED_CALLS)
            .totalDailyMissedCalls(UPDATED_TOTAL_DAILY_MISSED_CALLS)
            .totalDailyReceivedCallsExternalAgent(UPDATED_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT)
            .totalDailyAttendedCallsExternalAgent(UPDATED_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT)
            .totalDailyReceivedCallsInternalAgent(UPDATED_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT)
            .totalDailyAttendedCallsInternalAgent(UPDATED_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT)
            .totalDailyCallsTimeInMin(UPDATED_TOTAL_DAILY_CALLS_TIME_IN_MIN)
            .totalDailyCallsTimeExternalAgentInMin(UPDATED_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN)
            .totalDailyCallsTimeInternalAgentInMin(UPDATED_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN)
            .avgDailyOperationTimeExternalAgentInMin(UPDATED_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN)
            .avgDailyOperationTimeInternalAgentInMin(UPDATED_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN);
        return dailyCalls;
    }

    @BeforeEach
    public void initTest() {
        dailyCalls = createEntity(em);
    }

    @Test
    @Transactional
    void createDailyCalls() throws Exception {
        int databaseSizeBeforeCreate = dailyCallsRepository.findAll().size();
        // Create the DailyCalls
        DailyCallsDTO dailyCallsDTO = dailyCallsMapper.toDto(dailyCalls);
        restDailyCallsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyCallsDTO)))
            .andExpect(status().isCreated());

        // Validate the DailyCalls in the database
        List<DailyCalls> dailyCallsList = dailyCallsRepository.findAll();
        assertThat(dailyCallsList).hasSize(databaseSizeBeforeCreate + 1);
        DailyCalls testDailyCalls = dailyCallsList.get(dailyCallsList.size() - 1);
        assertThat(testDailyCalls.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testDailyCalls.getTotalDailyReceivedCalls()).isEqualTo(DEFAULT_TOTAL_DAILY_RECEIVED_CALLS);
        assertThat(testDailyCalls.getTotalDailyAttendedCalls()).isEqualTo(DEFAULT_TOTAL_DAILY_ATTENDED_CALLS);
        assertThat(testDailyCalls.getTotalDailyMissedCalls()).isEqualTo(DEFAULT_TOTAL_DAILY_MISSED_CALLS);
        assertThat(testDailyCalls.getTotalDailyReceivedCallsExternalAgent()).isEqualTo(DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT);
        assertThat(testDailyCalls.getTotalDailyAttendedCallsExternalAgent()).isEqualTo(DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT);
        assertThat(testDailyCalls.getTotalDailyReceivedCallsInternalAgent()).isEqualTo(DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT);
        assertThat(testDailyCalls.getTotalDailyAttendedCallsInternalAgent()).isEqualTo(DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT);
        assertThat(testDailyCalls.getTotalDailyCallsTimeInMin()).isEqualTo(DEFAULT_TOTAL_DAILY_CALLS_TIME_IN_MIN);
        assertThat(testDailyCalls.getTotalDailyCallsTimeExternalAgentInMin())
            .isEqualTo(DEFAULT_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN);
        assertThat(testDailyCalls.getTotalDailyCallsTimeInternalAgentInMin())
            .isEqualTo(DEFAULT_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN);
        assertThat(testDailyCalls.getAvgDailyOperationTimeExternalAgentInMin())
            .isEqualTo(DEFAULT_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN);
        assertThat(testDailyCalls.getAvgDailyOperationTimeInternalAgentInMin())
            .isEqualTo(DEFAULT_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN);
    }

    @Test
    @Transactional
    void createDailyCallsWithExistingId() throws Exception {
        // Create the DailyCalls with an existing ID
        dailyCalls.setId(1L);
        DailyCallsDTO dailyCallsDTO = dailyCallsMapper.toDto(dailyCalls);

        int databaseSizeBeforeCreate = dailyCallsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDailyCallsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyCallsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DailyCalls in the database
        List<DailyCalls> dailyCallsList = dailyCallsRepository.findAll();
        assertThat(dailyCallsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyCallsRepository.findAll().size();
        // set the field null
        dailyCalls.setDay(null);

        // Create the DailyCalls, which fails.
        DailyCallsDTO dailyCallsDTO = dailyCallsMapper.toDto(dailyCalls);

        restDailyCallsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyCallsDTO)))
            .andExpect(status().isBadRequest());

        List<DailyCalls> dailyCallsList = dailyCallsRepository.findAll();
        assertThat(dailyCallsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalDailyAttendedCallsIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyCallsRepository.findAll().size();
        // set the field null
        dailyCalls.setTotalDailyAttendedCalls(null);

        // Create the DailyCalls, which fails.
        DailyCallsDTO dailyCallsDTO = dailyCallsMapper.toDto(dailyCalls);

        restDailyCallsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyCallsDTO)))
            .andExpect(status().isBadRequest());

        List<DailyCalls> dailyCallsList = dailyCallsRepository.findAll();
        assertThat(dailyCallsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalDailyMissedCallsIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyCallsRepository.findAll().size();
        // set the field null
        dailyCalls.setTotalDailyMissedCalls(null);

        // Create the DailyCalls, which fails.
        DailyCallsDTO dailyCallsDTO = dailyCallsMapper.toDto(dailyCalls);

        restDailyCallsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyCallsDTO)))
            .andExpect(status().isBadRequest());

        List<DailyCalls> dailyCallsList = dailyCallsRepository.findAll();
        assertThat(dailyCallsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalDailyReceivedCallsExternalAgentIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyCallsRepository.findAll().size();
        // set the field null
        dailyCalls.setTotalDailyReceivedCallsExternalAgent(null);

        // Create the DailyCalls, which fails.
        DailyCallsDTO dailyCallsDTO = dailyCallsMapper.toDto(dailyCalls);

        restDailyCallsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyCallsDTO)))
            .andExpect(status().isBadRequest());

        List<DailyCalls> dailyCallsList = dailyCallsRepository.findAll();
        assertThat(dailyCallsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalDailyAttendedCallsExternalAgentIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyCallsRepository.findAll().size();
        // set the field null
        dailyCalls.setTotalDailyAttendedCallsExternalAgent(null);

        // Create the DailyCalls, which fails.
        DailyCallsDTO dailyCallsDTO = dailyCallsMapper.toDto(dailyCalls);

        restDailyCallsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyCallsDTO)))
            .andExpect(status().isBadRequest());

        List<DailyCalls> dailyCallsList = dailyCallsRepository.findAll();
        assertThat(dailyCallsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalDailyAttendedCallsInternalAgentIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyCallsRepository.findAll().size();
        // set the field null
        dailyCalls.setTotalDailyAttendedCallsInternalAgent(null);

        // Create the DailyCalls, which fails.
        DailyCallsDTO dailyCallsDTO = dailyCallsMapper.toDto(dailyCalls);

        restDailyCallsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyCallsDTO)))
            .andExpect(status().isBadRequest());

        List<DailyCalls> dailyCallsList = dailyCallsRepository.findAll();
        assertThat(dailyCallsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalDailyCallsTimeInMinIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyCallsRepository.findAll().size();
        // set the field null
        dailyCalls.setTotalDailyCallsTimeInMin(null);

        // Create the DailyCalls, which fails.
        DailyCallsDTO dailyCallsDTO = dailyCallsMapper.toDto(dailyCalls);

        restDailyCallsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyCallsDTO)))
            .andExpect(status().isBadRequest());

        List<DailyCalls> dailyCallsList = dailyCallsRepository.findAll();
        assertThat(dailyCallsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalDailyCallsTimeExternalAgentInMinIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyCallsRepository.findAll().size();
        // set the field null
        dailyCalls.setTotalDailyCallsTimeExternalAgentInMin(null);

        // Create the DailyCalls, which fails.
        DailyCallsDTO dailyCallsDTO = dailyCallsMapper.toDto(dailyCalls);

        restDailyCallsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyCallsDTO)))
            .andExpect(status().isBadRequest());

        List<DailyCalls> dailyCallsList = dailyCallsRepository.findAll();
        assertThat(dailyCallsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalDailyCallsTimeInternalAgentInMinIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyCallsRepository.findAll().size();
        // set the field null
        dailyCalls.setTotalDailyCallsTimeInternalAgentInMin(null);

        // Create the DailyCalls, which fails.
        DailyCallsDTO dailyCallsDTO = dailyCallsMapper.toDto(dailyCalls);

        restDailyCallsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyCallsDTO)))
            .andExpect(status().isBadRequest());

        List<DailyCalls> dailyCallsList = dailyCallsRepository.findAll();
        assertThat(dailyCallsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAvgDailyOperationTimeExternalAgentInMinIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyCallsRepository.findAll().size();
        // set the field null
        dailyCalls.setAvgDailyOperationTimeExternalAgentInMin(null);

        // Create the DailyCalls, which fails.
        DailyCallsDTO dailyCallsDTO = dailyCallsMapper.toDto(dailyCalls);

        restDailyCallsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyCallsDTO)))
            .andExpect(status().isBadRequest());

        List<DailyCalls> dailyCallsList = dailyCallsRepository.findAll();
        assertThat(dailyCallsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDailyCalls() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList
        restDailyCallsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dailyCalls.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].totalDailyReceivedCalls").value(hasItem(DEFAULT_TOTAL_DAILY_RECEIVED_CALLS)))
            .andExpect(jsonPath("$.[*].totalDailyAttendedCalls").value(hasItem(DEFAULT_TOTAL_DAILY_ATTENDED_CALLS)))
            .andExpect(jsonPath("$.[*].totalDailyMissedCalls").value(hasItem(DEFAULT_TOTAL_DAILY_MISSED_CALLS)))
            .andExpect(
                jsonPath("$.[*].totalDailyReceivedCallsExternalAgent").value(hasItem(DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT))
            )
            .andExpect(
                jsonPath("$.[*].totalDailyAttendedCallsExternalAgent").value(hasItem(DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT))
            )
            .andExpect(
                jsonPath("$.[*].totalDailyReceivedCallsInternalAgent").value(hasItem(DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT))
            )
            .andExpect(
                jsonPath("$.[*].totalDailyAttendedCallsInternalAgent").value(hasItem(DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT))
            )
            .andExpect(jsonPath("$.[*].totalDailyCallsTimeInMin").value(hasItem(DEFAULT_TOTAL_DAILY_CALLS_TIME_IN_MIN)))
            .andExpect(
                jsonPath("$.[*].totalDailyCallsTimeExternalAgentInMin").value(hasItem(DEFAULT_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN))
            )
            .andExpect(
                jsonPath("$.[*].totalDailyCallsTimeInternalAgentInMin").value(hasItem(DEFAULT_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN))
            )
            .andExpect(
                jsonPath("$.[*].avgDailyOperationTimeExternalAgentInMin")
                    .value(hasItem(DEFAULT_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN.doubleValue()))
            )
            .andExpect(
                jsonPath("$.[*].avgDailyOperationTimeInternalAgentInMin")
                    .value(hasItem(DEFAULT_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN.doubleValue()))
            );
    }

    @Test
    @Transactional
    void getDailyCalls() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get the dailyCalls
        restDailyCallsMockMvc
            .perform(get(ENTITY_API_URL_ID, dailyCalls.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dailyCalls.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.totalDailyReceivedCalls").value(DEFAULT_TOTAL_DAILY_RECEIVED_CALLS))
            .andExpect(jsonPath("$.totalDailyAttendedCalls").value(DEFAULT_TOTAL_DAILY_ATTENDED_CALLS))
            .andExpect(jsonPath("$.totalDailyMissedCalls").value(DEFAULT_TOTAL_DAILY_MISSED_CALLS))
            .andExpect(jsonPath("$.totalDailyReceivedCallsExternalAgent").value(DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT))
            .andExpect(jsonPath("$.totalDailyAttendedCallsExternalAgent").value(DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT))
            .andExpect(jsonPath("$.totalDailyReceivedCallsInternalAgent").value(DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT))
            .andExpect(jsonPath("$.totalDailyAttendedCallsInternalAgent").value(DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT))
            .andExpect(jsonPath("$.totalDailyCallsTimeInMin").value(DEFAULT_TOTAL_DAILY_CALLS_TIME_IN_MIN))
            .andExpect(jsonPath("$.totalDailyCallsTimeExternalAgentInMin").value(DEFAULT_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN))
            .andExpect(jsonPath("$.totalDailyCallsTimeInternalAgentInMin").value(DEFAULT_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN))
            .andExpect(
                jsonPath("$.avgDailyOperationTimeExternalAgentInMin")
                    .value(DEFAULT_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN.doubleValue())
            )
            .andExpect(
                jsonPath("$.avgDailyOperationTimeInternalAgentInMin")
                    .value(DEFAULT_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN.doubleValue())
            );
    }

    @Test
    @Transactional
    void getDailyCallsByIdFiltering() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        Long id = dailyCalls.getId();

        defaultDailyCallsShouldBeFound("id.equals=" + id);
        defaultDailyCallsShouldNotBeFound("id.notEquals=" + id);

        defaultDailyCallsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDailyCallsShouldNotBeFound("id.greaterThan=" + id);

        defaultDailyCallsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDailyCallsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDailyCallsByDayIsEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where day equals to DEFAULT_DAY
        defaultDailyCallsShouldBeFound("day.equals=" + DEFAULT_DAY);

        // Get all the dailyCallsList where day equals to UPDATED_DAY
        defaultDailyCallsShouldNotBeFound("day.equals=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllDailyCallsByDayIsInShouldWork() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where day in DEFAULT_DAY or UPDATED_DAY
        defaultDailyCallsShouldBeFound("day.in=" + DEFAULT_DAY + "," + UPDATED_DAY);

        // Get all the dailyCallsList where day equals to UPDATED_DAY
        defaultDailyCallsShouldNotBeFound("day.in=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllDailyCallsByDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where day is not null
        defaultDailyCallsShouldBeFound("day.specified=true");

        // Get all the dailyCallsList where day is null
        defaultDailyCallsShouldNotBeFound("day.specified=false");
    }

    @Test
    @Transactional
    void getAllDailyCallsByDayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where day is greater than or equal to DEFAULT_DAY
        defaultDailyCallsShouldBeFound("day.greaterThanOrEqual=" + DEFAULT_DAY);

        // Get all the dailyCallsList where day is greater than or equal to UPDATED_DAY
        defaultDailyCallsShouldNotBeFound("day.greaterThanOrEqual=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllDailyCallsByDayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where day is less than or equal to DEFAULT_DAY
        defaultDailyCallsShouldBeFound("day.lessThanOrEqual=" + DEFAULT_DAY);

        // Get all the dailyCallsList where day is less than or equal to SMALLER_DAY
        defaultDailyCallsShouldNotBeFound("day.lessThanOrEqual=" + SMALLER_DAY);
    }

    @Test
    @Transactional
    void getAllDailyCallsByDayIsLessThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where day is less than DEFAULT_DAY
        defaultDailyCallsShouldNotBeFound("day.lessThan=" + DEFAULT_DAY);

        // Get all the dailyCallsList where day is less than UPDATED_DAY
        defaultDailyCallsShouldBeFound("day.lessThan=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllDailyCallsByDayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where day is greater than DEFAULT_DAY
        defaultDailyCallsShouldNotBeFound("day.greaterThan=" + DEFAULT_DAY);

        // Get all the dailyCallsList where day is greater than SMALLER_DAY
        defaultDailyCallsShouldBeFound("day.greaterThan=" + SMALLER_DAY);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyReceivedCallsIsEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyReceivedCalls equals to DEFAULT_TOTAL_DAILY_RECEIVED_CALLS
        defaultDailyCallsShouldBeFound("totalDailyReceivedCalls.equals=" + DEFAULT_TOTAL_DAILY_RECEIVED_CALLS);

        // Get all the dailyCallsList where totalDailyReceivedCalls equals to UPDATED_TOTAL_DAILY_RECEIVED_CALLS
        defaultDailyCallsShouldNotBeFound("totalDailyReceivedCalls.equals=" + UPDATED_TOTAL_DAILY_RECEIVED_CALLS);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyReceivedCallsIsInShouldWork() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyReceivedCalls in DEFAULT_TOTAL_DAILY_RECEIVED_CALLS or UPDATED_TOTAL_DAILY_RECEIVED_CALLS
        defaultDailyCallsShouldBeFound(
            "totalDailyReceivedCalls.in=" + DEFAULT_TOTAL_DAILY_RECEIVED_CALLS + "," + UPDATED_TOTAL_DAILY_RECEIVED_CALLS
        );

        // Get all the dailyCallsList where totalDailyReceivedCalls equals to UPDATED_TOTAL_DAILY_RECEIVED_CALLS
        defaultDailyCallsShouldNotBeFound("totalDailyReceivedCalls.in=" + UPDATED_TOTAL_DAILY_RECEIVED_CALLS);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyReceivedCallsIsNullOrNotNull() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyReceivedCalls is not null
        defaultDailyCallsShouldBeFound("totalDailyReceivedCalls.specified=true");

        // Get all the dailyCallsList where totalDailyReceivedCalls is null
        defaultDailyCallsShouldNotBeFound("totalDailyReceivedCalls.specified=false");
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyReceivedCallsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyReceivedCalls is greater than or equal to DEFAULT_TOTAL_DAILY_RECEIVED_CALLS
        defaultDailyCallsShouldBeFound("totalDailyReceivedCalls.greaterThanOrEqual=" + DEFAULT_TOTAL_DAILY_RECEIVED_CALLS);

        // Get all the dailyCallsList where totalDailyReceivedCalls is greater than or equal to UPDATED_TOTAL_DAILY_RECEIVED_CALLS
        defaultDailyCallsShouldNotBeFound("totalDailyReceivedCalls.greaterThanOrEqual=" + UPDATED_TOTAL_DAILY_RECEIVED_CALLS);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyReceivedCallsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyReceivedCalls is less than or equal to DEFAULT_TOTAL_DAILY_RECEIVED_CALLS
        defaultDailyCallsShouldBeFound("totalDailyReceivedCalls.lessThanOrEqual=" + DEFAULT_TOTAL_DAILY_RECEIVED_CALLS);

        // Get all the dailyCallsList where totalDailyReceivedCalls is less than or equal to SMALLER_TOTAL_DAILY_RECEIVED_CALLS
        defaultDailyCallsShouldNotBeFound("totalDailyReceivedCalls.lessThanOrEqual=" + SMALLER_TOTAL_DAILY_RECEIVED_CALLS);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyReceivedCallsIsLessThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyReceivedCalls is less than DEFAULT_TOTAL_DAILY_RECEIVED_CALLS
        defaultDailyCallsShouldNotBeFound("totalDailyReceivedCalls.lessThan=" + DEFAULT_TOTAL_DAILY_RECEIVED_CALLS);

        // Get all the dailyCallsList where totalDailyReceivedCalls is less than UPDATED_TOTAL_DAILY_RECEIVED_CALLS
        defaultDailyCallsShouldBeFound("totalDailyReceivedCalls.lessThan=" + UPDATED_TOTAL_DAILY_RECEIVED_CALLS);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyReceivedCallsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyReceivedCalls is greater than DEFAULT_TOTAL_DAILY_RECEIVED_CALLS
        defaultDailyCallsShouldNotBeFound("totalDailyReceivedCalls.greaterThan=" + DEFAULT_TOTAL_DAILY_RECEIVED_CALLS);

        // Get all the dailyCallsList where totalDailyReceivedCalls is greater than SMALLER_TOTAL_DAILY_RECEIVED_CALLS
        defaultDailyCallsShouldBeFound("totalDailyReceivedCalls.greaterThan=" + SMALLER_TOTAL_DAILY_RECEIVED_CALLS);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyAttendedCallsIsEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyAttendedCalls equals to DEFAULT_TOTAL_DAILY_ATTENDED_CALLS
        defaultDailyCallsShouldBeFound("totalDailyAttendedCalls.equals=" + DEFAULT_TOTAL_DAILY_ATTENDED_CALLS);

        // Get all the dailyCallsList where totalDailyAttendedCalls equals to UPDATED_TOTAL_DAILY_ATTENDED_CALLS
        defaultDailyCallsShouldNotBeFound("totalDailyAttendedCalls.equals=" + UPDATED_TOTAL_DAILY_ATTENDED_CALLS);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyAttendedCallsIsInShouldWork() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyAttendedCalls in DEFAULT_TOTAL_DAILY_ATTENDED_CALLS or UPDATED_TOTAL_DAILY_ATTENDED_CALLS
        defaultDailyCallsShouldBeFound(
            "totalDailyAttendedCalls.in=" + DEFAULT_TOTAL_DAILY_ATTENDED_CALLS + "," + UPDATED_TOTAL_DAILY_ATTENDED_CALLS
        );

        // Get all the dailyCallsList where totalDailyAttendedCalls equals to UPDATED_TOTAL_DAILY_ATTENDED_CALLS
        defaultDailyCallsShouldNotBeFound("totalDailyAttendedCalls.in=" + UPDATED_TOTAL_DAILY_ATTENDED_CALLS);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyAttendedCallsIsNullOrNotNull() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyAttendedCalls is not null
        defaultDailyCallsShouldBeFound("totalDailyAttendedCalls.specified=true");

        // Get all the dailyCallsList where totalDailyAttendedCalls is null
        defaultDailyCallsShouldNotBeFound("totalDailyAttendedCalls.specified=false");
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyAttendedCallsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyAttendedCalls is greater than or equal to DEFAULT_TOTAL_DAILY_ATTENDED_CALLS
        defaultDailyCallsShouldBeFound("totalDailyAttendedCalls.greaterThanOrEqual=" + DEFAULT_TOTAL_DAILY_ATTENDED_CALLS);

        // Get all the dailyCallsList where totalDailyAttendedCalls is greater than or equal to UPDATED_TOTAL_DAILY_ATTENDED_CALLS
        defaultDailyCallsShouldNotBeFound("totalDailyAttendedCalls.greaterThanOrEqual=" + UPDATED_TOTAL_DAILY_ATTENDED_CALLS);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyAttendedCallsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyAttendedCalls is less than or equal to DEFAULT_TOTAL_DAILY_ATTENDED_CALLS
        defaultDailyCallsShouldBeFound("totalDailyAttendedCalls.lessThanOrEqual=" + DEFAULT_TOTAL_DAILY_ATTENDED_CALLS);

        // Get all the dailyCallsList where totalDailyAttendedCalls is less than or equal to SMALLER_TOTAL_DAILY_ATTENDED_CALLS
        defaultDailyCallsShouldNotBeFound("totalDailyAttendedCalls.lessThanOrEqual=" + SMALLER_TOTAL_DAILY_ATTENDED_CALLS);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyAttendedCallsIsLessThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyAttendedCalls is less than DEFAULT_TOTAL_DAILY_ATTENDED_CALLS
        defaultDailyCallsShouldNotBeFound("totalDailyAttendedCalls.lessThan=" + DEFAULT_TOTAL_DAILY_ATTENDED_CALLS);

        // Get all the dailyCallsList where totalDailyAttendedCalls is less than UPDATED_TOTAL_DAILY_ATTENDED_CALLS
        defaultDailyCallsShouldBeFound("totalDailyAttendedCalls.lessThan=" + UPDATED_TOTAL_DAILY_ATTENDED_CALLS);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyAttendedCallsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyAttendedCalls is greater than DEFAULT_TOTAL_DAILY_ATTENDED_CALLS
        defaultDailyCallsShouldNotBeFound("totalDailyAttendedCalls.greaterThan=" + DEFAULT_TOTAL_DAILY_ATTENDED_CALLS);

        // Get all the dailyCallsList where totalDailyAttendedCalls is greater than SMALLER_TOTAL_DAILY_ATTENDED_CALLS
        defaultDailyCallsShouldBeFound("totalDailyAttendedCalls.greaterThan=" + SMALLER_TOTAL_DAILY_ATTENDED_CALLS);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyMissedCallsIsEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyMissedCalls equals to DEFAULT_TOTAL_DAILY_MISSED_CALLS
        defaultDailyCallsShouldBeFound("totalDailyMissedCalls.equals=" + DEFAULT_TOTAL_DAILY_MISSED_CALLS);

        // Get all the dailyCallsList where totalDailyMissedCalls equals to UPDATED_TOTAL_DAILY_MISSED_CALLS
        defaultDailyCallsShouldNotBeFound("totalDailyMissedCalls.equals=" + UPDATED_TOTAL_DAILY_MISSED_CALLS);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyMissedCallsIsInShouldWork() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyMissedCalls in DEFAULT_TOTAL_DAILY_MISSED_CALLS or UPDATED_TOTAL_DAILY_MISSED_CALLS
        defaultDailyCallsShouldBeFound(
            "totalDailyMissedCalls.in=" + DEFAULT_TOTAL_DAILY_MISSED_CALLS + "," + UPDATED_TOTAL_DAILY_MISSED_CALLS
        );

        // Get all the dailyCallsList where totalDailyMissedCalls equals to UPDATED_TOTAL_DAILY_MISSED_CALLS
        defaultDailyCallsShouldNotBeFound("totalDailyMissedCalls.in=" + UPDATED_TOTAL_DAILY_MISSED_CALLS);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyMissedCallsIsNullOrNotNull() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyMissedCalls is not null
        defaultDailyCallsShouldBeFound("totalDailyMissedCalls.specified=true");

        // Get all the dailyCallsList where totalDailyMissedCalls is null
        defaultDailyCallsShouldNotBeFound("totalDailyMissedCalls.specified=false");
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyMissedCallsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyMissedCalls is greater than or equal to DEFAULT_TOTAL_DAILY_MISSED_CALLS
        defaultDailyCallsShouldBeFound("totalDailyMissedCalls.greaterThanOrEqual=" + DEFAULT_TOTAL_DAILY_MISSED_CALLS);

        // Get all the dailyCallsList where totalDailyMissedCalls is greater than or equal to UPDATED_TOTAL_DAILY_MISSED_CALLS
        defaultDailyCallsShouldNotBeFound("totalDailyMissedCalls.greaterThanOrEqual=" + UPDATED_TOTAL_DAILY_MISSED_CALLS);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyMissedCallsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyMissedCalls is less than or equal to DEFAULT_TOTAL_DAILY_MISSED_CALLS
        defaultDailyCallsShouldBeFound("totalDailyMissedCalls.lessThanOrEqual=" + DEFAULT_TOTAL_DAILY_MISSED_CALLS);

        // Get all the dailyCallsList where totalDailyMissedCalls is less than or equal to SMALLER_TOTAL_DAILY_MISSED_CALLS
        defaultDailyCallsShouldNotBeFound("totalDailyMissedCalls.lessThanOrEqual=" + SMALLER_TOTAL_DAILY_MISSED_CALLS);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyMissedCallsIsLessThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyMissedCalls is less than DEFAULT_TOTAL_DAILY_MISSED_CALLS
        defaultDailyCallsShouldNotBeFound("totalDailyMissedCalls.lessThan=" + DEFAULT_TOTAL_DAILY_MISSED_CALLS);

        // Get all the dailyCallsList where totalDailyMissedCalls is less than UPDATED_TOTAL_DAILY_MISSED_CALLS
        defaultDailyCallsShouldBeFound("totalDailyMissedCalls.lessThan=" + UPDATED_TOTAL_DAILY_MISSED_CALLS);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyMissedCallsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyMissedCalls is greater than DEFAULT_TOTAL_DAILY_MISSED_CALLS
        defaultDailyCallsShouldNotBeFound("totalDailyMissedCalls.greaterThan=" + DEFAULT_TOTAL_DAILY_MISSED_CALLS);

        // Get all the dailyCallsList where totalDailyMissedCalls is greater than SMALLER_TOTAL_DAILY_MISSED_CALLS
        defaultDailyCallsShouldBeFound("totalDailyMissedCalls.greaterThan=" + SMALLER_TOTAL_DAILY_MISSED_CALLS);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyReceivedCallsExternalAgentIsEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyReceivedCallsExternalAgent equals to DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT
        defaultDailyCallsShouldBeFound("totalDailyReceivedCallsExternalAgent.equals=" + DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT);

        // Get all the dailyCallsList where totalDailyReceivedCallsExternalAgent equals to UPDATED_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT
        defaultDailyCallsShouldNotBeFound(
            "totalDailyReceivedCallsExternalAgent.equals=" + UPDATED_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyReceivedCallsExternalAgentIsInShouldWork() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyReceivedCallsExternalAgent in DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT or UPDATED_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT
        defaultDailyCallsShouldBeFound(
            "totalDailyReceivedCallsExternalAgent.in=" +
            DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT +
            "," +
            UPDATED_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT
        );

        // Get all the dailyCallsList where totalDailyReceivedCallsExternalAgent equals to UPDATED_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT
        defaultDailyCallsShouldNotBeFound("totalDailyReceivedCallsExternalAgent.in=" + UPDATED_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyReceivedCallsExternalAgentIsNullOrNotNull() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyReceivedCallsExternalAgent is not null
        defaultDailyCallsShouldBeFound("totalDailyReceivedCallsExternalAgent.specified=true");

        // Get all the dailyCallsList where totalDailyReceivedCallsExternalAgent is null
        defaultDailyCallsShouldNotBeFound("totalDailyReceivedCallsExternalAgent.specified=false");
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyReceivedCallsExternalAgentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyReceivedCallsExternalAgent is greater than or equal to DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT
        defaultDailyCallsShouldBeFound(
            "totalDailyReceivedCallsExternalAgent.greaterThanOrEqual=" + DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT
        );

        // Get all the dailyCallsList where totalDailyReceivedCallsExternalAgent is greater than or equal to UPDATED_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT
        defaultDailyCallsShouldNotBeFound(
            "totalDailyReceivedCallsExternalAgent.greaterThanOrEqual=" + UPDATED_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyReceivedCallsExternalAgentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyReceivedCallsExternalAgent is less than or equal to DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT
        defaultDailyCallsShouldBeFound(
            "totalDailyReceivedCallsExternalAgent.lessThanOrEqual=" + DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT
        );

        // Get all the dailyCallsList where totalDailyReceivedCallsExternalAgent is less than or equal to SMALLER_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT
        defaultDailyCallsShouldNotBeFound(
            "totalDailyReceivedCallsExternalAgent.lessThanOrEqual=" + SMALLER_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyReceivedCallsExternalAgentIsLessThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyReceivedCallsExternalAgent is less than DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT
        defaultDailyCallsShouldNotBeFound(
            "totalDailyReceivedCallsExternalAgent.lessThan=" + DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT
        );

        // Get all the dailyCallsList where totalDailyReceivedCallsExternalAgent is less than UPDATED_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT
        defaultDailyCallsShouldBeFound(
            "totalDailyReceivedCallsExternalAgent.lessThan=" + UPDATED_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyReceivedCallsExternalAgentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyReceivedCallsExternalAgent is greater than DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT
        defaultDailyCallsShouldNotBeFound(
            "totalDailyReceivedCallsExternalAgent.greaterThan=" + DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT
        );

        // Get all the dailyCallsList where totalDailyReceivedCallsExternalAgent is greater than SMALLER_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT
        defaultDailyCallsShouldBeFound(
            "totalDailyReceivedCallsExternalAgent.greaterThan=" + SMALLER_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyAttendedCallsExternalAgentIsEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyAttendedCallsExternalAgent equals to DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT
        defaultDailyCallsShouldBeFound("totalDailyAttendedCallsExternalAgent.equals=" + DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT);

        // Get all the dailyCallsList where totalDailyAttendedCallsExternalAgent equals to UPDATED_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT
        defaultDailyCallsShouldNotBeFound(
            "totalDailyAttendedCallsExternalAgent.equals=" + UPDATED_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyAttendedCallsExternalAgentIsInShouldWork() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyAttendedCallsExternalAgent in DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT or UPDATED_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT
        defaultDailyCallsShouldBeFound(
            "totalDailyAttendedCallsExternalAgent.in=" +
            DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT +
            "," +
            UPDATED_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT
        );

        // Get all the dailyCallsList where totalDailyAttendedCallsExternalAgent equals to UPDATED_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT
        defaultDailyCallsShouldNotBeFound("totalDailyAttendedCallsExternalAgent.in=" + UPDATED_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyAttendedCallsExternalAgentIsNullOrNotNull() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyAttendedCallsExternalAgent is not null
        defaultDailyCallsShouldBeFound("totalDailyAttendedCallsExternalAgent.specified=true");

        // Get all the dailyCallsList where totalDailyAttendedCallsExternalAgent is null
        defaultDailyCallsShouldNotBeFound("totalDailyAttendedCallsExternalAgent.specified=false");
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyAttendedCallsExternalAgentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyAttendedCallsExternalAgent is greater than or equal to DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT
        defaultDailyCallsShouldBeFound(
            "totalDailyAttendedCallsExternalAgent.greaterThanOrEqual=" + DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT
        );

        // Get all the dailyCallsList where totalDailyAttendedCallsExternalAgent is greater than or equal to UPDATED_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT
        defaultDailyCallsShouldNotBeFound(
            "totalDailyAttendedCallsExternalAgent.greaterThanOrEqual=" + UPDATED_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyAttendedCallsExternalAgentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyAttendedCallsExternalAgent is less than or equal to DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT
        defaultDailyCallsShouldBeFound(
            "totalDailyAttendedCallsExternalAgent.lessThanOrEqual=" + DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT
        );

        // Get all the dailyCallsList where totalDailyAttendedCallsExternalAgent is less than or equal to SMALLER_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT
        defaultDailyCallsShouldNotBeFound(
            "totalDailyAttendedCallsExternalAgent.lessThanOrEqual=" + SMALLER_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyAttendedCallsExternalAgentIsLessThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyAttendedCallsExternalAgent is less than DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT
        defaultDailyCallsShouldNotBeFound(
            "totalDailyAttendedCallsExternalAgent.lessThan=" + DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT
        );

        // Get all the dailyCallsList where totalDailyAttendedCallsExternalAgent is less than UPDATED_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT
        defaultDailyCallsShouldBeFound(
            "totalDailyAttendedCallsExternalAgent.lessThan=" + UPDATED_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyAttendedCallsExternalAgentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyAttendedCallsExternalAgent is greater than DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT
        defaultDailyCallsShouldNotBeFound(
            "totalDailyAttendedCallsExternalAgent.greaterThan=" + DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT
        );

        // Get all the dailyCallsList where totalDailyAttendedCallsExternalAgent is greater than SMALLER_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT
        defaultDailyCallsShouldBeFound(
            "totalDailyAttendedCallsExternalAgent.greaterThan=" + SMALLER_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyReceivedCallsInternalAgentIsEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyReceivedCallsInternalAgent equals to DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT
        defaultDailyCallsShouldBeFound("totalDailyReceivedCallsInternalAgent.equals=" + DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT);

        // Get all the dailyCallsList where totalDailyReceivedCallsInternalAgent equals to UPDATED_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT
        defaultDailyCallsShouldNotBeFound(
            "totalDailyReceivedCallsInternalAgent.equals=" + UPDATED_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyReceivedCallsInternalAgentIsInShouldWork() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyReceivedCallsInternalAgent in DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT or UPDATED_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT
        defaultDailyCallsShouldBeFound(
            "totalDailyReceivedCallsInternalAgent.in=" +
            DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT +
            "," +
            UPDATED_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT
        );

        // Get all the dailyCallsList where totalDailyReceivedCallsInternalAgent equals to UPDATED_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT
        defaultDailyCallsShouldNotBeFound("totalDailyReceivedCallsInternalAgent.in=" + UPDATED_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyReceivedCallsInternalAgentIsNullOrNotNull() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyReceivedCallsInternalAgent is not null
        defaultDailyCallsShouldBeFound("totalDailyReceivedCallsInternalAgent.specified=true");

        // Get all the dailyCallsList where totalDailyReceivedCallsInternalAgent is null
        defaultDailyCallsShouldNotBeFound("totalDailyReceivedCallsInternalAgent.specified=false");
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyReceivedCallsInternalAgentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyReceivedCallsInternalAgent is greater than or equal to DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT
        defaultDailyCallsShouldBeFound(
            "totalDailyReceivedCallsInternalAgent.greaterThanOrEqual=" + DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT
        );

        // Get all the dailyCallsList where totalDailyReceivedCallsInternalAgent is greater than or equal to UPDATED_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT
        defaultDailyCallsShouldNotBeFound(
            "totalDailyReceivedCallsInternalAgent.greaterThanOrEqual=" + UPDATED_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyReceivedCallsInternalAgentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyReceivedCallsInternalAgent is less than or equal to DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT
        defaultDailyCallsShouldBeFound(
            "totalDailyReceivedCallsInternalAgent.lessThanOrEqual=" + DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT
        );

        // Get all the dailyCallsList where totalDailyReceivedCallsInternalAgent is less than or equal to SMALLER_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT
        defaultDailyCallsShouldNotBeFound(
            "totalDailyReceivedCallsInternalAgent.lessThanOrEqual=" + SMALLER_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyReceivedCallsInternalAgentIsLessThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyReceivedCallsInternalAgent is less than DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT
        defaultDailyCallsShouldNotBeFound(
            "totalDailyReceivedCallsInternalAgent.lessThan=" + DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT
        );

        // Get all the dailyCallsList where totalDailyReceivedCallsInternalAgent is less than UPDATED_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT
        defaultDailyCallsShouldBeFound(
            "totalDailyReceivedCallsInternalAgent.lessThan=" + UPDATED_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyReceivedCallsInternalAgentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyReceivedCallsInternalAgent is greater than DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT
        defaultDailyCallsShouldNotBeFound(
            "totalDailyReceivedCallsInternalAgent.greaterThan=" + DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT
        );

        // Get all the dailyCallsList where totalDailyReceivedCallsInternalAgent is greater than SMALLER_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT
        defaultDailyCallsShouldBeFound(
            "totalDailyReceivedCallsInternalAgent.greaterThan=" + SMALLER_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyAttendedCallsInternalAgentIsEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyAttendedCallsInternalAgent equals to DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT
        defaultDailyCallsShouldBeFound("totalDailyAttendedCallsInternalAgent.equals=" + DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT);

        // Get all the dailyCallsList where totalDailyAttendedCallsInternalAgent equals to UPDATED_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT
        defaultDailyCallsShouldNotBeFound(
            "totalDailyAttendedCallsInternalAgent.equals=" + UPDATED_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyAttendedCallsInternalAgentIsInShouldWork() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyAttendedCallsInternalAgent in DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT or UPDATED_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT
        defaultDailyCallsShouldBeFound(
            "totalDailyAttendedCallsInternalAgent.in=" +
            DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT +
            "," +
            UPDATED_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT
        );

        // Get all the dailyCallsList where totalDailyAttendedCallsInternalAgent equals to UPDATED_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT
        defaultDailyCallsShouldNotBeFound("totalDailyAttendedCallsInternalAgent.in=" + UPDATED_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyAttendedCallsInternalAgentIsNullOrNotNull() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyAttendedCallsInternalAgent is not null
        defaultDailyCallsShouldBeFound("totalDailyAttendedCallsInternalAgent.specified=true");

        // Get all the dailyCallsList where totalDailyAttendedCallsInternalAgent is null
        defaultDailyCallsShouldNotBeFound("totalDailyAttendedCallsInternalAgent.specified=false");
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyAttendedCallsInternalAgentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyAttendedCallsInternalAgent is greater than or equal to DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT
        defaultDailyCallsShouldBeFound(
            "totalDailyAttendedCallsInternalAgent.greaterThanOrEqual=" + DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT
        );

        // Get all the dailyCallsList where totalDailyAttendedCallsInternalAgent is greater than or equal to UPDATED_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT
        defaultDailyCallsShouldNotBeFound(
            "totalDailyAttendedCallsInternalAgent.greaterThanOrEqual=" + UPDATED_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyAttendedCallsInternalAgentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyAttendedCallsInternalAgent is less than or equal to DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT
        defaultDailyCallsShouldBeFound(
            "totalDailyAttendedCallsInternalAgent.lessThanOrEqual=" + DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT
        );

        // Get all the dailyCallsList where totalDailyAttendedCallsInternalAgent is less than or equal to SMALLER_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT
        defaultDailyCallsShouldNotBeFound(
            "totalDailyAttendedCallsInternalAgent.lessThanOrEqual=" + SMALLER_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyAttendedCallsInternalAgentIsLessThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyAttendedCallsInternalAgent is less than DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT
        defaultDailyCallsShouldNotBeFound(
            "totalDailyAttendedCallsInternalAgent.lessThan=" + DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT
        );

        // Get all the dailyCallsList where totalDailyAttendedCallsInternalAgent is less than UPDATED_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT
        defaultDailyCallsShouldBeFound(
            "totalDailyAttendedCallsInternalAgent.lessThan=" + UPDATED_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyAttendedCallsInternalAgentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyAttendedCallsInternalAgent is greater than DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT
        defaultDailyCallsShouldNotBeFound(
            "totalDailyAttendedCallsInternalAgent.greaterThan=" + DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT
        );

        // Get all the dailyCallsList where totalDailyAttendedCallsInternalAgent is greater than SMALLER_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT
        defaultDailyCallsShouldBeFound(
            "totalDailyAttendedCallsInternalAgent.greaterThan=" + SMALLER_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyCallsTimeInMinIsEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyCallsTimeInMin equals to DEFAULT_TOTAL_DAILY_CALLS_TIME_IN_MIN
        defaultDailyCallsShouldBeFound("totalDailyCallsTimeInMin.equals=" + DEFAULT_TOTAL_DAILY_CALLS_TIME_IN_MIN);

        // Get all the dailyCallsList where totalDailyCallsTimeInMin equals to UPDATED_TOTAL_DAILY_CALLS_TIME_IN_MIN
        defaultDailyCallsShouldNotBeFound("totalDailyCallsTimeInMin.equals=" + UPDATED_TOTAL_DAILY_CALLS_TIME_IN_MIN);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyCallsTimeInMinIsInShouldWork() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyCallsTimeInMin in DEFAULT_TOTAL_DAILY_CALLS_TIME_IN_MIN or UPDATED_TOTAL_DAILY_CALLS_TIME_IN_MIN
        defaultDailyCallsShouldBeFound(
            "totalDailyCallsTimeInMin.in=" + DEFAULT_TOTAL_DAILY_CALLS_TIME_IN_MIN + "," + UPDATED_TOTAL_DAILY_CALLS_TIME_IN_MIN
        );

        // Get all the dailyCallsList where totalDailyCallsTimeInMin equals to UPDATED_TOTAL_DAILY_CALLS_TIME_IN_MIN
        defaultDailyCallsShouldNotBeFound("totalDailyCallsTimeInMin.in=" + UPDATED_TOTAL_DAILY_CALLS_TIME_IN_MIN);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyCallsTimeInMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyCallsTimeInMin is not null
        defaultDailyCallsShouldBeFound("totalDailyCallsTimeInMin.specified=true");

        // Get all the dailyCallsList where totalDailyCallsTimeInMin is null
        defaultDailyCallsShouldNotBeFound("totalDailyCallsTimeInMin.specified=false");
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyCallsTimeInMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyCallsTimeInMin is greater than or equal to DEFAULT_TOTAL_DAILY_CALLS_TIME_IN_MIN
        defaultDailyCallsShouldBeFound("totalDailyCallsTimeInMin.greaterThanOrEqual=" + DEFAULT_TOTAL_DAILY_CALLS_TIME_IN_MIN);

        // Get all the dailyCallsList where totalDailyCallsTimeInMin is greater than or equal to UPDATED_TOTAL_DAILY_CALLS_TIME_IN_MIN
        defaultDailyCallsShouldNotBeFound("totalDailyCallsTimeInMin.greaterThanOrEqual=" + UPDATED_TOTAL_DAILY_CALLS_TIME_IN_MIN);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyCallsTimeInMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyCallsTimeInMin is less than or equal to DEFAULT_TOTAL_DAILY_CALLS_TIME_IN_MIN
        defaultDailyCallsShouldBeFound("totalDailyCallsTimeInMin.lessThanOrEqual=" + DEFAULT_TOTAL_DAILY_CALLS_TIME_IN_MIN);

        // Get all the dailyCallsList where totalDailyCallsTimeInMin is less than or equal to SMALLER_TOTAL_DAILY_CALLS_TIME_IN_MIN
        defaultDailyCallsShouldNotBeFound("totalDailyCallsTimeInMin.lessThanOrEqual=" + SMALLER_TOTAL_DAILY_CALLS_TIME_IN_MIN);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyCallsTimeInMinIsLessThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyCallsTimeInMin is less than DEFAULT_TOTAL_DAILY_CALLS_TIME_IN_MIN
        defaultDailyCallsShouldNotBeFound("totalDailyCallsTimeInMin.lessThan=" + DEFAULT_TOTAL_DAILY_CALLS_TIME_IN_MIN);

        // Get all the dailyCallsList where totalDailyCallsTimeInMin is less than UPDATED_TOTAL_DAILY_CALLS_TIME_IN_MIN
        defaultDailyCallsShouldBeFound("totalDailyCallsTimeInMin.lessThan=" + UPDATED_TOTAL_DAILY_CALLS_TIME_IN_MIN);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyCallsTimeInMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyCallsTimeInMin is greater than DEFAULT_TOTAL_DAILY_CALLS_TIME_IN_MIN
        defaultDailyCallsShouldNotBeFound("totalDailyCallsTimeInMin.greaterThan=" + DEFAULT_TOTAL_DAILY_CALLS_TIME_IN_MIN);

        // Get all the dailyCallsList where totalDailyCallsTimeInMin is greater than SMALLER_TOTAL_DAILY_CALLS_TIME_IN_MIN
        defaultDailyCallsShouldBeFound("totalDailyCallsTimeInMin.greaterThan=" + SMALLER_TOTAL_DAILY_CALLS_TIME_IN_MIN);
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyCallsTimeExternalAgentInMinIsEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyCallsTimeExternalAgentInMin equals to DEFAULT_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldBeFound(
            "totalDailyCallsTimeExternalAgentInMin.equals=" + DEFAULT_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN
        );

        // Get all the dailyCallsList where totalDailyCallsTimeExternalAgentInMin equals to UPDATED_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldNotBeFound(
            "totalDailyCallsTimeExternalAgentInMin.equals=" + UPDATED_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyCallsTimeExternalAgentInMinIsInShouldWork() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyCallsTimeExternalAgentInMin in DEFAULT_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN or UPDATED_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldBeFound(
            "totalDailyCallsTimeExternalAgentInMin.in=" +
            DEFAULT_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN +
            "," +
            UPDATED_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN
        );

        // Get all the dailyCallsList where totalDailyCallsTimeExternalAgentInMin equals to UPDATED_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldNotBeFound(
            "totalDailyCallsTimeExternalAgentInMin.in=" + UPDATED_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyCallsTimeExternalAgentInMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyCallsTimeExternalAgentInMin is not null
        defaultDailyCallsShouldBeFound("totalDailyCallsTimeExternalAgentInMin.specified=true");

        // Get all the dailyCallsList where totalDailyCallsTimeExternalAgentInMin is null
        defaultDailyCallsShouldNotBeFound("totalDailyCallsTimeExternalAgentInMin.specified=false");
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyCallsTimeExternalAgentInMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyCallsTimeExternalAgentInMin is greater than or equal to DEFAULT_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldBeFound(
            "totalDailyCallsTimeExternalAgentInMin.greaterThanOrEqual=" + DEFAULT_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN
        );

        // Get all the dailyCallsList where totalDailyCallsTimeExternalAgentInMin is greater than or equal to UPDATED_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldNotBeFound(
            "totalDailyCallsTimeExternalAgentInMin.greaterThanOrEqual=" + UPDATED_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyCallsTimeExternalAgentInMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyCallsTimeExternalAgentInMin is less than or equal to DEFAULT_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldBeFound(
            "totalDailyCallsTimeExternalAgentInMin.lessThanOrEqual=" + DEFAULT_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN
        );

        // Get all the dailyCallsList where totalDailyCallsTimeExternalAgentInMin is less than or equal to SMALLER_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldNotBeFound(
            "totalDailyCallsTimeExternalAgentInMin.lessThanOrEqual=" + SMALLER_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyCallsTimeExternalAgentInMinIsLessThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyCallsTimeExternalAgentInMin is less than DEFAULT_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldNotBeFound(
            "totalDailyCallsTimeExternalAgentInMin.lessThan=" + DEFAULT_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN
        );

        // Get all the dailyCallsList where totalDailyCallsTimeExternalAgentInMin is less than UPDATED_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldBeFound(
            "totalDailyCallsTimeExternalAgentInMin.lessThan=" + UPDATED_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyCallsTimeExternalAgentInMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyCallsTimeExternalAgentInMin is greater than DEFAULT_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldNotBeFound(
            "totalDailyCallsTimeExternalAgentInMin.greaterThan=" + DEFAULT_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN
        );

        // Get all the dailyCallsList where totalDailyCallsTimeExternalAgentInMin is greater than SMALLER_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldBeFound(
            "totalDailyCallsTimeExternalAgentInMin.greaterThan=" + SMALLER_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyCallsTimeInternalAgentInMinIsEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyCallsTimeInternalAgentInMin equals to DEFAULT_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldBeFound(
            "totalDailyCallsTimeInternalAgentInMin.equals=" + DEFAULT_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN
        );

        // Get all the dailyCallsList where totalDailyCallsTimeInternalAgentInMin equals to UPDATED_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldNotBeFound(
            "totalDailyCallsTimeInternalAgentInMin.equals=" + UPDATED_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyCallsTimeInternalAgentInMinIsInShouldWork() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyCallsTimeInternalAgentInMin in DEFAULT_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN or UPDATED_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldBeFound(
            "totalDailyCallsTimeInternalAgentInMin.in=" +
            DEFAULT_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN +
            "," +
            UPDATED_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN
        );

        // Get all the dailyCallsList where totalDailyCallsTimeInternalAgentInMin equals to UPDATED_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldNotBeFound(
            "totalDailyCallsTimeInternalAgentInMin.in=" + UPDATED_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyCallsTimeInternalAgentInMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyCallsTimeInternalAgentInMin is not null
        defaultDailyCallsShouldBeFound("totalDailyCallsTimeInternalAgentInMin.specified=true");

        // Get all the dailyCallsList where totalDailyCallsTimeInternalAgentInMin is null
        defaultDailyCallsShouldNotBeFound("totalDailyCallsTimeInternalAgentInMin.specified=false");
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyCallsTimeInternalAgentInMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyCallsTimeInternalAgentInMin is greater than or equal to DEFAULT_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldBeFound(
            "totalDailyCallsTimeInternalAgentInMin.greaterThanOrEqual=" + DEFAULT_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN
        );

        // Get all the dailyCallsList where totalDailyCallsTimeInternalAgentInMin is greater than or equal to UPDATED_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldNotBeFound(
            "totalDailyCallsTimeInternalAgentInMin.greaterThanOrEqual=" + UPDATED_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyCallsTimeInternalAgentInMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyCallsTimeInternalAgentInMin is less than or equal to DEFAULT_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldBeFound(
            "totalDailyCallsTimeInternalAgentInMin.lessThanOrEqual=" + DEFAULT_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN
        );

        // Get all the dailyCallsList where totalDailyCallsTimeInternalAgentInMin is less than or equal to SMALLER_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldNotBeFound(
            "totalDailyCallsTimeInternalAgentInMin.lessThanOrEqual=" + SMALLER_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyCallsTimeInternalAgentInMinIsLessThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyCallsTimeInternalAgentInMin is less than DEFAULT_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldNotBeFound(
            "totalDailyCallsTimeInternalAgentInMin.lessThan=" + DEFAULT_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN
        );

        // Get all the dailyCallsList where totalDailyCallsTimeInternalAgentInMin is less than UPDATED_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldBeFound(
            "totalDailyCallsTimeInternalAgentInMin.lessThan=" + UPDATED_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByTotalDailyCallsTimeInternalAgentInMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where totalDailyCallsTimeInternalAgentInMin is greater than DEFAULT_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldNotBeFound(
            "totalDailyCallsTimeInternalAgentInMin.greaterThan=" + DEFAULT_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN
        );

        // Get all the dailyCallsList where totalDailyCallsTimeInternalAgentInMin is greater than SMALLER_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldBeFound(
            "totalDailyCallsTimeInternalAgentInMin.greaterThan=" + SMALLER_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByAvgDailyOperationTimeExternalAgentInMinIsEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where avgDailyOperationTimeExternalAgentInMin equals to DEFAULT_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldBeFound(
            "avgDailyOperationTimeExternalAgentInMin.equals=" + DEFAULT_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN
        );

        // Get all the dailyCallsList where avgDailyOperationTimeExternalAgentInMin equals to UPDATED_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldNotBeFound(
            "avgDailyOperationTimeExternalAgentInMin.equals=" + UPDATED_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByAvgDailyOperationTimeExternalAgentInMinIsInShouldWork() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where avgDailyOperationTimeExternalAgentInMin in DEFAULT_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN or UPDATED_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldBeFound(
            "avgDailyOperationTimeExternalAgentInMin.in=" +
            DEFAULT_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN +
            "," +
            UPDATED_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN
        );

        // Get all the dailyCallsList where avgDailyOperationTimeExternalAgentInMin equals to UPDATED_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldNotBeFound(
            "avgDailyOperationTimeExternalAgentInMin.in=" + UPDATED_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByAvgDailyOperationTimeExternalAgentInMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where avgDailyOperationTimeExternalAgentInMin is not null
        defaultDailyCallsShouldBeFound("avgDailyOperationTimeExternalAgentInMin.specified=true");

        // Get all the dailyCallsList where avgDailyOperationTimeExternalAgentInMin is null
        defaultDailyCallsShouldNotBeFound("avgDailyOperationTimeExternalAgentInMin.specified=false");
    }

    @Test
    @Transactional
    void getAllDailyCallsByAvgDailyOperationTimeExternalAgentInMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where avgDailyOperationTimeExternalAgentInMin is greater than or equal to DEFAULT_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldBeFound(
            "avgDailyOperationTimeExternalAgentInMin.greaterThanOrEqual=" + DEFAULT_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN
        );

        // Get all the dailyCallsList where avgDailyOperationTimeExternalAgentInMin is greater than or equal to UPDATED_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldNotBeFound(
            "avgDailyOperationTimeExternalAgentInMin.greaterThanOrEqual=" + UPDATED_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByAvgDailyOperationTimeExternalAgentInMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where avgDailyOperationTimeExternalAgentInMin is less than or equal to DEFAULT_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldBeFound(
            "avgDailyOperationTimeExternalAgentInMin.lessThanOrEqual=" + DEFAULT_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN
        );

        // Get all the dailyCallsList where avgDailyOperationTimeExternalAgentInMin is less than or equal to SMALLER_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldNotBeFound(
            "avgDailyOperationTimeExternalAgentInMin.lessThanOrEqual=" + SMALLER_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByAvgDailyOperationTimeExternalAgentInMinIsLessThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where avgDailyOperationTimeExternalAgentInMin is less than DEFAULT_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldNotBeFound(
            "avgDailyOperationTimeExternalAgentInMin.lessThan=" + DEFAULT_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN
        );

        // Get all the dailyCallsList where avgDailyOperationTimeExternalAgentInMin is less than UPDATED_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldBeFound(
            "avgDailyOperationTimeExternalAgentInMin.lessThan=" + UPDATED_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByAvgDailyOperationTimeExternalAgentInMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where avgDailyOperationTimeExternalAgentInMin is greater than DEFAULT_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldNotBeFound(
            "avgDailyOperationTimeExternalAgentInMin.greaterThan=" + DEFAULT_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN
        );

        // Get all the dailyCallsList where avgDailyOperationTimeExternalAgentInMin is greater than SMALLER_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldBeFound(
            "avgDailyOperationTimeExternalAgentInMin.greaterThan=" + SMALLER_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByAvgDailyOperationTimeInternalAgentInMinIsEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where avgDailyOperationTimeInternalAgentInMin equals to DEFAULT_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldBeFound(
            "avgDailyOperationTimeInternalAgentInMin.equals=" + DEFAULT_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN
        );

        // Get all the dailyCallsList where avgDailyOperationTimeInternalAgentInMin equals to UPDATED_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldNotBeFound(
            "avgDailyOperationTimeInternalAgentInMin.equals=" + UPDATED_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByAvgDailyOperationTimeInternalAgentInMinIsInShouldWork() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where avgDailyOperationTimeInternalAgentInMin in DEFAULT_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN or UPDATED_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldBeFound(
            "avgDailyOperationTimeInternalAgentInMin.in=" +
            DEFAULT_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN +
            "," +
            UPDATED_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN
        );

        // Get all the dailyCallsList where avgDailyOperationTimeInternalAgentInMin equals to UPDATED_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldNotBeFound(
            "avgDailyOperationTimeInternalAgentInMin.in=" + UPDATED_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByAvgDailyOperationTimeInternalAgentInMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where avgDailyOperationTimeInternalAgentInMin is not null
        defaultDailyCallsShouldBeFound("avgDailyOperationTimeInternalAgentInMin.specified=true");

        // Get all the dailyCallsList where avgDailyOperationTimeInternalAgentInMin is null
        defaultDailyCallsShouldNotBeFound("avgDailyOperationTimeInternalAgentInMin.specified=false");
    }

    @Test
    @Transactional
    void getAllDailyCallsByAvgDailyOperationTimeInternalAgentInMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where avgDailyOperationTimeInternalAgentInMin is greater than or equal to DEFAULT_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldBeFound(
            "avgDailyOperationTimeInternalAgentInMin.greaterThanOrEqual=" + DEFAULT_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN
        );

        // Get all the dailyCallsList where avgDailyOperationTimeInternalAgentInMin is greater than or equal to UPDATED_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldNotBeFound(
            "avgDailyOperationTimeInternalAgentInMin.greaterThanOrEqual=" + UPDATED_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByAvgDailyOperationTimeInternalAgentInMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where avgDailyOperationTimeInternalAgentInMin is less than or equal to DEFAULT_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldBeFound(
            "avgDailyOperationTimeInternalAgentInMin.lessThanOrEqual=" + DEFAULT_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN
        );

        // Get all the dailyCallsList where avgDailyOperationTimeInternalAgentInMin is less than or equal to SMALLER_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldNotBeFound(
            "avgDailyOperationTimeInternalAgentInMin.lessThanOrEqual=" + SMALLER_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByAvgDailyOperationTimeInternalAgentInMinIsLessThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where avgDailyOperationTimeInternalAgentInMin is less than DEFAULT_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldNotBeFound(
            "avgDailyOperationTimeInternalAgentInMin.lessThan=" + DEFAULT_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN
        );

        // Get all the dailyCallsList where avgDailyOperationTimeInternalAgentInMin is less than UPDATED_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldBeFound(
            "avgDailyOperationTimeInternalAgentInMin.lessThan=" + UPDATED_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyCallsByAvgDailyOperationTimeInternalAgentInMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        // Get all the dailyCallsList where avgDailyOperationTimeInternalAgentInMin is greater than DEFAULT_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldNotBeFound(
            "avgDailyOperationTimeInternalAgentInMin.greaterThan=" + DEFAULT_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN
        );

        // Get all the dailyCallsList where avgDailyOperationTimeInternalAgentInMin is greater than SMALLER_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN
        defaultDailyCallsShouldBeFound(
            "avgDailyOperationTimeInternalAgentInMin.greaterThan=" + SMALLER_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN
        );
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDailyCallsShouldBeFound(String filter) throws Exception {
        restDailyCallsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dailyCalls.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].totalDailyReceivedCalls").value(hasItem(DEFAULT_TOTAL_DAILY_RECEIVED_CALLS)))
            .andExpect(jsonPath("$.[*].totalDailyAttendedCalls").value(hasItem(DEFAULT_TOTAL_DAILY_ATTENDED_CALLS)))
            .andExpect(jsonPath("$.[*].totalDailyMissedCalls").value(hasItem(DEFAULT_TOTAL_DAILY_MISSED_CALLS)))
            .andExpect(
                jsonPath("$.[*].totalDailyReceivedCallsExternalAgent").value(hasItem(DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT))
            )
            .andExpect(
                jsonPath("$.[*].totalDailyAttendedCallsExternalAgent").value(hasItem(DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT))
            )
            .andExpect(
                jsonPath("$.[*].totalDailyReceivedCallsInternalAgent").value(hasItem(DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT))
            )
            .andExpect(
                jsonPath("$.[*].totalDailyAttendedCallsInternalAgent").value(hasItem(DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT))
            )
            .andExpect(jsonPath("$.[*].totalDailyCallsTimeInMin").value(hasItem(DEFAULT_TOTAL_DAILY_CALLS_TIME_IN_MIN)))
            .andExpect(
                jsonPath("$.[*].totalDailyCallsTimeExternalAgentInMin").value(hasItem(DEFAULT_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN))
            )
            .andExpect(
                jsonPath("$.[*].totalDailyCallsTimeInternalAgentInMin").value(hasItem(DEFAULT_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN))
            )
            .andExpect(
                jsonPath("$.[*].avgDailyOperationTimeExternalAgentInMin")
                    .value(hasItem(DEFAULT_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN.doubleValue()))
            )
            .andExpect(
                jsonPath("$.[*].avgDailyOperationTimeInternalAgentInMin")
                    .value(hasItem(DEFAULT_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN.doubleValue()))
            );

        // Check, that the count call also returns 1
        restDailyCallsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDailyCallsShouldNotBeFound(String filter) throws Exception {
        restDailyCallsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDailyCallsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDailyCalls() throws Exception {
        // Get the dailyCalls
        restDailyCallsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDailyCalls() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        int databaseSizeBeforeUpdate = dailyCallsRepository.findAll().size();

        // Update the dailyCalls
        DailyCalls updatedDailyCalls = dailyCallsRepository.findById(dailyCalls.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDailyCalls are not directly saved in db
        em.detach(updatedDailyCalls);
        updatedDailyCalls
            .day(UPDATED_DAY)
            .totalDailyReceivedCalls(UPDATED_TOTAL_DAILY_RECEIVED_CALLS)
            .totalDailyAttendedCalls(UPDATED_TOTAL_DAILY_ATTENDED_CALLS)
            .totalDailyMissedCalls(UPDATED_TOTAL_DAILY_MISSED_CALLS)
            .totalDailyReceivedCallsExternalAgent(UPDATED_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT)
            .totalDailyAttendedCallsExternalAgent(UPDATED_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT)
            .totalDailyReceivedCallsInternalAgent(UPDATED_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT)
            .totalDailyAttendedCallsInternalAgent(UPDATED_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT)
            .totalDailyCallsTimeInMin(UPDATED_TOTAL_DAILY_CALLS_TIME_IN_MIN)
            .totalDailyCallsTimeExternalAgentInMin(UPDATED_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN)
            .totalDailyCallsTimeInternalAgentInMin(UPDATED_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN)
            .avgDailyOperationTimeExternalAgentInMin(UPDATED_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN)
            .avgDailyOperationTimeInternalAgentInMin(UPDATED_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN);
        DailyCallsDTO dailyCallsDTO = dailyCallsMapper.toDto(updatedDailyCalls);

        restDailyCallsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dailyCallsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dailyCallsDTO))
            )
            .andExpect(status().isOk());

        // Validate the DailyCalls in the database
        List<DailyCalls> dailyCallsList = dailyCallsRepository.findAll();
        assertThat(dailyCallsList).hasSize(databaseSizeBeforeUpdate);
        DailyCalls testDailyCalls = dailyCallsList.get(dailyCallsList.size() - 1);
        assertThat(testDailyCalls.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testDailyCalls.getTotalDailyReceivedCalls()).isEqualTo(UPDATED_TOTAL_DAILY_RECEIVED_CALLS);
        assertThat(testDailyCalls.getTotalDailyAttendedCalls()).isEqualTo(UPDATED_TOTAL_DAILY_ATTENDED_CALLS);
        assertThat(testDailyCalls.getTotalDailyMissedCalls()).isEqualTo(UPDATED_TOTAL_DAILY_MISSED_CALLS);
        assertThat(testDailyCalls.getTotalDailyReceivedCallsExternalAgent()).isEqualTo(UPDATED_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT);
        assertThat(testDailyCalls.getTotalDailyAttendedCallsExternalAgent()).isEqualTo(UPDATED_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT);
        assertThat(testDailyCalls.getTotalDailyReceivedCallsInternalAgent()).isEqualTo(UPDATED_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT);
        assertThat(testDailyCalls.getTotalDailyAttendedCallsInternalAgent()).isEqualTo(UPDATED_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT);
        assertThat(testDailyCalls.getTotalDailyCallsTimeInMin()).isEqualTo(UPDATED_TOTAL_DAILY_CALLS_TIME_IN_MIN);
        assertThat(testDailyCalls.getTotalDailyCallsTimeExternalAgentInMin())
            .isEqualTo(UPDATED_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN);
        assertThat(testDailyCalls.getTotalDailyCallsTimeInternalAgentInMin())
            .isEqualTo(UPDATED_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN);
        assertThat(testDailyCalls.getAvgDailyOperationTimeExternalAgentInMin())
            .isEqualTo(UPDATED_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN);
        assertThat(testDailyCalls.getAvgDailyOperationTimeInternalAgentInMin())
            .isEqualTo(UPDATED_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN);
    }

    @Test
    @Transactional
    void putNonExistingDailyCalls() throws Exception {
        int databaseSizeBeforeUpdate = dailyCallsRepository.findAll().size();
        dailyCalls.setId(longCount.incrementAndGet());

        // Create the DailyCalls
        DailyCallsDTO dailyCallsDTO = dailyCallsMapper.toDto(dailyCalls);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDailyCallsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dailyCallsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dailyCallsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyCalls in the database
        List<DailyCalls> dailyCallsList = dailyCallsRepository.findAll();
        assertThat(dailyCallsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDailyCalls() throws Exception {
        int databaseSizeBeforeUpdate = dailyCallsRepository.findAll().size();
        dailyCalls.setId(longCount.incrementAndGet());

        // Create the DailyCalls
        DailyCallsDTO dailyCallsDTO = dailyCallsMapper.toDto(dailyCalls);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDailyCallsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dailyCallsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyCalls in the database
        List<DailyCalls> dailyCallsList = dailyCallsRepository.findAll();
        assertThat(dailyCallsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDailyCalls() throws Exception {
        int databaseSizeBeforeUpdate = dailyCallsRepository.findAll().size();
        dailyCalls.setId(longCount.incrementAndGet());

        // Create the DailyCalls
        DailyCallsDTO dailyCallsDTO = dailyCallsMapper.toDto(dailyCalls);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDailyCallsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyCallsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DailyCalls in the database
        List<DailyCalls> dailyCallsList = dailyCallsRepository.findAll();
        assertThat(dailyCallsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDailyCallsWithPatch() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        int databaseSizeBeforeUpdate = dailyCallsRepository.findAll().size();

        // Update the dailyCalls using partial update
        DailyCalls partialUpdatedDailyCalls = new DailyCalls();
        partialUpdatedDailyCalls.setId(dailyCalls.getId());

        partialUpdatedDailyCalls
            .day(UPDATED_DAY)
            .totalDailyReceivedCalls(UPDATED_TOTAL_DAILY_RECEIVED_CALLS)
            .totalDailyAttendedCalls(UPDATED_TOTAL_DAILY_ATTENDED_CALLS)
            .totalDailyReceivedCallsExternalAgent(UPDATED_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT)
            .totalDailyAttendedCallsExternalAgent(UPDATED_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT)
            .totalDailyCallsTimeExternalAgentInMin(UPDATED_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN)
            .totalDailyCallsTimeInternalAgentInMin(UPDATED_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN)
            .avgDailyOperationTimeExternalAgentInMin(UPDATED_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN)
            .avgDailyOperationTimeInternalAgentInMin(UPDATED_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN);

        restDailyCallsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDailyCalls.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDailyCalls))
            )
            .andExpect(status().isOk());

        // Validate the DailyCalls in the database
        List<DailyCalls> dailyCallsList = dailyCallsRepository.findAll();
        assertThat(dailyCallsList).hasSize(databaseSizeBeforeUpdate);
        DailyCalls testDailyCalls = dailyCallsList.get(dailyCallsList.size() - 1);
        assertThat(testDailyCalls.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testDailyCalls.getTotalDailyReceivedCalls()).isEqualTo(UPDATED_TOTAL_DAILY_RECEIVED_CALLS);
        assertThat(testDailyCalls.getTotalDailyAttendedCalls()).isEqualTo(UPDATED_TOTAL_DAILY_ATTENDED_CALLS);
        assertThat(testDailyCalls.getTotalDailyMissedCalls()).isEqualTo(DEFAULT_TOTAL_DAILY_MISSED_CALLS);
        assertThat(testDailyCalls.getTotalDailyReceivedCallsExternalAgent()).isEqualTo(UPDATED_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT);
        assertThat(testDailyCalls.getTotalDailyAttendedCallsExternalAgent()).isEqualTo(UPDATED_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT);
        assertThat(testDailyCalls.getTotalDailyReceivedCallsInternalAgent()).isEqualTo(DEFAULT_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT);
        assertThat(testDailyCalls.getTotalDailyAttendedCallsInternalAgent()).isEqualTo(DEFAULT_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT);
        assertThat(testDailyCalls.getTotalDailyCallsTimeInMin()).isEqualTo(DEFAULT_TOTAL_DAILY_CALLS_TIME_IN_MIN);
        assertThat(testDailyCalls.getTotalDailyCallsTimeExternalAgentInMin())
            .isEqualTo(UPDATED_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN);
        assertThat(testDailyCalls.getTotalDailyCallsTimeInternalAgentInMin())
            .isEqualTo(UPDATED_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN);
        assertThat(testDailyCalls.getAvgDailyOperationTimeExternalAgentInMin())
            .isEqualTo(UPDATED_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN);
        assertThat(testDailyCalls.getAvgDailyOperationTimeInternalAgentInMin())
            .isEqualTo(UPDATED_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN);
    }

    @Test
    @Transactional
    void fullUpdateDailyCallsWithPatch() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        int databaseSizeBeforeUpdate = dailyCallsRepository.findAll().size();

        // Update the dailyCalls using partial update
        DailyCalls partialUpdatedDailyCalls = new DailyCalls();
        partialUpdatedDailyCalls.setId(dailyCalls.getId());

        partialUpdatedDailyCalls
            .day(UPDATED_DAY)
            .totalDailyReceivedCalls(UPDATED_TOTAL_DAILY_RECEIVED_CALLS)
            .totalDailyAttendedCalls(UPDATED_TOTAL_DAILY_ATTENDED_CALLS)
            .totalDailyMissedCalls(UPDATED_TOTAL_DAILY_MISSED_CALLS)
            .totalDailyReceivedCallsExternalAgent(UPDATED_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT)
            .totalDailyAttendedCallsExternalAgent(UPDATED_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT)
            .totalDailyReceivedCallsInternalAgent(UPDATED_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT)
            .totalDailyAttendedCallsInternalAgent(UPDATED_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT)
            .totalDailyCallsTimeInMin(UPDATED_TOTAL_DAILY_CALLS_TIME_IN_MIN)
            .totalDailyCallsTimeExternalAgentInMin(UPDATED_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN)
            .totalDailyCallsTimeInternalAgentInMin(UPDATED_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN)
            .avgDailyOperationTimeExternalAgentInMin(UPDATED_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN)
            .avgDailyOperationTimeInternalAgentInMin(UPDATED_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN);

        restDailyCallsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDailyCalls.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDailyCalls))
            )
            .andExpect(status().isOk());

        // Validate the DailyCalls in the database
        List<DailyCalls> dailyCallsList = dailyCallsRepository.findAll();
        assertThat(dailyCallsList).hasSize(databaseSizeBeforeUpdate);
        DailyCalls testDailyCalls = dailyCallsList.get(dailyCallsList.size() - 1);
        assertThat(testDailyCalls.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testDailyCalls.getTotalDailyReceivedCalls()).isEqualTo(UPDATED_TOTAL_DAILY_RECEIVED_CALLS);
        assertThat(testDailyCalls.getTotalDailyAttendedCalls()).isEqualTo(UPDATED_TOTAL_DAILY_ATTENDED_CALLS);
        assertThat(testDailyCalls.getTotalDailyMissedCalls()).isEqualTo(UPDATED_TOTAL_DAILY_MISSED_CALLS);
        assertThat(testDailyCalls.getTotalDailyReceivedCallsExternalAgent()).isEqualTo(UPDATED_TOTAL_DAILY_RECEIVED_CALLS_EXTERNAL_AGENT);
        assertThat(testDailyCalls.getTotalDailyAttendedCallsExternalAgent()).isEqualTo(UPDATED_TOTAL_DAILY_ATTENDED_CALLS_EXTERNAL_AGENT);
        assertThat(testDailyCalls.getTotalDailyReceivedCallsInternalAgent()).isEqualTo(UPDATED_TOTAL_DAILY_RECEIVED_CALLS_INTERNAL_AGENT);
        assertThat(testDailyCalls.getTotalDailyAttendedCallsInternalAgent()).isEqualTo(UPDATED_TOTAL_DAILY_ATTENDED_CALLS_INTERNAL_AGENT);
        assertThat(testDailyCalls.getTotalDailyCallsTimeInMin()).isEqualTo(UPDATED_TOTAL_DAILY_CALLS_TIME_IN_MIN);
        assertThat(testDailyCalls.getTotalDailyCallsTimeExternalAgentInMin())
            .isEqualTo(UPDATED_TOTAL_DAILY_CALLS_TIME_EXTERNAL_AGENT_IN_MIN);
        assertThat(testDailyCalls.getTotalDailyCallsTimeInternalAgentInMin())
            .isEqualTo(UPDATED_TOTAL_DAILY_CALLS_TIME_INTERNAL_AGENT_IN_MIN);
        assertThat(testDailyCalls.getAvgDailyOperationTimeExternalAgentInMin())
            .isEqualTo(UPDATED_AVG_DAILY_OPERATION_TIME_EXTERNAL_AGENT_IN_MIN);
        assertThat(testDailyCalls.getAvgDailyOperationTimeInternalAgentInMin())
            .isEqualTo(UPDATED_AVG_DAILY_OPERATION_TIME_INTERNAL_AGENT_IN_MIN);
    }

    @Test
    @Transactional
    void patchNonExistingDailyCalls() throws Exception {
        int databaseSizeBeforeUpdate = dailyCallsRepository.findAll().size();
        dailyCalls.setId(longCount.incrementAndGet());

        // Create the DailyCalls
        DailyCallsDTO dailyCallsDTO = dailyCallsMapper.toDto(dailyCalls);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDailyCallsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dailyCallsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dailyCallsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyCalls in the database
        List<DailyCalls> dailyCallsList = dailyCallsRepository.findAll();
        assertThat(dailyCallsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDailyCalls() throws Exception {
        int databaseSizeBeforeUpdate = dailyCallsRepository.findAll().size();
        dailyCalls.setId(longCount.incrementAndGet());

        // Create the DailyCalls
        DailyCallsDTO dailyCallsDTO = dailyCallsMapper.toDto(dailyCalls);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDailyCallsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dailyCallsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyCalls in the database
        List<DailyCalls> dailyCallsList = dailyCallsRepository.findAll();
        assertThat(dailyCallsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDailyCalls() throws Exception {
        int databaseSizeBeforeUpdate = dailyCallsRepository.findAll().size();
        dailyCalls.setId(longCount.incrementAndGet());

        // Create the DailyCalls
        DailyCallsDTO dailyCallsDTO = dailyCallsMapper.toDto(dailyCalls);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDailyCallsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dailyCallsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DailyCalls in the database
        List<DailyCalls> dailyCallsList = dailyCallsRepository.findAll();
        assertThat(dailyCallsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDailyCalls() throws Exception {
        // Initialize the database
        dailyCallsRepository.saveAndFlush(dailyCalls);

        int databaseSizeBeforeDelete = dailyCallsRepository.findAll().size();

        // Delete the dailyCalls
        restDailyCallsMockMvc
            .perform(delete(ENTITY_API_URL_ID, dailyCalls.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DailyCalls> dailyCallsList = dailyCallsRepository.findAll();
        assertThat(dailyCallsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
