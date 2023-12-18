package com.quality.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.quality.app.IntegrationTest;
import com.quality.app.domain.DailyChats;
import com.quality.app.repository.DailyChatsRepository;
import com.quality.app.service.dto.DailyChatsDTO;
import com.quality.app.service.mapper.DailyChatsMapper;
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
 * Integration tests for the {@link DailyChatsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DailyChatsResourceIT {

    private static final LocalDate DEFAULT_DAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DAY = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DAY = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_TOTAL_DAILY_RECEIVED_CHATS = 1;
    private static final Integer UPDATED_TOTAL_DAILY_RECEIVED_CHATS = 2;
    private static final Integer SMALLER_TOTAL_DAILY_RECEIVED_CHATS = 1 - 1;

    private static final Integer DEFAULT_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN = 1;
    private static final Integer UPDATED_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN = 2;
    private static final Integer SMALLER_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN = 1 - 1;

    private static final Integer DEFAULT_TOTAL_DAILY_ATTENDED_CHATS = 1;
    private static final Integer UPDATED_TOTAL_DAILY_ATTENDED_CHATS = 2;
    private static final Integer SMALLER_TOTAL_DAILY_ATTENDED_CHATS = 1 - 1;

    private static final Integer DEFAULT_TOTAL_DAILY_MISSED_CHATS = 1;
    private static final Integer UPDATED_TOTAL_DAILY_MISSED_CHATS = 2;
    private static final Integer SMALLER_TOTAL_DAILY_MISSED_CHATS = 1 - 1;

    private static final Float DEFAULT_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN = 1F;
    private static final Float UPDATED_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN = 2F;
    private static final Float SMALLER_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN = 1F - 1F;

    private static final String ENTITY_API_URL = "/api/daily-chats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DailyChatsRepository dailyChatsRepository;

    @Autowired
    private DailyChatsMapper dailyChatsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDailyChatsMockMvc;

    private DailyChats dailyChats;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DailyChats createEntity(EntityManager em) {
        DailyChats dailyChats = new DailyChats()
            .day(DEFAULT_DAY)
            .totalDailyReceivedChats(DEFAULT_TOTAL_DAILY_RECEIVED_CHATS)
            .totalDailyConversationChatsTimeInMin(DEFAULT_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN)
            .totalDailyAttendedChats(DEFAULT_TOTAL_DAILY_ATTENDED_CHATS)
            .totalDailyMissedChats(DEFAULT_TOTAL_DAILY_MISSED_CHATS)
            .avgDailyConversationChatsTimeInMin(DEFAULT_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN);
        return dailyChats;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DailyChats createUpdatedEntity(EntityManager em) {
        DailyChats dailyChats = new DailyChats()
            .day(UPDATED_DAY)
            .totalDailyReceivedChats(UPDATED_TOTAL_DAILY_RECEIVED_CHATS)
            .totalDailyConversationChatsTimeInMin(UPDATED_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN)
            .totalDailyAttendedChats(UPDATED_TOTAL_DAILY_ATTENDED_CHATS)
            .totalDailyMissedChats(UPDATED_TOTAL_DAILY_MISSED_CHATS)
            .avgDailyConversationChatsTimeInMin(UPDATED_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN);
        return dailyChats;
    }

    @BeforeEach
    public void initTest() {
        dailyChats = createEntity(em);
    }

    @Test
    @Transactional
    void createDailyChats() throws Exception {
        int databaseSizeBeforeCreate = dailyChatsRepository.findAll().size();
        // Create the DailyChats
        DailyChatsDTO dailyChatsDTO = dailyChatsMapper.toDto(dailyChats);
        restDailyChatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyChatsDTO)))
            .andExpect(status().isCreated());

        // Validate the DailyChats in the database
        List<DailyChats> dailyChatsList = dailyChatsRepository.findAll();
        assertThat(dailyChatsList).hasSize(databaseSizeBeforeCreate + 1);
        DailyChats testDailyChats = dailyChatsList.get(dailyChatsList.size() - 1);
        assertThat(testDailyChats.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testDailyChats.getTotalDailyReceivedChats()).isEqualTo(DEFAULT_TOTAL_DAILY_RECEIVED_CHATS);
        assertThat(testDailyChats.getTotalDailyConversationChatsTimeInMin()).isEqualTo(DEFAULT_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN);
        assertThat(testDailyChats.getTotalDailyAttendedChats()).isEqualTo(DEFAULT_TOTAL_DAILY_ATTENDED_CHATS);
        assertThat(testDailyChats.getTotalDailyMissedChats()).isEqualTo(DEFAULT_TOTAL_DAILY_MISSED_CHATS);
        assertThat(testDailyChats.getAvgDailyConversationChatsTimeInMin()).isEqualTo(DEFAULT_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN);
    }

    @Test
    @Transactional
    void createDailyChatsWithExistingId() throws Exception {
        // Create the DailyChats with an existing ID
        dailyChats.setId(1L);
        DailyChatsDTO dailyChatsDTO = dailyChatsMapper.toDto(dailyChats);

        int databaseSizeBeforeCreate = dailyChatsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDailyChatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyChatsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DailyChats in the database
        List<DailyChats> dailyChatsList = dailyChatsRepository.findAll();
        assertThat(dailyChatsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyChatsRepository.findAll().size();
        // set the field null
        dailyChats.setDay(null);

        // Create the DailyChats, which fails.
        DailyChatsDTO dailyChatsDTO = dailyChatsMapper.toDto(dailyChats);

        restDailyChatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyChatsDTO)))
            .andExpect(status().isBadRequest());

        List<DailyChats> dailyChatsList = dailyChatsRepository.findAll();
        assertThat(dailyChatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalDailyReceivedChatsIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyChatsRepository.findAll().size();
        // set the field null
        dailyChats.setTotalDailyReceivedChats(null);

        // Create the DailyChats, which fails.
        DailyChatsDTO dailyChatsDTO = dailyChatsMapper.toDto(dailyChats);

        restDailyChatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyChatsDTO)))
            .andExpect(status().isBadRequest());

        List<DailyChats> dailyChatsList = dailyChatsRepository.findAll();
        assertThat(dailyChatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalDailyConversationChatsTimeInMinIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyChatsRepository.findAll().size();
        // set the field null
        dailyChats.setTotalDailyConversationChatsTimeInMin(null);

        // Create the DailyChats, which fails.
        DailyChatsDTO dailyChatsDTO = dailyChatsMapper.toDto(dailyChats);

        restDailyChatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyChatsDTO)))
            .andExpect(status().isBadRequest());

        List<DailyChats> dailyChatsList = dailyChatsRepository.findAll();
        assertThat(dailyChatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalDailyAttendedChatsIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyChatsRepository.findAll().size();
        // set the field null
        dailyChats.setTotalDailyAttendedChats(null);

        // Create the DailyChats, which fails.
        DailyChatsDTO dailyChatsDTO = dailyChatsMapper.toDto(dailyChats);

        restDailyChatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyChatsDTO)))
            .andExpect(status().isBadRequest());

        List<DailyChats> dailyChatsList = dailyChatsRepository.findAll();
        assertThat(dailyChatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalDailyMissedChatsIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyChatsRepository.findAll().size();
        // set the field null
        dailyChats.setTotalDailyMissedChats(null);

        // Create the DailyChats, which fails.
        DailyChatsDTO dailyChatsDTO = dailyChatsMapper.toDto(dailyChats);

        restDailyChatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyChatsDTO)))
            .andExpect(status().isBadRequest());

        List<DailyChats> dailyChatsList = dailyChatsRepository.findAll();
        assertThat(dailyChatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAvgDailyConversationChatsTimeInMinIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyChatsRepository.findAll().size();
        // set the field null
        dailyChats.setAvgDailyConversationChatsTimeInMin(null);

        // Create the DailyChats, which fails.
        DailyChatsDTO dailyChatsDTO = dailyChatsMapper.toDto(dailyChats);

        restDailyChatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyChatsDTO)))
            .andExpect(status().isBadRequest());

        List<DailyChats> dailyChatsList = dailyChatsRepository.findAll();
        assertThat(dailyChatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDailyChats() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList
        restDailyChatsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dailyChats.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].totalDailyReceivedChats").value(hasItem(DEFAULT_TOTAL_DAILY_RECEIVED_CHATS)))
            .andExpect(
                jsonPath("$.[*].totalDailyConversationChatsTimeInMin").value(hasItem(DEFAULT_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN))
            )
            .andExpect(jsonPath("$.[*].totalDailyAttendedChats").value(hasItem(DEFAULT_TOTAL_DAILY_ATTENDED_CHATS)))
            .andExpect(jsonPath("$.[*].totalDailyMissedChats").value(hasItem(DEFAULT_TOTAL_DAILY_MISSED_CHATS)))
            .andExpect(
                jsonPath("$.[*].avgDailyConversationChatsTimeInMin")
                    .value(hasItem(DEFAULT_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN.doubleValue()))
            );
    }

    @Test
    @Transactional
    void getDailyChats() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get the dailyChats
        restDailyChatsMockMvc
            .perform(get(ENTITY_API_URL_ID, dailyChats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dailyChats.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.totalDailyReceivedChats").value(DEFAULT_TOTAL_DAILY_RECEIVED_CHATS))
            .andExpect(jsonPath("$.totalDailyConversationChatsTimeInMin").value(DEFAULT_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN))
            .andExpect(jsonPath("$.totalDailyAttendedChats").value(DEFAULT_TOTAL_DAILY_ATTENDED_CHATS))
            .andExpect(jsonPath("$.totalDailyMissedChats").value(DEFAULT_TOTAL_DAILY_MISSED_CHATS))
            .andExpect(
                jsonPath("$.avgDailyConversationChatsTimeInMin").value(DEFAULT_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN.doubleValue())
            );
    }

    @Test
    @Transactional
    void getDailyChatsByIdFiltering() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        Long id = dailyChats.getId();

        defaultDailyChatsShouldBeFound("id.equals=" + id);
        defaultDailyChatsShouldNotBeFound("id.notEquals=" + id);

        defaultDailyChatsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDailyChatsShouldNotBeFound("id.greaterThan=" + id);

        defaultDailyChatsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDailyChatsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDailyChatsByDayIsEqualToSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where day equals to DEFAULT_DAY
        defaultDailyChatsShouldBeFound("day.equals=" + DEFAULT_DAY);

        // Get all the dailyChatsList where day equals to UPDATED_DAY
        defaultDailyChatsShouldNotBeFound("day.equals=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllDailyChatsByDayIsInShouldWork() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where day in DEFAULT_DAY or UPDATED_DAY
        defaultDailyChatsShouldBeFound("day.in=" + DEFAULT_DAY + "," + UPDATED_DAY);

        // Get all the dailyChatsList where day equals to UPDATED_DAY
        defaultDailyChatsShouldNotBeFound("day.in=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllDailyChatsByDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where day is not null
        defaultDailyChatsShouldBeFound("day.specified=true");

        // Get all the dailyChatsList where day is null
        defaultDailyChatsShouldNotBeFound("day.specified=false");
    }

    @Test
    @Transactional
    void getAllDailyChatsByDayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where day is greater than or equal to DEFAULT_DAY
        defaultDailyChatsShouldBeFound("day.greaterThanOrEqual=" + DEFAULT_DAY);

        // Get all the dailyChatsList where day is greater than or equal to UPDATED_DAY
        defaultDailyChatsShouldNotBeFound("day.greaterThanOrEqual=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllDailyChatsByDayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where day is less than or equal to DEFAULT_DAY
        defaultDailyChatsShouldBeFound("day.lessThanOrEqual=" + DEFAULT_DAY);

        // Get all the dailyChatsList where day is less than or equal to SMALLER_DAY
        defaultDailyChatsShouldNotBeFound("day.lessThanOrEqual=" + SMALLER_DAY);
    }

    @Test
    @Transactional
    void getAllDailyChatsByDayIsLessThanSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where day is less than DEFAULT_DAY
        defaultDailyChatsShouldNotBeFound("day.lessThan=" + DEFAULT_DAY);

        // Get all the dailyChatsList where day is less than UPDATED_DAY
        defaultDailyChatsShouldBeFound("day.lessThan=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllDailyChatsByDayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where day is greater than DEFAULT_DAY
        defaultDailyChatsShouldNotBeFound("day.greaterThan=" + DEFAULT_DAY);

        // Get all the dailyChatsList where day is greater than SMALLER_DAY
        defaultDailyChatsShouldBeFound("day.greaterThan=" + SMALLER_DAY);
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyReceivedChatsIsEqualToSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyReceivedChats equals to DEFAULT_TOTAL_DAILY_RECEIVED_CHATS
        defaultDailyChatsShouldBeFound("totalDailyReceivedChats.equals=" + DEFAULT_TOTAL_DAILY_RECEIVED_CHATS);

        // Get all the dailyChatsList where totalDailyReceivedChats equals to UPDATED_TOTAL_DAILY_RECEIVED_CHATS
        defaultDailyChatsShouldNotBeFound("totalDailyReceivedChats.equals=" + UPDATED_TOTAL_DAILY_RECEIVED_CHATS);
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyReceivedChatsIsInShouldWork() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyReceivedChats in DEFAULT_TOTAL_DAILY_RECEIVED_CHATS or UPDATED_TOTAL_DAILY_RECEIVED_CHATS
        defaultDailyChatsShouldBeFound(
            "totalDailyReceivedChats.in=" + DEFAULT_TOTAL_DAILY_RECEIVED_CHATS + "," + UPDATED_TOTAL_DAILY_RECEIVED_CHATS
        );

        // Get all the dailyChatsList where totalDailyReceivedChats equals to UPDATED_TOTAL_DAILY_RECEIVED_CHATS
        defaultDailyChatsShouldNotBeFound("totalDailyReceivedChats.in=" + UPDATED_TOTAL_DAILY_RECEIVED_CHATS);
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyReceivedChatsIsNullOrNotNull() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyReceivedChats is not null
        defaultDailyChatsShouldBeFound("totalDailyReceivedChats.specified=true");

        // Get all the dailyChatsList where totalDailyReceivedChats is null
        defaultDailyChatsShouldNotBeFound("totalDailyReceivedChats.specified=false");
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyReceivedChatsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyReceivedChats is greater than or equal to DEFAULT_TOTAL_DAILY_RECEIVED_CHATS
        defaultDailyChatsShouldBeFound("totalDailyReceivedChats.greaterThanOrEqual=" + DEFAULT_TOTAL_DAILY_RECEIVED_CHATS);

        // Get all the dailyChatsList where totalDailyReceivedChats is greater than or equal to UPDATED_TOTAL_DAILY_RECEIVED_CHATS
        defaultDailyChatsShouldNotBeFound("totalDailyReceivedChats.greaterThanOrEqual=" + UPDATED_TOTAL_DAILY_RECEIVED_CHATS);
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyReceivedChatsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyReceivedChats is less than or equal to DEFAULT_TOTAL_DAILY_RECEIVED_CHATS
        defaultDailyChatsShouldBeFound("totalDailyReceivedChats.lessThanOrEqual=" + DEFAULT_TOTAL_DAILY_RECEIVED_CHATS);

        // Get all the dailyChatsList where totalDailyReceivedChats is less than or equal to SMALLER_TOTAL_DAILY_RECEIVED_CHATS
        defaultDailyChatsShouldNotBeFound("totalDailyReceivedChats.lessThanOrEqual=" + SMALLER_TOTAL_DAILY_RECEIVED_CHATS);
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyReceivedChatsIsLessThanSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyReceivedChats is less than DEFAULT_TOTAL_DAILY_RECEIVED_CHATS
        defaultDailyChatsShouldNotBeFound("totalDailyReceivedChats.lessThan=" + DEFAULT_TOTAL_DAILY_RECEIVED_CHATS);

        // Get all the dailyChatsList where totalDailyReceivedChats is less than UPDATED_TOTAL_DAILY_RECEIVED_CHATS
        defaultDailyChatsShouldBeFound("totalDailyReceivedChats.lessThan=" + UPDATED_TOTAL_DAILY_RECEIVED_CHATS);
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyReceivedChatsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyReceivedChats is greater than DEFAULT_TOTAL_DAILY_RECEIVED_CHATS
        defaultDailyChatsShouldNotBeFound("totalDailyReceivedChats.greaterThan=" + DEFAULT_TOTAL_DAILY_RECEIVED_CHATS);

        // Get all the dailyChatsList where totalDailyReceivedChats is greater than SMALLER_TOTAL_DAILY_RECEIVED_CHATS
        defaultDailyChatsShouldBeFound("totalDailyReceivedChats.greaterThan=" + SMALLER_TOTAL_DAILY_RECEIVED_CHATS);
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyConversationChatsTimeInMinIsEqualToSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyConversationChatsTimeInMin equals to DEFAULT_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        defaultDailyChatsShouldBeFound("totalDailyConversationChatsTimeInMin.equals=" + DEFAULT_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN);

        // Get all the dailyChatsList where totalDailyConversationChatsTimeInMin equals to UPDATED_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        defaultDailyChatsShouldNotBeFound(
            "totalDailyConversationChatsTimeInMin.equals=" + UPDATED_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyConversationChatsTimeInMinIsInShouldWork() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyConversationChatsTimeInMin in DEFAULT_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN or UPDATED_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        defaultDailyChatsShouldBeFound(
            "totalDailyConversationChatsTimeInMin.in=" +
            DEFAULT_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN +
            "," +
            UPDATED_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        );

        // Get all the dailyChatsList where totalDailyConversationChatsTimeInMin equals to UPDATED_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        defaultDailyChatsShouldNotBeFound("totalDailyConversationChatsTimeInMin.in=" + UPDATED_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN);
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyConversationChatsTimeInMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyConversationChatsTimeInMin is not null
        defaultDailyChatsShouldBeFound("totalDailyConversationChatsTimeInMin.specified=true");

        // Get all the dailyChatsList where totalDailyConversationChatsTimeInMin is null
        defaultDailyChatsShouldNotBeFound("totalDailyConversationChatsTimeInMin.specified=false");
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyConversationChatsTimeInMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyConversationChatsTimeInMin is greater than or equal to DEFAULT_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        defaultDailyChatsShouldBeFound(
            "totalDailyConversationChatsTimeInMin.greaterThanOrEqual=" + DEFAULT_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        );

        // Get all the dailyChatsList where totalDailyConversationChatsTimeInMin is greater than or equal to UPDATED_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        defaultDailyChatsShouldNotBeFound(
            "totalDailyConversationChatsTimeInMin.greaterThanOrEqual=" + UPDATED_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyConversationChatsTimeInMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyConversationChatsTimeInMin is less than or equal to DEFAULT_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        defaultDailyChatsShouldBeFound(
            "totalDailyConversationChatsTimeInMin.lessThanOrEqual=" + DEFAULT_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        );

        // Get all the dailyChatsList where totalDailyConversationChatsTimeInMin is less than or equal to SMALLER_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        defaultDailyChatsShouldNotBeFound(
            "totalDailyConversationChatsTimeInMin.lessThanOrEqual=" + SMALLER_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyConversationChatsTimeInMinIsLessThanSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyConversationChatsTimeInMin is less than DEFAULT_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        defaultDailyChatsShouldNotBeFound(
            "totalDailyConversationChatsTimeInMin.lessThan=" + DEFAULT_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        );

        // Get all the dailyChatsList where totalDailyConversationChatsTimeInMin is less than UPDATED_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        defaultDailyChatsShouldBeFound(
            "totalDailyConversationChatsTimeInMin.lessThan=" + UPDATED_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyConversationChatsTimeInMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyConversationChatsTimeInMin is greater than DEFAULT_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        defaultDailyChatsShouldNotBeFound(
            "totalDailyConversationChatsTimeInMin.greaterThan=" + DEFAULT_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        );

        // Get all the dailyChatsList where totalDailyConversationChatsTimeInMin is greater than SMALLER_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        defaultDailyChatsShouldBeFound(
            "totalDailyConversationChatsTimeInMin.greaterThan=" + SMALLER_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyAttendedChatsIsEqualToSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyAttendedChats equals to DEFAULT_TOTAL_DAILY_ATTENDED_CHATS
        defaultDailyChatsShouldBeFound("totalDailyAttendedChats.equals=" + DEFAULT_TOTAL_DAILY_ATTENDED_CHATS);

        // Get all the dailyChatsList where totalDailyAttendedChats equals to UPDATED_TOTAL_DAILY_ATTENDED_CHATS
        defaultDailyChatsShouldNotBeFound("totalDailyAttendedChats.equals=" + UPDATED_TOTAL_DAILY_ATTENDED_CHATS);
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyAttendedChatsIsInShouldWork() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyAttendedChats in DEFAULT_TOTAL_DAILY_ATTENDED_CHATS or UPDATED_TOTAL_DAILY_ATTENDED_CHATS
        defaultDailyChatsShouldBeFound(
            "totalDailyAttendedChats.in=" + DEFAULT_TOTAL_DAILY_ATTENDED_CHATS + "," + UPDATED_TOTAL_DAILY_ATTENDED_CHATS
        );

        // Get all the dailyChatsList where totalDailyAttendedChats equals to UPDATED_TOTAL_DAILY_ATTENDED_CHATS
        defaultDailyChatsShouldNotBeFound("totalDailyAttendedChats.in=" + UPDATED_TOTAL_DAILY_ATTENDED_CHATS);
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyAttendedChatsIsNullOrNotNull() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyAttendedChats is not null
        defaultDailyChatsShouldBeFound("totalDailyAttendedChats.specified=true");

        // Get all the dailyChatsList where totalDailyAttendedChats is null
        defaultDailyChatsShouldNotBeFound("totalDailyAttendedChats.specified=false");
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyAttendedChatsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyAttendedChats is greater than or equal to DEFAULT_TOTAL_DAILY_ATTENDED_CHATS
        defaultDailyChatsShouldBeFound("totalDailyAttendedChats.greaterThanOrEqual=" + DEFAULT_TOTAL_DAILY_ATTENDED_CHATS);

        // Get all the dailyChatsList where totalDailyAttendedChats is greater than or equal to UPDATED_TOTAL_DAILY_ATTENDED_CHATS
        defaultDailyChatsShouldNotBeFound("totalDailyAttendedChats.greaterThanOrEqual=" + UPDATED_TOTAL_DAILY_ATTENDED_CHATS);
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyAttendedChatsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyAttendedChats is less than or equal to DEFAULT_TOTAL_DAILY_ATTENDED_CHATS
        defaultDailyChatsShouldBeFound("totalDailyAttendedChats.lessThanOrEqual=" + DEFAULT_TOTAL_DAILY_ATTENDED_CHATS);

        // Get all the dailyChatsList where totalDailyAttendedChats is less than or equal to SMALLER_TOTAL_DAILY_ATTENDED_CHATS
        defaultDailyChatsShouldNotBeFound("totalDailyAttendedChats.lessThanOrEqual=" + SMALLER_TOTAL_DAILY_ATTENDED_CHATS);
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyAttendedChatsIsLessThanSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyAttendedChats is less than DEFAULT_TOTAL_DAILY_ATTENDED_CHATS
        defaultDailyChatsShouldNotBeFound("totalDailyAttendedChats.lessThan=" + DEFAULT_TOTAL_DAILY_ATTENDED_CHATS);

        // Get all the dailyChatsList where totalDailyAttendedChats is less than UPDATED_TOTAL_DAILY_ATTENDED_CHATS
        defaultDailyChatsShouldBeFound("totalDailyAttendedChats.lessThan=" + UPDATED_TOTAL_DAILY_ATTENDED_CHATS);
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyAttendedChatsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyAttendedChats is greater than DEFAULT_TOTAL_DAILY_ATTENDED_CHATS
        defaultDailyChatsShouldNotBeFound("totalDailyAttendedChats.greaterThan=" + DEFAULT_TOTAL_DAILY_ATTENDED_CHATS);

        // Get all the dailyChatsList where totalDailyAttendedChats is greater than SMALLER_TOTAL_DAILY_ATTENDED_CHATS
        defaultDailyChatsShouldBeFound("totalDailyAttendedChats.greaterThan=" + SMALLER_TOTAL_DAILY_ATTENDED_CHATS);
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyMissedChatsIsEqualToSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyMissedChats equals to DEFAULT_TOTAL_DAILY_MISSED_CHATS
        defaultDailyChatsShouldBeFound("totalDailyMissedChats.equals=" + DEFAULT_TOTAL_DAILY_MISSED_CHATS);

        // Get all the dailyChatsList where totalDailyMissedChats equals to UPDATED_TOTAL_DAILY_MISSED_CHATS
        defaultDailyChatsShouldNotBeFound("totalDailyMissedChats.equals=" + UPDATED_TOTAL_DAILY_MISSED_CHATS);
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyMissedChatsIsInShouldWork() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyMissedChats in DEFAULT_TOTAL_DAILY_MISSED_CHATS or UPDATED_TOTAL_DAILY_MISSED_CHATS
        defaultDailyChatsShouldBeFound(
            "totalDailyMissedChats.in=" + DEFAULT_TOTAL_DAILY_MISSED_CHATS + "," + UPDATED_TOTAL_DAILY_MISSED_CHATS
        );

        // Get all the dailyChatsList where totalDailyMissedChats equals to UPDATED_TOTAL_DAILY_MISSED_CHATS
        defaultDailyChatsShouldNotBeFound("totalDailyMissedChats.in=" + UPDATED_TOTAL_DAILY_MISSED_CHATS);
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyMissedChatsIsNullOrNotNull() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyMissedChats is not null
        defaultDailyChatsShouldBeFound("totalDailyMissedChats.specified=true");

        // Get all the dailyChatsList where totalDailyMissedChats is null
        defaultDailyChatsShouldNotBeFound("totalDailyMissedChats.specified=false");
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyMissedChatsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyMissedChats is greater than or equal to DEFAULT_TOTAL_DAILY_MISSED_CHATS
        defaultDailyChatsShouldBeFound("totalDailyMissedChats.greaterThanOrEqual=" + DEFAULT_TOTAL_DAILY_MISSED_CHATS);

        // Get all the dailyChatsList where totalDailyMissedChats is greater than or equal to UPDATED_TOTAL_DAILY_MISSED_CHATS
        defaultDailyChatsShouldNotBeFound("totalDailyMissedChats.greaterThanOrEqual=" + UPDATED_TOTAL_DAILY_MISSED_CHATS);
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyMissedChatsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyMissedChats is less than or equal to DEFAULT_TOTAL_DAILY_MISSED_CHATS
        defaultDailyChatsShouldBeFound("totalDailyMissedChats.lessThanOrEqual=" + DEFAULT_TOTAL_DAILY_MISSED_CHATS);

        // Get all the dailyChatsList where totalDailyMissedChats is less than or equal to SMALLER_TOTAL_DAILY_MISSED_CHATS
        defaultDailyChatsShouldNotBeFound("totalDailyMissedChats.lessThanOrEqual=" + SMALLER_TOTAL_DAILY_MISSED_CHATS);
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyMissedChatsIsLessThanSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyMissedChats is less than DEFAULT_TOTAL_DAILY_MISSED_CHATS
        defaultDailyChatsShouldNotBeFound("totalDailyMissedChats.lessThan=" + DEFAULT_TOTAL_DAILY_MISSED_CHATS);

        // Get all the dailyChatsList where totalDailyMissedChats is less than UPDATED_TOTAL_DAILY_MISSED_CHATS
        defaultDailyChatsShouldBeFound("totalDailyMissedChats.lessThan=" + UPDATED_TOTAL_DAILY_MISSED_CHATS);
    }

    @Test
    @Transactional
    void getAllDailyChatsByTotalDailyMissedChatsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where totalDailyMissedChats is greater than DEFAULT_TOTAL_DAILY_MISSED_CHATS
        defaultDailyChatsShouldNotBeFound("totalDailyMissedChats.greaterThan=" + DEFAULT_TOTAL_DAILY_MISSED_CHATS);

        // Get all the dailyChatsList where totalDailyMissedChats is greater than SMALLER_TOTAL_DAILY_MISSED_CHATS
        defaultDailyChatsShouldBeFound("totalDailyMissedChats.greaterThan=" + SMALLER_TOTAL_DAILY_MISSED_CHATS);
    }

    @Test
    @Transactional
    void getAllDailyChatsByAvgDailyConversationChatsTimeInMinIsEqualToSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where avgDailyConversationChatsTimeInMin equals to DEFAULT_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        defaultDailyChatsShouldBeFound("avgDailyConversationChatsTimeInMin.equals=" + DEFAULT_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN);

        // Get all the dailyChatsList where avgDailyConversationChatsTimeInMin equals to UPDATED_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        defaultDailyChatsShouldNotBeFound("avgDailyConversationChatsTimeInMin.equals=" + UPDATED_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN);
    }

    @Test
    @Transactional
    void getAllDailyChatsByAvgDailyConversationChatsTimeInMinIsInShouldWork() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where avgDailyConversationChatsTimeInMin in DEFAULT_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN or UPDATED_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        defaultDailyChatsShouldBeFound(
            "avgDailyConversationChatsTimeInMin.in=" +
            DEFAULT_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN +
            "," +
            UPDATED_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        );

        // Get all the dailyChatsList where avgDailyConversationChatsTimeInMin equals to UPDATED_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        defaultDailyChatsShouldNotBeFound("avgDailyConversationChatsTimeInMin.in=" + UPDATED_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN);
    }

    @Test
    @Transactional
    void getAllDailyChatsByAvgDailyConversationChatsTimeInMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where avgDailyConversationChatsTimeInMin is not null
        defaultDailyChatsShouldBeFound("avgDailyConversationChatsTimeInMin.specified=true");

        // Get all the dailyChatsList where avgDailyConversationChatsTimeInMin is null
        defaultDailyChatsShouldNotBeFound("avgDailyConversationChatsTimeInMin.specified=false");
    }

    @Test
    @Transactional
    void getAllDailyChatsByAvgDailyConversationChatsTimeInMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where avgDailyConversationChatsTimeInMin is greater than or equal to DEFAULT_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        defaultDailyChatsShouldBeFound(
            "avgDailyConversationChatsTimeInMin.greaterThanOrEqual=" + DEFAULT_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        );

        // Get all the dailyChatsList where avgDailyConversationChatsTimeInMin is greater than or equal to UPDATED_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        defaultDailyChatsShouldNotBeFound(
            "avgDailyConversationChatsTimeInMin.greaterThanOrEqual=" + UPDATED_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyChatsByAvgDailyConversationChatsTimeInMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where avgDailyConversationChatsTimeInMin is less than or equal to DEFAULT_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        defaultDailyChatsShouldBeFound(
            "avgDailyConversationChatsTimeInMin.lessThanOrEqual=" + DEFAULT_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        );

        // Get all the dailyChatsList where avgDailyConversationChatsTimeInMin is less than or equal to SMALLER_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        defaultDailyChatsShouldNotBeFound(
            "avgDailyConversationChatsTimeInMin.lessThanOrEqual=" + SMALLER_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        );
    }

    @Test
    @Transactional
    void getAllDailyChatsByAvgDailyConversationChatsTimeInMinIsLessThanSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where avgDailyConversationChatsTimeInMin is less than DEFAULT_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        defaultDailyChatsShouldNotBeFound(
            "avgDailyConversationChatsTimeInMin.lessThan=" + DEFAULT_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        );

        // Get all the dailyChatsList where avgDailyConversationChatsTimeInMin is less than UPDATED_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        defaultDailyChatsShouldBeFound("avgDailyConversationChatsTimeInMin.lessThan=" + UPDATED_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN);
    }

    @Test
    @Transactional
    void getAllDailyChatsByAvgDailyConversationChatsTimeInMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        // Get all the dailyChatsList where avgDailyConversationChatsTimeInMin is greater than DEFAULT_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        defaultDailyChatsShouldNotBeFound(
            "avgDailyConversationChatsTimeInMin.greaterThan=" + DEFAULT_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        );

        // Get all the dailyChatsList where avgDailyConversationChatsTimeInMin is greater than SMALLER_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        defaultDailyChatsShouldBeFound(
            "avgDailyConversationChatsTimeInMin.greaterThan=" + SMALLER_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN
        );
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDailyChatsShouldBeFound(String filter) throws Exception {
        restDailyChatsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dailyChats.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].totalDailyReceivedChats").value(hasItem(DEFAULT_TOTAL_DAILY_RECEIVED_CHATS)))
            .andExpect(
                jsonPath("$.[*].totalDailyConversationChatsTimeInMin").value(hasItem(DEFAULT_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN))
            )
            .andExpect(jsonPath("$.[*].totalDailyAttendedChats").value(hasItem(DEFAULT_TOTAL_DAILY_ATTENDED_CHATS)))
            .andExpect(jsonPath("$.[*].totalDailyMissedChats").value(hasItem(DEFAULT_TOTAL_DAILY_MISSED_CHATS)))
            .andExpect(
                jsonPath("$.[*].avgDailyConversationChatsTimeInMin")
                    .value(hasItem(DEFAULT_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN.doubleValue()))
            );

        // Check, that the count call also returns 1
        restDailyChatsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDailyChatsShouldNotBeFound(String filter) throws Exception {
        restDailyChatsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDailyChatsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDailyChats() throws Exception {
        // Get the dailyChats
        restDailyChatsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDailyChats() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        int databaseSizeBeforeUpdate = dailyChatsRepository.findAll().size();

        // Update the dailyChats
        DailyChats updatedDailyChats = dailyChatsRepository.findById(dailyChats.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDailyChats are not directly saved in db
        em.detach(updatedDailyChats);
        updatedDailyChats
            .day(UPDATED_DAY)
            .totalDailyReceivedChats(UPDATED_TOTAL_DAILY_RECEIVED_CHATS)
            .totalDailyConversationChatsTimeInMin(UPDATED_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN)
            .totalDailyAttendedChats(UPDATED_TOTAL_DAILY_ATTENDED_CHATS)
            .totalDailyMissedChats(UPDATED_TOTAL_DAILY_MISSED_CHATS)
            .avgDailyConversationChatsTimeInMin(UPDATED_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN);
        DailyChatsDTO dailyChatsDTO = dailyChatsMapper.toDto(updatedDailyChats);

        restDailyChatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dailyChatsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dailyChatsDTO))
            )
            .andExpect(status().isOk());

        // Validate the DailyChats in the database
        List<DailyChats> dailyChatsList = dailyChatsRepository.findAll();
        assertThat(dailyChatsList).hasSize(databaseSizeBeforeUpdate);
        DailyChats testDailyChats = dailyChatsList.get(dailyChatsList.size() - 1);
        assertThat(testDailyChats.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testDailyChats.getTotalDailyReceivedChats()).isEqualTo(UPDATED_TOTAL_DAILY_RECEIVED_CHATS);
        assertThat(testDailyChats.getTotalDailyConversationChatsTimeInMin()).isEqualTo(UPDATED_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN);
        assertThat(testDailyChats.getTotalDailyAttendedChats()).isEqualTo(UPDATED_TOTAL_DAILY_ATTENDED_CHATS);
        assertThat(testDailyChats.getTotalDailyMissedChats()).isEqualTo(UPDATED_TOTAL_DAILY_MISSED_CHATS);
        assertThat(testDailyChats.getAvgDailyConversationChatsTimeInMin()).isEqualTo(UPDATED_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN);
    }

    @Test
    @Transactional
    void putNonExistingDailyChats() throws Exception {
        int databaseSizeBeforeUpdate = dailyChatsRepository.findAll().size();
        dailyChats.setId(longCount.incrementAndGet());

        // Create the DailyChats
        DailyChatsDTO dailyChatsDTO = dailyChatsMapper.toDto(dailyChats);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDailyChatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dailyChatsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dailyChatsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyChats in the database
        List<DailyChats> dailyChatsList = dailyChatsRepository.findAll();
        assertThat(dailyChatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDailyChats() throws Exception {
        int databaseSizeBeforeUpdate = dailyChatsRepository.findAll().size();
        dailyChats.setId(longCount.incrementAndGet());

        // Create the DailyChats
        DailyChatsDTO dailyChatsDTO = dailyChatsMapper.toDto(dailyChats);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDailyChatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dailyChatsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyChats in the database
        List<DailyChats> dailyChatsList = dailyChatsRepository.findAll();
        assertThat(dailyChatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDailyChats() throws Exception {
        int databaseSizeBeforeUpdate = dailyChatsRepository.findAll().size();
        dailyChats.setId(longCount.incrementAndGet());

        // Create the DailyChats
        DailyChatsDTO dailyChatsDTO = dailyChatsMapper.toDto(dailyChats);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDailyChatsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyChatsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DailyChats in the database
        List<DailyChats> dailyChatsList = dailyChatsRepository.findAll();
        assertThat(dailyChatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDailyChatsWithPatch() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        int databaseSizeBeforeUpdate = dailyChatsRepository.findAll().size();

        // Update the dailyChats using partial update
        DailyChats partialUpdatedDailyChats = new DailyChats();
        partialUpdatedDailyChats.setId(dailyChats.getId());

        partialUpdatedDailyChats
            .day(UPDATED_DAY)
            .totalDailyReceivedChats(UPDATED_TOTAL_DAILY_RECEIVED_CHATS)
            .totalDailyConversationChatsTimeInMin(UPDATED_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN)
            .avgDailyConversationChatsTimeInMin(UPDATED_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN);

        restDailyChatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDailyChats.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDailyChats))
            )
            .andExpect(status().isOk());

        // Validate the DailyChats in the database
        List<DailyChats> dailyChatsList = dailyChatsRepository.findAll();
        assertThat(dailyChatsList).hasSize(databaseSizeBeforeUpdate);
        DailyChats testDailyChats = dailyChatsList.get(dailyChatsList.size() - 1);
        assertThat(testDailyChats.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testDailyChats.getTotalDailyReceivedChats()).isEqualTo(UPDATED_TOTAL_DAILY_RECEIVED_CHATS);
        assertThat(testDailyChats.getTotalDailyConversationChatsTimeInMin()).isEqualTo(UPDATED_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN);
        assertThat(testDailyChats.getTotalDailyAttendedChats()).isEqualTo(DEFAULT_TOTAL_DAILY_ATTENDED_CHATS);
        assertThat(testDailyChats.getTotalDailyMissedChats()).isEqualTo(DEFAULT_TOTAL_DAILY_MISSED_CHATS);
        assertThat(testDailyChats.getAvgDailyConversationChatsTimeInMin()).isEqualTo(UPDATED_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN);
    }

    @Test
    @Transactional
    void fullUpdateDailyChatsWithPatch() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        int databaseSizeBeforeUpdate = dailyChatsRepository.findAll().size();

        // Update the dailyChats using partial update
        DailyChats partialUpdatedDailyChats = new DailyChats();
        partialUpdatedDailyChats.setId(dailyChats.getId());

        partialUpdatedDailyChats
            .day(UPDATED_DAY)
            .totalDailyReceivedChats(UPDATED_TOTAL_DAILY_RECEIVED_CHATS)
            .totalDailyConversationChatsTimeInMin(UPDATED_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN)
            .totalDailyAttendedChats(UPDATED_TOTAL_DAILY_ATTENDED_CHATS)
            .totalDailyMissedChats(UPDATED_TOTAL_DAILY_MISSED_CHATS)
            .avgDailyConversationChatsTimeInMin(UPDATED_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN);

        restDailyChatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDailyChats.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDailyChats))
            )
            .andExpect(status().isOk());

        // Validate the DailyChats in the database
        List<DailyChats> dailyChatsList = dailyChatsRepository.findAll();
        assertThat(dailyChatsList).hasSize(databaseSizeBeforeUpdate);
        DailyChats testDailyChats = dailyChatsList.get(dailyChatsList.size() - 1);
        assertThat(testDailyChats.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testDailyChats.getTotalDailyReceivedChats()).isEqualTo(UPDATED_TOTAL_DAILY_RECEIVED_CHATS);
        assertThat(testDailyChats.getTotalDailyConversationChatsTimeInMin()).isEqualTo(UPDATED_TOTAL_DAILY_CONVERSATION_CHATS_TIME_IN_MIN);
        assertThat(testDailyChats.getTotalDailyAttendedChats()).isEqualTo(UPDATED_TOTAL_DAILY_ATTENDED_CHATS);
        assertThat(testDailyChats.getTotalDailyMissedChats()).isEqualTo(UPDATED_TOTAL_DAILY_MISSED_CHATS);
        assertThat(testDailyChats.getAvgDailyConversationChatsTimeInMin()).isEqualTo(UPDATED_AVG_DAILY_CONVERSATION_CHATS_TIME_IN_MIN);
    }

    @Test
    @Transactional
    void patchNonExistingDailyChats() throws Exception {
        int databaseSizeBeforeUpdate = dailyChatsRepository.findAll().size();
        dailyChats.setId(longCount.incrementAndGet());

        // Create the DailyChats
        DailyChatsDTO dailyChatsDTO = dailyChatsMapper.toDto(dailyChats);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDailyChatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dailyChatsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dailyChatsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyChats in the database
        List<DailyChats> dailyChatsList = dailyChatsRepository.findAll();
        assertThat(dailyChatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDailyChats() throws Exception {
        int databaseSizeBeforeUpdate = dailyChatsRepository.findAll().size();
        dailyChats.setId(longCount.incrementAndGet());

        // Create the DailyChats
        DailyChatsDTO dailyChatsDTO = dailyChatsMapper.toDto(dailyChats);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDailyChatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dailyChatsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyChats in the database
        List<DailyChats> dailyChatsList = dailyChatsRepository.findAll();
        assertThat(dailyChatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDailyChats() throws Exception {
        int databaseSizeBeforeUpdate = dailyChatsRepository.findAll().size();
        dailyChats.setId(longCount.incrementAndGet());

        // Create the DailyChats
        DailyChatsDTO dailyChatsDTO = dailyChatsMapper.toDto(dailyChats);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDailyChatsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dailyChatsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DailyChats in the database
        List<DailyChats> dailyChatsList = dailyChatsRepository.findAll();
        assertThat(dailyChatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDailyChats() throws Exception {
        // Initialize the database
        dailyChatsRepository.saveAndFlush(dailyChats);

        int databaseSizeBeforeDelete = dailyChatsRepository.findAll().size();

        // Delete the dailyChats
        restDailyChatsMockMvc
            .perform(delete(ENTITY_API_URL_ID, dailyChats.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DailyChats> dailyChatsList = dailyChatsRepository.findAll();
        assertThat(dailyChatsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
