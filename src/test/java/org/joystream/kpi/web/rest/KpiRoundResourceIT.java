package org.joystream.kpi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.joystream.kpi.IntegrationTest;
import org.joystream.kpi.domain.Kpi;
import org.joystream.kpi.domain.KpiRound;
import org.joystream.kpi.repository.KpiRoundRepository;
import org.joystream.kpi.service.criteria.KpiRoundCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KpiRoundResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KpiRoundResourceIT {

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_POSSIBLE_REWARDS = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_POSSIBLE_REWARDS = "BBBBBBBBBB";

    private static final Integer DEFAULT_COUNCIL_ELECTED_IN_ROUND = 1;
    private static final Integer UPDATED_COUNCIL_ELECTED_IN_ROUND = 2;
    private static final Integer SMALLER_COUNCIL_ELECTED_IN_ROUND = 1 - 1;

    private static final Integer DEFAULT_COUNCIL_MEMBERS = 2;
    private static final Integer UPDATED_COUNCIL_MEMBERS = 3;
    private static final Integer SMALLER_COUNCIL_MEMBERS = 2 - 1;

    private static final String DEFAULT_TERM_LENGTH = "AAAAAAAAAA";
    private static final String UPDATED_TERM_LENGTH = "BBBBBBBBBB";

    private static final Integer DEFAULT_START_BLOCK = 1;
    private static final Integer UPDATED_START_BLOCK = 2;
    private static final Integer SMALLER_START_BLOCK = 1 - 1;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_END_BLOCK = "AAAAAAAAAA";
    private static final String UPDATED_END_BLOCK = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_END_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_TERM_SUMMARY = "AAAAAAAAAA";
    private static final String UPDATED_TERM_SUMMARY = "BBBBBBBBBB";

    private static final String DEFAULT_SUMMARY_SUBMISSION_DEADLINE = "AAAAAAAAAA";
    private static final String UPDATED_SUMMARY_SUBMISSION_DEADLINE = "BBBBBBBBBB";

    private static final String DEFAULT_MAX_FIAT_POOL_DIFFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_MAX_FIAT_POOL_DIFFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER_OF_KPIS = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER_OF_KPIS = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/kpi-rounds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KpiRoundRepository kpiRoundRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKpiRoundMockMvc;

    private KpiRound kpiRound;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KpiRound createEntity(EntityManager em) {
        KpiRound kpiRound = new KpiRound()
            .displayName(DEFAULT_DISPLAY_NAME)
            .totalPossibleRewards(DEFAULT_TOTAL_POSSIBLE_REWARDS)
            .councilElectedInRound(DEFAULT_COUNCIL_ELECTED_IN_ROUND)
            .councilMembers(DEFAULT_COUNCIL_MEMBERS)
            .termLength(DEFAULT_TERM_LENGTH)
            .startBlock(DEFAULT_START_BLOCK)
            .startDate(DEFAULT_START_DATE)
            .endBlock(DEFAULT_END_BLOCK)
            .endDate(DEFAULT_END_DATE)
            .termSummary(DEFAULT_TERM_SUMMARY)
            .summarySubmissionDeadline(DEFAULT_SUMMARY_SUBMISSION_DEADLINE)
            .maxFiatPoolDifference(DEFAULT_MAX_FIAT_POOL_DIFFERENCE)
            .numberOfKpis(DEFAULT_NUMBER_OF_KPIS)
            .notes(DEFAULT_NOTES);
        return kpiRound;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KpiRound createUpdatedEntity(EntityManager em) {
        KpiRound kpiRound = new KpiRound()
            .displayName(UPDATED_DISPLAY_NAME)
            .totalPossibleRewards(UPDATED_TOTAL_POSSIBLE_REWARDS)
            .councilElectedInRound(UPDATED_COUNCIL_ELECTED_IN_ROUND)
            .councilMembers(UPDATED_COUNCIL_MEMBERS)
            .termLength(UPDATED_TERM_LENGTH)
            .startBlock(UPDATED_START_BLOCK)
            .startDate(UPDATED_START_DATE)
            .endBlock(UPDATED_END_BLOCK)
            .endDate(UPDATED_END_DATE)
            .termSummary(UPDATED_TERM_SUMMARY)
            .summarySubmissionDeadline(UPDATED_SUMMARY_SUBMISSION_DEADLINE)
            .maxFiatPoolDifference(UPDATED_MAX_FIAT_POOL_DIFFERENCE)
            .numberOfKpis(UPDATED_NUMBER_OF_KPIS)
            .notes(UPDATED_NOTES);
        return kpiRound;
    }

    @BeforeEach
    public void initTest() {
        kpiRound = createEntity(em);
    }

    @Test
    @Transactional
    void createKpiRound() throws Exception {
        int databaseSizeBeforeCreate = kpiRoundRepository.findAll().size();
        // Create the KpiRound
        restKpiRoundMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpiRound)))
            .andExpect(status().isCreated());

        // Validate the KpiRound in the database
        List<KpiRound> kpiRoundList = kpiRoundRepository.findAll();
        assertThat(kpiRoundList).hasSize(databaseSizeBeforeCreate + 1);
        KpiRound testKpiRound = kpiRoundList.get(kpiRoundList.size() - 1);
        assertThat(testKpiRound.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testKpiRound.getTotalPossibleRewards()).isEqualTo(DEFAULT_TOTAL_POSSIBLE_REWARDS);
        assertThat(testKpiRound.getCouncilElectedInRound()).isEqualTo(DEFAULT_COUNCIL_ELECTED_IN_ROUND);
        assertThat(testKpiRound.getCouncilMembers()).isEqualTo(DEFAULT_COUNCIL_MEMBERS);
        assertThat(testKpiRound.getTermLength()).isEqualTo(DEFAULT_TERM_LENGTH);
        assertThat(testKpiRound.getStartBlock()).isEqualTo(DEFAULT_START_BLOCK);
        assertThat(testKpiRound.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testKpiRound.getEndBlock()).isEqualTo(DEFAULT_END_BLOCK);
        assertThat(testKpiRound.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testKpiRound.getTermSummary()).isEqualTo(DEFAULT_TERM_SUMMARY);
        assertThat(testKpiRound.getSummarySubmissionDeadline()).isEqualTo(DEFAULT_SUMMARY_SUBMISSION_DEADLINE);
        assertThat(testKpiRound.getMaxFiatPoolDifference()).isEqualTo(DEFAULT_MAX_FIAT_POOL_DIFFERENCE);
        assertThat(testKpiRound.getNumberOfKpis()).isEqualTo(DEFAULT_NUMBER_OF_KPIS);
        assertThat(testKpiRound.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void createKpiRoundWithExistingId() throws Exception {
        // Create the KpiRound with an existing ID
        kpiRound.setId(1L);

        int databaseSizeBeforeCreate = kpiRoundRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKpiRoundMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpiRound)))
            .andExpect(status().isBadRequest());

        // Validate the KpiRound in the database
        List<KpiRound> kpiRoundList = kpiRoundRepository.findAll();
        assertThat(kpiRoundList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDisplayNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = kpiRoundRepository.findAll().size();
        // set the field null
        kpiRound.setDisplayName(null);

        // Create the KpiRound, which fails.

        restKpiRoundMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpiRound)))
            .andExpect(status().isBadRequest());

        List<KpiRound> kpiRoundList = kpiRoundRepository.findAll();
        assertThat(kpiRoundList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalPossibleRewardsIsRequired() throws Exception {
        int databaseSizeBeforeTest = kpiRoundRepository.findAll().size();
        // set the field null
        kpiRound.setTotalPossibleRewards(null);

        // Create the KpiRound, which fails.

        restKpiRoundMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpiRound)))
            .andExpect(status().isBadRequest());

        List<KpiRound> kpiRoundList = kpiRoundRepository.findAll();
        assertThat(kpiRoundList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCouncilElectedInRoundIsRequired() throws Exception {
        int databaseSizeBeforeTest = kpiRoundRepository.findAll().size();
        // set the field null
        kpiRound.setCouncilElectedInRound(null);

        // Create the KpiRound, which fails.

        restKpiRoundMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpiRound)))
            .andExpect(status().isBadRequest());

        List<KpiRound> kpiRoundList = kpiRoundRepository.findAll();
        assertThat(kpiRoundList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCouncilMembersIsRequired() throws Exception {
        int databaseSizeBeforeTest = kpiRoundRepository.findAll().size();
        // set the field null
        kpiRound.setCouncilMembers(null);

        // Create the KpiRound, which fails.

        restKpiRoundMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpiRound)))
            .andExpect(status().isBadRequest());

        List<KpiRound> kpiRoundList = kpiRoundRepository.findAll();
        assertThat(kpiRoundList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTermLengthIsRequired() throws Exception {
        int databaseSizeBeforeTest = kpiRoundRepository.findAll().size();
        // set the field null
        kpiRound.setTermLength(null);

        // Create the KpiRound, which fails.

        restKpiRoundMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpiRound)))
            .andExpect(status().isBadRequest());

        List<KpiRound> kpiRoundList = kpiRoundRepository.findAll();
        assertThat(kpiRoundList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStartBlockIsRequired() throws Exception {
        int databaseSizeBeforeTest = kpiRoundRepository.findAll().size();
        // set the field null
        kpiRound.setStartBlock(null);

        // Create the KpiRound, which fails.

        restKpiRoundMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpiRound)))
            .andExpect(status().isBadRequest());

        List<KpiRound> kpiRoundList = kpiRoundRepository.findAll();
        assertThat(kpiRoundList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = kpiRoundRepository.findAll().size();
        // set the field null
        kpiRound.setStartDate(null);

        // Create the KpiRound, which fails.

        restKpiRoundMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpiRound)))
            .andExpect(status().isBadRequest());

        List<KpiRound> kpiRoundList = kpiRoundRepository.findAll();
        assertThat(kpiRoundList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndBlockIsRequired() throws Exception {
        int databaseSizeBeforeTest = kpiRoundRepository.findAll().size();
        // set the field null
        kpiRound.setEndBlock(null);

        // Create the KpiRound, which fails.

        restKpiRoundMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpiRound)))
            .andExpect(status().isBadRequest());

        List<KpiRound> kpiRoundList = kpiRoundRepository.findAll();
        assertThat(kpiRoundList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = kpiRoundRepository.findAll().size();
        // set the field null
        kpiRound.setEndDate(null);

        // Create the KpiRound, which fails.

        restKpiRoundMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpiRound)))
            .andExpect(status().isBadRequest());

        List<KpiRound> kpiRoundList = kpiRoundRepository.findAll();
        assertThat(kpiRoundList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllKpiRounds() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList
        restKpiRoundMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kpiRound.getId().intValue())))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME)))
            .andExpect(jsonPath("$.[*].totalPossibleRewards").value(hasItem(DEFAULT_TOTAL_POSSIBLE_REWARDS)))
            .andExpect(jsonPath("$.[*].councilElectedInRound").value(hasItem(DEFAULT_COUNCIL_ELECTED_IN_ROUND)))
            .andExpect(jsonPath("$.[*].councilMembers").value(hasItem(DEFAULT_COUNCIL_MEMBERS)))
            .andExpect(jsonPath("$.[*].termLength").value(hasItem(DEFAULT_TERM_LENGTH)))
            .andExpect(jsonPath("$.[*].startBlock").value(hasItem(DEFAULT_START_BLOCK)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endBlock").value(hasItem(DEFAULT_END_BLOCK)))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].termSummary").value(hasItem(DEFAULT_TERM_SUMMARY)))
            .andExpect(jsonPath("$.[*].summarySubmissionDeadline").value(hasItem(DEFAULT_SUMMARY_SUBMISSION_DEADLINE)))
            .andExpect(jsonPath("$.[*].maxFiatPoolDifference").value(hasItem(DEFAULT_MAX_FIAT_POOL_DIFFERENCE)))
            .andExpect(jsonPath("$.[*].numberOfKpis").value(hasItem(DEFAULT_NUMBER_OF_KPIS)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    void getKpiRound() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get the kpiRound
        restKpiRoundMockMvc
            .perform(get(ENTITY_API_URL_ID, kpiRound.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kpiRound.getId().intValue()))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME))
            .andExpect(jsonPath("$.totalPossibleRewards").value(DEFAULT_TOTAL_POSSIBLE_REWARDS))
            .andExpect(jsonPath("$.councilElectedInRound").value(DEFAULT_COUNCIL_ELECTED_IN_ROUND))
            .andExpect(jsonPath("$.councilMembers").value(DEFAULT_COUNCIL_MEMBERS))
            .andExpect(jsonPath("$.termLength").value(DEFAULT_TERM_LENGTH))
            .andExpect(jsonPath("$.startBlock").value(DEFAULT_START_BLOCK))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endBlock").value(DEFAULT_END_BLOCK))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.termSummary").value(DEFAULT_TERM_SUMMARY))
            .andExpect(jsonPath("$.summarySubmissionDeadline").value(DEFAULT_SUMMARY_SUBMISSION_DEADLINE))
            .andExpect(jsonPath("$.maxFiatPoolDifference").value(DEFAULT_MAX_FIAT_POOL_DIFFERENCE))
            .andExpect(jsonPath("$.numberOfKpis").value(DEFAULT_NUMBER_OF_KPIS))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    void getKpiRoundsByIdFiltering() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        Long id = kpiRound.getId();

        defaultKpiRoundShouldBeFound("id.equals=" + id);
        defaultKpiRoundShouldNotBeFound("id.notEquals=" + id);

        defaultKpiRoundShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKpiRoundShouldNotBeFound("id.greaterThan=" + id);

        defaultKpiRoundShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKpiRoundShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByDisplayNameIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where displayName equals to DEFAULT_DISPLAY_NAME
        defaultKpiRoundShouldBeFound("displayName.equals=" + DEFAULT_DISPLAY_NAME);

        // Get all the kpiRoundList where displayName equals to UPDATED_DISPLAY_NAME
        defaultKpiRoundShouldNotBeFound("displayName.equals=" + UPDATED_DISPLAY_NAME);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByDisplayNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where displayName not equals to DEFAULT_DISPLAY_NAME
        defaultKpiRoundShouldNotBeFound("displayName.notEquals=" + DEFAULT_DISPLAY_NAME);

        // Get all the kpiRoundList where displayName not equals to UPDATED_DISPLAY_NAME
        defaultKpiRoundShouldBeFound("displayName.notEquals=" + UPDATED_DISPLAY_NAME);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByDisplayNameIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where displayName in DEFAULT_DISPLAY_NAME or UPDATED_DISPLAY_NAME
        defaultKpiRoundShouldBeFound("displayName.in=" + DEFAULT_DISPLAY_NAME + "," + UPDATED_DISPLAY_NAME);

        // Get all the kpiRoundList where displayName equals to UPDATED_DISPLAY_NAME
        defaultKpiRoundShouldNotBeFound("displayName.in=" + UPDATED_DISPLAY_NAME);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByDisplayNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where displayName is not null
        defaultKpiRoundShouldBeFound("displayName.specified=true");

        // Get all the kpiRoundList where displayName is null
        defaultKpiRoundShouldNotBeFound("displayName.specified=false");
    }

    @Test
    @Transactional
    void getAllKpiRoundsByDisplayNameContainsSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where displayName contains DEFAULT_DISPLAY_NAME
        defaultKpiRoundShouldBeFound("displayName.contains=" + DEFAULT_DISPLAY_NAME);

        // Get all the kpiRoundList where displayName contains UPDATED_DISPLAY_NAME
        defaultKpiRoundShouldNotBeFound("displayName.contains=" + UPDATED_DISPLAY_NAME);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByDisplayNameNotContainsSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where displayName does not contain DEFAULT_DISPLAY_NAME
        defaultKpiRoundShouldNotBeFound("displayName.doesNotContain=" + DEFAULT_DISPLAY_NAME);

        // Get all the kpiRoundList where displayName does not contain UPDATED_DISPLAY_NAME
        defaultKpiRoundShouldBeFound("displayName.doesNotContain=" + UPDATED_DISPLAY_NAME);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByTotalPossibleRewardsIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where totalPossibleRewards equals to DEFAULT_TOTAL_POSSIBLE_REWARDS
        defaultKpiRoundShouldBeFound("totalPossibleRewards.equals=" + DEFAULT_TOTAL_POSSIBLE_REWARDS);

        // Get all the kpiRoundList where totalPossibleRewards equals to UPDATED_TOTAL_POSSIBLE_REWARDS
        defaultKpiRoundShouldNotBeFound("totalPossibleRewards.equals=" + UPDATED_TOTAL_POSSIBLE_REWARDS);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByTotalPossibleRewardsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where totalPossibleRewards not equals to DEFAULT_TOTAL_POSSIBLE_REWARDS
        defaultKpiRoundShouldNotBeFound("totalPossibleRewards.notEquals=" + DEFAULT_TOTAL_POSSIBLE_REWARDS);

        // Get all the kpiRoundList where totalPossibleRewards not equals to UPDATED_TOTAL_POSSIBLE_REWARDS
        defaultKpiRoundShouldBeFound("totalPossibleRewards.notEquals=" + UPDATED_TOTAL_POSSIBLE_REWARDS);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByTotalPossibleRewardsIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where totalPossibleRewards in DEFAULT_TOTAL_POSSIBLE_REWARDS or UPDATED_TOTAL_POSSIBLE_REWARDS
        defaultKpiRoundShouldBeFound("totalPossibleRewards.in=" + DEFAULT_TOTAL_POSSIBLE_REWARDS + "," + UPDATED_TOTAL_POSSIBLE_REWARDS);

        // Get all the kpiRoundList where totalPossibleRewards equals to UPDATED_TOTAL_POSSIBLE_REWARDS
        defaultKpiRoundShouldNotBeFound("totalPossibleRewards.in=" + UPDATED_TOTAL_POSSIBLE_REWARDS);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByTotalPossibleRewardsIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where totalPossibleRewards is not null
        defaultKpiRoundShouldBeFound("totalPossibleRewards.specified=true");

        // Get all the kpiRoundList where totalPossibleRewards is null
        defaultKpiRoundShouldNotBeFound("totalPossibleRewards.specified=false");
    }

    @Test
    @Transactional
    void getAllKpiRoundsByTotalPossibleRewardsContainsSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where totalPossibleRewards contains DEFAULT_TOTAL_POSSIBLE_REWARDS
        defaultKpiRoundShouldBeFound("totalPossibleRewards.contains=" + DEFAULT_TOTAL_POSSIBLE_REWARDS);

        // Get all the kpiRoundList where totalPossibleRewards contains UPDATED_TOTAL_POSSIBLE_REWARDS
        defaultKpiRoundShouldNotBeFound("totalPossibleRewards.contains=" + UPDATED_TOTAL_POSSIBLE_REWARDS);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByTotalPossibleRewardsNotContainsSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where totalPossibleRewards does not contain DEFAULT_TOTAL_POSSIBLE_REWARDS
        defaultKpiRoundShouldNotBeFound("totalPossibleRewards.doesNotContain=" + DEFAULT_TOTAL_POSSIBLE_REWARDS);

        // Get all the kpiRoundList where totalPossibleRewards does not contain UPDATED_TOTAL_POSSIBLE_REWARDS
        defaultKpiRoundShouldBeFound("totalPossibleRewards.doesNotContain=" + UPDATED_TOTAL_POSSIBLE_REWARDS);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByCouncilElectedInRoundIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where councilElectedInRound equals to DEFAULT_COUNCIL_ELECTED_IN_ROUND
        defaultKpiRoundShouldBeFound("councilElectedInRound.equals=" + DEFAULT_COUNCIL_ELECTED_IN_ROUND);

        // Get all the kpiRoundList where councilElectedInRound equals to UPDATED_COUNCIL_ELECTED_IN_ROUND
        defaultKpiRoundShouldNotBeFound("councilElectedInRound.equals=" + UPDATED_COUNCIL_ELECTED_IN_ROUND);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByCouncilElectedInRoundIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where councilElectedInRound not equals to DEFAULT_COUNCIL_ELECTED_IN_ROUND
        defaultKpiRoundShouldNotBeFound("councilElectedInRound.notEquals=" + DEFAULT_COUNCIL_ELECTED_IN_ROUND);

        // Get all the kpiRoundList where councilElectedInRound not equals to UPDATED_COUNCIL_ELECTED_IN_ROUND
        defaultKpiRoundShouldBeFound("councilElectedInRound.notEquals=" + UPDATED_COUNCIL_ELECTED_IN_ROUND);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByCouncilElectedInRoundIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where councilElectedInRound in DEFAULT_COUNCIL_ELECTED_IN_ROUND or UPDATED_COUNCIL_ELECTED_IN_ROUND
        defaultKpiRoundShouldBeFound(
            "councilElectedInRound.in=" + DEFAULT_COUNCIL_ELECTED_IN_ROUND + "," + UPDATED_COUNCIL_ELECTED_IN_ROUND
        );

        // Get all the kpiRoundList where councilElectedInRound equals to UPDATED_COUNCIL_ELECTED_IN_ROUND
        defaultKpiRoundShouldNotBeFound("councilElectedInRound.in=" + UPDATED_COUNCIL_ELECTED_IN_ROUND);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByCouncilElectedInRoundIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where councilElectedInRound is not null
        defaultKpiRoundShouldBeFound("councilElectedInRound.specified=true");

        // Get all the kpiRoundList where councilElectedInRound is null
        defaultKpiRoundShouldNotBeFound("councilElectedInRound.specified=false");
    }

    @Test
    @Transactional
    void getAllKpiRoundsByCouncilElectedInRoundIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where councilElectedInRound is greater than or equal to DEFAULT_COUNCIL_ELECTED_IN_ROUND
        defaultKpiRoundShouldBeFound("councilElectedInRound.greaterThanOrEqual=" + DEFAULT_COUNCIL_ELECTED_IN_ROUND);

        // Get all the kpiRoundList where councilElectedInRound is greater than or equal to UPDATED_COUNCIL_ELECTED_IN_ROUND
        defaultKpiRoundShouldNotBeFound("councilElectedInRound.greaterThanOrEqual=" + UPDATED_COUNCIL_ELECTED_IN_ROUND);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByCouncilElectedInRoundIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where councilElectedInRound is less than or equal to DEFAULT_COUNCIL_ELECTED_IN_ROUND
        defaultKpiRoundShouldBeFound("councilElectedInRound.lessThanOrEqual=" + DEFAULT_COUNCIL_ELECTED_IN_ROUND);

        // Get all the kpiRoundList where councilElectedInRound is less than or equal to SMALLER_COUNCIL_ELECTED_IN_ROUND
        defaultKpiRoundShouldNotBeFound("councilElectedInRound.lessThanOrEqual=" + SMALLER_COUNCIL_ELECTED_IN_ROUND);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByCouncilElectedInRoundIsLessThanSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where councilElectedInRound is less than DEFAULT_COUNCIL_ELECTED_IN_ROUND
        defaultKpiRoundShouldNotBeFound("councilElectedInRound.lessThan=" + DEFAULT_COUNCIL_ELECTED_IN_ROUND);

        // Get all the kpiRoundList where councilElectedInRound is less than UPDATED_COUNCIL_ELECTED_IN_ROUND
        defaultKpiRoundShouldBeFound("councilElectedInRound.lessThan=" + UPDATED_COUNCIL_ELECTED_IN_ROUND);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByCouncilElectedInRoundIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where councilElectedInRound is greater than DEFAULT_COUNCIL_ELECTED_IN_ROUND
        defaultKpiRoundShouldNotBeFound("councilElectedInRound.greaterThan=" + DEFAULT_COUNCIL_ELECTED_IN_ROUND);

        // Get all the kpiRoundList where councilElectedInRound is greater than SMALLER_COUNCIL_ELECTED_IN_ROUND
        defaultKpiRoundShouldBeFound("councilElectedInRound.greaterThan=" + SMALLER_COUNCIL_ELECTED_IN_ROUND);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByCouncilMembersIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where councilMembers equals to DEFAULT_COUNCIL_MEMBERS
        defaultKpiRoundShouldBeFound("councilMembers.equals=" + DEFAULT_COUNCIL_MEMBERS);

        // Get all the kpiRoundList where councilMembers equals to UPDATED_COUNCIL_MEMBERS
        defaultKpiRoundShouldNotBeFound("councilMembers.equals=" + UPDATED_COUNCIL_MEMBERS);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByCouncilMembersIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where councilMembers not equals to DEFAULT_COUNCIL_MEMBERS
        defaultKpiRoundShouldNotBeFound("councilMembers.notEquals=" + DEFAULT_COUNCIL_MEMBERS);

        // Get all the kpiRoundList where councilMembers not equals to UPDATED_COUNCIL_MEMBERS
        defaultKpiRoundShouldBeFound("councilMembers.notEquals=" + UPDATED_COUNCIL_MEMBERS);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByCouncilMembersIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where councilMembers in DEFAULT_COUNCIL_MEMBERS or UPDATED_COUNCIL_MEMBERS
        defaultKpiRoundShouldBeFound("councilMembers.in=" + DEFAULT_COUNCIL_MEMBERS + "," + UPDATED_COUNCIL_MEMBERS);

        // Get all the kpiRoundList where councilMembers equals to UPDATED_COUNCIL_MEMBERS
        defaultKpiRoundShouldNotBeFound("councilMembers.in=" + UPDATED_COUNCIL_MEMBERS);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByCouncilMembersIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where councilMembers is not null
        defaultKpiRoundShouldBeFound("councilMembers.specified=true");

        // Get all the kpiRoundList where councilMembers is null
        defaultKpiRoundShouldNotBeFound("councilMembers.specified=false");
    }

    @Test
    @Transactional
    void getAllKpiRoundsByCouncilMembersIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where councilMembers is greater than or equal to DEFAULT_COUNCIL_MEMBERS
        defaultKpiRoundShouldBeFound("councilMembers.greaterThanOrEqual=" + DEFAULT_COUNCIL_MEMBERS);

        // Get all the kpiRoundList where councilMembers is greater than or equal to UPDATED_COUNCIL_MEMBERS
        defaultKpiRoundShouldNotBeFound("councilMembers.greaterThanOrEqual=" + UPDATED_COUNCIL_MEMBERS);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByCouncilMembersIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where councilMembers is less than or equal to DEFAULT_COUNCIL_MEMBERS
        defaultKpiRoundShouldBeFound("councilMembers.lessThanOrEqual=" + DEFAULT_COUNCIL_MEMBERS);

        // Get all the kpiRoundList where councilMembers is less than or equal to SMALLER_COUNCIL_MEMBERS
        defaultKpiRoundShouldNotBeFound("councilMembers.lessThanOrEqual=" + SMALLER_COUNCIL_MEMBERS);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByCouncilMembersIsLessThanSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where councilMembers is less than DEFAULT_COUNCIL_MEMBERS
        defaultKpiRoundShouldNotBeFound("councilMembers.lessThan=" + DEFAULT_COUNCIL_MEMBERS);

        // Get all the kpiRoundList where councilMembers is less than UPDATED_COUNCIL_MEMBERS
        defaultKpiRoundShouldBeFound("councilMembers.lessThan=" + UPDATED_COUNCIL_MEMBERS);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByCouncilMembersIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where councilMembers is greater than DEFAULT_COUNCIL_MEMBERS
        defaultKpiRoundShouldNotBeFound("councilMembers.greaterThan=" + DEFAULT_COUNCIL_MEMBERS);

        // Get all the kpiRoundList where councilMembers is greater than SMALLER_COUNCIL_MEMBERS
        defaultKpiRoundShouldBeFound("councilMembers.greaterThan=" + SMALLER_COUNCIL_MEMBERS);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByTermLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where termLength equals to DEFAULT_TERM_LENGTH
        defaultKpiRoundShouldBeFound("termLength.equals=" + DEFAULT_TERM_LENGTH);

        // Get all the kpiRoundList where termLength equals to UPDATED_TERM_LENGTH
        defaultKpiRoundShouldNotBeFound("termLength.equals=" + UPDATED_TERM_LENGTH);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByTermLengthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where termLength not equals to DEFAULT_TERM_LENGTH
        defaultKpiRoundShouldNotBeFound("termLength.notEquals=" + DEFAULT_TERM_LENGTH);

        // Get all the kpiRoundList where termLength not equals to UPDATED_TERM_LENGTH
        defaultKpiRoundShouldBeFound("termLength.notEquals=" + UPDATED_TERM_LENGTH);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByTermLengthIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where termLength in DEFAULT_TERM_LENGTH or UPDATED_TERM_LENGTH
        defaultKpiRoundShouldBeFound("termLength.in=" + DEFAULT_TERM_LENGTH + "," + UPDATED_TERM_LENGTH);

        // Get all the kpiRoundList where termLength equals to UPDATED_TERM_LENGTH
        defaultKpiRoundShouldNotBeFound("termLength.in=" + UPDATED_TERM_LENGTH);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByTermLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where termLength is not null
        defaultKpiRoundShouldBeFound("termLength.specified=true");

        // Get all the kpiRoundList where termLength is null
        defaultKpiRoundShouldNotBeFound("termLength.specified=false");
    }

    @Test
    @Transactional
    void getAllKpiRoundsByTermLengthContainsSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where termLength contains DEFAULT_TERM_LENGTH
        defaultKpiRoundShouldBeFound("termLength.contains=" + DEFAULT_TERM_LENGTH);

        // Get all the kpiRoundList where termLength contains UPDATED_TERM_LENGTH
        defaultKpiRoundShouldNotBeFound("termLength.contains=" + UPDATED_TERM_LENGTH);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByTermLengthNotContainsSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where termLength does not contain DEFAULT_TERM_LENGTH
        defaultKpiRoundShouldNotBeFound("termLength.doesNotContain=" + DEFAULT_TERM_LENGTH);

        // Get all the kpiRoundList where termLength does not contain UPDATED_TERM_LENGTH
        defaultKpiRoundShouldBeFound("termLength.doesNotContain=" + UPDATED_TERM_LENGTH);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByStartBlockIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where startBlock equals to DEFAULT_START_BLOCK
        defaultKpiRoundShouldBeFound("startBlock.equals=" + DEFAULT_START_BLOCK);

        // Get all the kpiRoundList where startBlock equals to UPDATED_START_BLOCK
        defaultKpiRoundShouldNotBeFound("startBlock.equals=" + UPDATED_START_BLOCK);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByStartBlockIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where startBlock not equals to DEFAULT_START_BLOCK
        defaultKpiRoundShouldNotBeFound("startBlock.notEquals=" + DEFAULT_START_BLOCK);

        // Get all the kpiRoundList where startBlock not equals to UPDATED_START_BLOCK
        defaultKpiRoundShouldBeFound("startBlock.notEquals=" + UPDATED_START_BLOCK);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByStartBlockIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where startBlock in DEFAULT_START_BLOCK or UPDATED_START_BLOCK
        defaultKpiRoundShouldBeFound("startBlock.in=" + DEFAULT_START_BLOCK + "," + UPDATED_START_BLOCK);

        // Get all the kpiRoundList where startBlock equals to UPDATED_START_BLOCK
        defaultKpiRoundShouldNotBeFound("startBlock.in=" + UPDATED_START_BLOCK);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByStartBlockIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where startBlock is not null
        defaultKpiRoundShouldBeFound("startBlock.specified=true");

        // Get all the kpiRoundList where startBlock is null
        defaultKpiRoundShouldNotBeFound("startBlock.specified=false");
    }

    @Test
    @Transactional
    void getAllKpiRoundsByStartBlockIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where startBlock is greater than or equal to DEFAULT_START_BLOCK
        defaultKpiRoundShouldBeFound("startBlock.greaterThanOrEqual=" + DEFAULT_START_BLOCK);

        // Get all the kpiRoundList where startBlock is greater than or equal to UPDATED_START_BLOCK
        defaultKpiRoundShouldNotBeFound("startBlock.greaterThanOrEqual=" + UPDATED_START_BLOCK);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByStartBlockIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where startBlock is less than or equal to DEFAULT_START_BLOCK
        defaultKpiRoundShouldBeFound("startBlock.lessThanOrEqual=" + DEFAULT_START_BLOCK);

        // Get all the kpiRoundList where startBlock is less than or equal to SMALLER_START_BLOCK
        defaultKpiRoundShouldNotBeFound("startBlock.lessThanOrEqual=" + SMALLER_START_BLOCK);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByStartBlockIsLessThanSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where startBlock is less than DEFAULT_START_BLOCK
        defaultKpiRoundShouldNotBeFound("startBlock.lessThan=" + DEFAULT_START_BLOCK);

        // Get all the kpiRoundList where startBlock is less than UPDATED_START_BLOCK
        defaultKpiRoundShouldBeFound("startBlock.lessThan=" + UPDATED_START_BLOCK);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByStartBlockIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where startBlock is greater than DEFAULT_START_BLOCK
        defaultKpiRoundShouldNotBeFound("startBlock.greaterThan=" + DEFAULT_START_BLOCK);

        // Get all the kpiRoundList where startBlock is greater than SMALLER_START_BLOCK
        defaultKpiRoundShouldBeFound("startBlock.greaterThan=" + SMALLER_START_BLOCK);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where startDate equals to DEFAULT_START_DATE
        defaultKpiRoundShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the kpiRoundList where startDate equals to UPDATED_START_DATE
        defaultKpiRoundShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where startDate not equals to DEFAULT_START_DATE
        defaultKpiRoundShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the kpiRoundList where startDate not equals to UPDATED_START_DATE
        defaultKpiRoundShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultKpiRoundShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the kpiRoundList where startDate equals to UPDATED_START_DATE
        defaultKpiRoundShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where startDate is not null
        defaultKpiRoundShouldBeFound("startDate.specified=true");

        // Get all the kpiRoundList where startDate is null
        defaultKpiRoundShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKpiRoundsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultKpiRoundShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the kpiRoundList where startDate is greater than or equal to UPDATED_START_DATE
        defaultKpiRoundShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where startDate is less than or equal to DEFAULT_START_DATE
        defaultKpiRoundShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the kpiRoundList where startDate is less than or equal to SMALLER_START_DATE
        defaultKpiRoundShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where startDate is less than DEFAULT_START_DATE
        defaultKpiRoundShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the kpiRoundList where startDate is less than UPDATED_START_DATE
        defaultKpiRoundShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where startDate is greater than DEFAULT_START_DATE
        defaultKpiRoundShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the kpiRoundList where startDate is greater than SMALLER_START_DATE
        defaultKpiRoundShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByEndBlockIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where endBlock equals to DEFAULT_END_BLOCK
        defaultKpiRoundShouldBeFound("endBlock.equals=" + DEFAULT_END_BLOCK);

        // Get all the kpiRoundList where endBlock equals to UPDATED_END_BLOCK
        defaultKpiRoundShouldNotBeFound("endBlock.equals=" + UPDATED_END_BLOCK);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByEndBlockIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where endBlock not equals to DEFAULT_END_BLOCK
        defaultKpiRoundShouldNotBeFound("endBlock.notEquals=" + DEFAULT_END_BLOCK);

        // Get all the kpiRoundList where endBlock not equals to UPDATED_END_BLOCK
        defaultKpiRoundShouldBeFound("endBlock.notEquals=" + UPDATED_END_BLOCK);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByEndBlockIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where endBlock in DEFAULT_END_BLOCK or UPDATED_END_BLOCK
        defaultKpiRoundShouldBeFound("endBlock.in=" + DEFAULT_END_BLOCK + "," + UPDATED_END_BLOCK);

        // Get all the kpiRoundList where endBlock equals to UPDATED_END_BLOCK
        defaultKpiRoundShouldNotBeFound("endBlock.in=" + UPDATED_END_BLOCK);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByEndBlockIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where endBlock is not null
        defaultKpiRoundShouldBeFound("endBlock.specified=true");

        // Get all the kpiRoundList where endBlock is null
        defaultKpiRoundShouldNotBeFound("endBlock.specified=false");
    }

    @Test
    @Transactional
    void getAllKpiRoundsByEndBlockContainsSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where endBlock contains DEFAULT_END_BLOCK
        defaultKpiRoundShouldBeFound("endBlock.contains=" + DEFAULT_END_BLOCK);

        // Get all the kpiRoundList where endBlock contains UPDATED_END_BLOCK
        defaultKpiRoundShouldNotBeFound("endBlock.contains=" + UPDATED_END_BLOCK);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByEndBlockNotContainsSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where endBlock does not contain DEFAULT_END_BLOCK
        defaultKpiRoundShouldNotBeFound("endBlock.doesNotContain=" + DEFAULT_END_BLOCK);

        // Get all the kpiRoundList where endBlock does not contain UPDATED_END_BLOCK
        defaultKpiRoundShouldBeFound("endBlock.doesNotContain=" + UPDATED_END_BLOCK);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where endDate equals to DEFAULT_END_DATE
        defaultKpiRoundShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the kpiRoundList where endDate equals to UPDATED_END_DATE
        defaultKpiRoundShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where endDate not equals to DEFAULT_END_DATE
        defaultKpiRoundShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the kpiRoundList where endDate not equals to UPDATED_END_DATE
        defaultKpiRoundShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultKpiRoundShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the kpiRoundList where endDate equals to UPDATED_END_DATE
        defaultKpiRoundShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where endDate is not null
        defaultKpiRoundShouldBeFound("endDate.specified=true");

        // Get all the kpiRoundList where endDate is null
        defaultKpiRoundShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKpiRoundsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where endDate is greater than or equal to DEFAULT_END_DATE
        defaultKpiRoundShouldBeFound("endDate.greaterThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the kpiRoundList where endDate is greater than or equal to UPDATED_END_DATE
        defaultKpiRoundShouldNotBeFound("endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where endDate is less than or equal to DEFAULT_END_DATE
        defaultKpiRoundShouldBeFound("endDate.lessThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the kpiRoundList where endDate is less than or equal to SMALLER_END_DATE
        defaultKpiRoundShouldNotBeFound("endDate.lessThanOrEqual=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where endDate is less than DEFAULT_END_DATE
        defaultKpiRoundShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the kpiRoundList where endDate is less than UPDATED_END_DATE
        defaultKpiRoundShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where endDate is greater than DEFAULT_END_DATE
        defaultKpiRoundShouldNotBeFound("endDate.greaterThan=" + DEFAULT_END_DATE);

        // Get all the kpiRoundList where endDate is greater than SMALLER_END_DATE
        defaultKpiRoundShouldBeFound("endDate.greaterThan=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByTermSummaryIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where termSummary equals to DEFAULT_TERM_SUMMARY
        defaultKpiRoundShouldBeFound("termSummary.equals=" + DEFAULT_TERM_SUMMARY);

        // Get all the kpiRoundList where termSummary equals to UPDATED_TERM_SUMMARY
        defaultKpiRoundShouldNotBeFound("termSummary.equals=" + UPDATED_TERM_SUMMARY);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByTermSummaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where termSummary not equals to DEFAULT_TERM_SUMMARY
        defaultKpiRoundShouldNotBeFound("termSummary.notEquals=" + DEFAULT_TERM_SUMMARY);

        // Get all the kpiRoundList where termSummary not equals to UPDATED_TERM_SUMMARY
        defaultKpiRoundShouldBeFound("termSummary.notEquals=" + UPDATED_TERM_SUMMARY);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByTermSummaryIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where termSummary in DEFAULT_TERM_SUMMARY or UPDATED_TERM_SUMMARY
        defaultKpiRoundShouldBeFound("termSummary.in=" + DEFAULT_TERM_SUMMARY + "," + UPDATED_TERM_SUMMARY);

        // Get all the kpiRoundList where termSummary equals to UPDATED_TERM_SUMMARY
        defaultKpiRoundShouldNotBeFound("termSummary.in=" + UPDATED_TERM_SUMMARY);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByTermSummaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where termSummary is not null
        defaultKpiRoundShouldBeFound("termSummary.specified=true");

        // Get all the kpiRoundList where termSummary is null
        defaultKpiRoundShouldNotBeFound("termSummary.specified=false");
    }

    @Test
    @Transactional
    void getAllKpiRoundsByTermSummaryContainsSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where termSummary contains DEFAULT_TERM_SUMMARY
        defaultKpiRoundShouldBeFound("termSummary.contains=" + DEFAULT_TERM_SUMMARY);

        // Get all the kpiRoundList where termSummary contains UPDATED_TERM_SUMMARY
        defaultKpiRoundShouldNotBeFound("termSummary.contains=" + UPDATED_TERM_SUMMARY);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByTermSummaryNotContainsSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where termSummary does not contain DEFAULT_TERM_SUMMARY
        defaultKpiRoundShouldNotBeFound("termSummary.doesNotContain=" + DEFAULT_TERM_SUMMARY);

        // Get all the kpiRoundList where termSummary does not contain UPDATED_TERM_SUMMARY
        defaultKpiRoundShouldBeFound("termSummary.doesNotContain=" + UPDATED_TERM_SUMMARY);
    }

    @Test
    @Transactional
    void getAllKpiRoundsBySummarySubmissionDeadlineIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where summarySubmissionDeadline equals to DEFAULT_SUMMARY_SUBMISSION_DEADLINE
        defaultKpiRoundShouldBeFound("summarySubmissionDeadline.equals=" + DEFAULT_SUMMARY_SUBMISSION_DEADLINE);

        // Get all the kpiRoundList where summarySubmissionDeadline equals to UPDATED_SUMMARY_SUBMISSION_DEADLINE
        defaultKpiRoundShouldNotBeFound("summarySubmissionDeadline.equals=" + UPDATED_SUMMARY_SUBMISSION_DEADLINE);
    }

    @Test
    @Transactional
    void getAllKpiRoundsBySummarySubmissionDeadlineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where summarySubmissionDeadline not equals to DEFAULT_SUMMARY_SUBMISSION_DEADLINE
        defaultKpiRoundShouldNotBeFound("summarySubmissionDeadline.notEquals=" + DEFAULT_SUMMARY_SUBMISSION_DEADLINE);

        // Get all the kpiRoundList where summarySubmissionDeadline not equals to UPDATED_SUMMARY_SUBMISSION_DEADLINE
        defaultKpiRoundShouldBeFound("summarySubmissionDeadline.notEquals=" + UPDATED_SUMMARY_SUBMISSION_DEADLINE);
    }

    @Test
    @Transactional
    void getAllKpiRoundsBySummarySubmissionDeadlineIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where summarySubmissionDeadline in DEFAULT_SUMMARY_SUBMISSION_DEADLINE or UPDATED_SUMMARY_SUBMISSION_DEADLINE
        defaultKpiRoundShouldBeFound(
            "summarySubmissionDeadline.in=" + DEFAULT_SUMMARY_SUBMISSION_DEADLINE + "," + UPDATED_SUMMARY_SUBMISSION_DEADLINE
        );

        // Get all the kpiRoundList where summarySubmissionDeadline equals to UPDATED_SUMMARY_SUBMISSION_DEADLINE
        defaultKpiRoundShouldNotBeFound("summarySubmissionDeadline.in=" + UPDATED_SUMMARY_SUBMISSION_DEADLINE);
    }

    @Test
    @Transactional
    void getAllKpiRoundsBySummarySubmissionDeadlineIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where summarySubmissionDeadline is not null
        defaultKpiRoundShouldBeFound("summarySubmissionDeadline.specified=true");

        // Get all the kpiRoundList where summarySubmissionDeadline is null
        defaultKpiRoundShouldNotBeFound("summarySubmissionDeadline.specified=false");
    }

    @Test
    @Transactional
    void getAllKpiRoundsBySummarySubmissionDeadlineContainsSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where summarySubmissionDeadline contains DEFAULT_SUMMARY_SUBMISSION_DEADLINE
        defaultKpiRoundShouldBeFound("summarySubmissionDeadline.contains=" + DEFAULT_SUMMARY_SUBMISSION_DEADLINE);

        // Get all the kpiRoundList where summarySubmissionDeadline contains UPDATED_SUMMARY_SUBMISSION_DEADLINE
        defaultKpiRoundShouldNotBeFound("summarySubmissionDeadline.contains=" + UPDATED_SUMMARY_SUBMISSION_DEADLINE);
    }

    @Test
    @Transactional
    void getAllKpiRoundsBySummarySubmissionDeadlineNotContainsSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where summarySubmissionDeadline does not contain DEFAULT_SUMMARY_SUBMISSION_DEADLINE
        defaultKpiRoundShouldNotBeFound("summarySubmissionDeadline.doesNotContain=" + DEFAULT_SUMMARY_SUBMISSION_DEADLINE);

        // Get all the kpiRoundList where summarySubmissionDeadline does not contain UPDATED_SUMMARY_SUBMISSION_DEADLINE
        defaultKpiRoundShouldBeFound("summarySubmissionDeadline.doesNotContain=" + UPDATED_SUMMARY_SUBMISSION_DEADLINE);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByMaxFiatPoolDifferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where maxFiatPoolDifference equals to DEFAULT_MAX_FIAT_POOL_DIFFERENCE
        defaultKpiRoundShouldBeFound("maxFiatPoolDifference.equals=" + DEFAULT_MAX_FIAT_POOL_DIFFERENCE);

        // Get all the kpiRoundList where maxFiatPoolDifference equals to UPDATED_MAX_FIAT_POOL_DIFFERENCE
        defaultKpiRoundShouldNotBeFound("maxFiatPoolDifference.equals=" + UPDATED_MAX_FIAT_POOL_DIFFERENCE);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByMaxFiatPoolDifferenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where maxFiatPoolDifference not equals to DEFAULT_MAX_FIAT_POOL_DIFFERENCE
        defaultKpiRoundShouldNotBeFound("maxFiatPoolDifference.notEquals=" + DEFAULT_MAX_FIAT_POOL_DIFFERENCE);

        // Get all the kpiRoundList where maxFiatPoolDifference not equals to UPDATED_MAX_FIAT_POOL_DIFFERENCE
        defaultKpiRoundShouldBeFound("maxFiatPoolDifference.notEquals=" + UPDATED_MAX_FIAT_POOL_DIFFERENCE);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByMaxFiatPoolDifferenceIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where maxFiatPoolDifference in DEFAULT_MAX_FIAT_POOL_DIFFERENCE or UPDATED_MAX_FIAT_POOL_DIFFERENCE
        defaultKpiRoundShouldBeFound(
            "maxFiatPoolDifference.in=" + DEFAULT_MAX_FIAT_POOL_DIFFERENCE + "," + UPDATED_MAX_FIAT_POOL_DIFFERENCE
        );

        // Get all the kpiRoundList where maxFiatPoolDifference equals to UPDATED_MAX_FIAT_POOL_DIFFERENCE
        defaultKpiRoundShouldNotBeFound("maxFiatPoolDifference.in=" + UPDATED_MAX_FIAT_POOL_DIFFERENCE);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByMaxFiatPoolDifferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where maxFiatPoolDifference is not null
        defaultKpiRoundShouldBeFound("maxFiatPoolDifference.specified=true");

        // Get all the kpiRoundList where maxFiatPoolDifference is null
        defaultKpiRoundShouldNotBeFound("maxFiatPoolDifference.specified=false");
    }

    @Test
    @Transactional
    void getAllKpiRoundsByMaxFiatPoolDifferenceContainsSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where maxFiatPoolDifference contains DEFAULT_MAX_FIAT_POOL_DIFFERENCE
        defaultKpiRoundShouldBeFound("maxFiatPoolDifference.contains=" + DEFAULT_MAX_FIAT_POOL_DIFFERENCE);

        // Get all the kpiRoundList where maxFiatPoolDifference contains UPDATED_MAX_FIAT_POOL_DIFFERENCE
        defaultKpiRoundShouldNotBeFound("maxFiatPoolDifference.contains=" + UPDATED_MAX_FIAT_POOL_DIFFERENCE);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByMaxFiatPoolDifferenceNotContainsSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where maxFiatPoolDifference does not contain DEFAULT_MAX_FIAT_POOL_DIFFERENCE
        defaultKpiRoundShouldNotBeFound("maxFiatPoolDifference.doesNotContain=" + DEFAULT_MAX_FIAT_POOL_DIFFERENCE);

        // Get all the kpiRoundList where maxFiatPoolDifference does not contain UPDATED_MAX_FIAT_POOL_DIFFERENCE
        defaultKpiRoundShouldBeFound("maxFiatPoolDifference.doesNotContain=" + UPDATED_MAX_FIAT_POOL_DIFFERENCE);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByNumberOfKpisIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where numberOfKpis equals to DEFAULT_NUMBER_OF_KPIS
        defaultKpiRoundShouldBeFound("numberOfKpis.equals=" + DEFAULT_NUMBER_OF_KPIS);

        // Get all the kpiRoundList where numberOfKpis equals to UPDATED_NUMBER_OF_KPIS
        defaultKpiRoundShouldNotBeFound("numberOfKpis.equals=" + UPDATED_NUMBER_OF_KPIS);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByNumberOfKpisIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where numberOfKpis not equals to DEFAULT_NUMBER_OF_KPIS
        defaultKpiRoundShouldNotBeFound("numberOfKpis.notEquals=" + DEFAULT_NUMBER_OF_KPIS);

        // Get all the kpiRoundList where numberOfKpis not equals to UPDATED_NUMBER_OF_KPIS
        defaultKpiRoundShouldBeFound("numberOfKpis.notEquals=" + UPDATED_NUMBER_OF_KPIS);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByNumberOfKpisIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where numberOfKpis in DEFAULT_NUMBER_OF_KPIS or UPDATED_NUMBER_OF_KPIS
        defaultKpiRoundShouldBeFound("numberOfKpis.in=" + DEFAULT_NUMBER_OF_KPIS + "," + UPDATED_NUMBER_OF_KPIS);

        // Get all the kpiRoundList where numberOfKpis equals to UPDATED_NUMBER_OF_KPIS
        defaultKpiRoundShouldNotBeFound("numberOfKpis.in=" + UPDATED_NUMBER_OF_KPIS);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByNumberOfKpisIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where numberOfKpis is not null
        defaultKpiRoundShouldBeFound("numberOfKpis.specified=true");

        // Get all the kpiRoundList where numberOfKpis is null
        defaultKpiRoundShouldNotBeFound("numberOfKpis.specified=false");
    }

    @Test
    @Transactional
    void getAllKpiRoundsByNumberOfKpisContainsSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where numberOfKpis contains DEFAULT_NUMBER_OF_KPIS
        defaultKpiRoundShouldBeFound("numberOfKpis.contains=" + DEFAULT_NUMBER_OF_KPIS);

        // Get all the kpiRoundList where numberOfKpis contains UPDATED_NUMBER_OF_KPIS
        defaultKpiRoundShouldNotBeFound("numberOfKpis.contains=" + UPDATED_NUMBER_OF_KPIS);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByNumberOfKpisNotContainsSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where numberOfKpis does not contain DEFAULT_NUMBER_OF_KPIS
        defaultKpiRoundShouldNotBeFound("numberOfKpis.doesNotContain=" + DEFAULT_NUMBER_OF_KPIS);

        // Get all the kpiRoundList where numberOfKpis does not contain UPDATED_NUMBER_OF_KPIS
        defaultKpiRoundShouldBeFound("numberOfKpis.doesNotContain=" + UPDATED_NUMBER_OF_KPIS);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where notes equals to DEFAULT_NOTES
        defaultKpiRoundShouldBeFound("notes.equals=" + DEFAULT_NOTES);

        // Get all the kpiRoundList where notes equals to UPDATED_NOTES
        defaultKpiRoundShouldNotBeFound("notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByNotesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where notes not equals to DEFAULT_NOTES
        defaultKpiRoundShouldNotBeFound("notes.notEquals=" + DEFAULT_NOTES);

        // Get all the kpiRoundList where notes not equals to UPDATED_NOTES
        defaultKpiRoundShouldBeFound("notes.notEquals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where notes in DEFAULT_NOTES or UPDATED_NOTES
        defaultKpiRoundShouldBeFound("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES);

        // Get all the kpiRoundList where notes equals to UPDATED_NOTES
        defaultKpiRoundShouldNotBeFound("notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where notes is not null
        defaultKpiRoundShouldBeFound("notes.specified=true");

        // Get all the kpiRoundList where notes is null
        defaultKpiRoundShouldNotBeFound("notes.specified=false");
    }

    @Test
    @Transactional
    void getAllKpiRoundsByNotesContainsSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where notes contains DEFAULT_NOTES
        defaultKpiRoundShouldBeFound("notes.contains=" + DEFAULT_NOTES);

        // Get all the kpiRoundList where notes contains UPDATED_NOTES
        defaultKpiRoundShouldNotBeFound("notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        // Get all the kpiRoundList where notes does not contain DEFAULT_NOTES
        defaultKpiRoundShouldNotBeFound("notes.doesNotContain=" + DEFAULT_NOTES);

        // Get all the kpiRoundList where notes does not contain UPDATED_NOTES
        defaultKpiRoundShouldBeFound("notes.doesNotContain=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllKpiRoundsByKpisIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);
        Kpi kpis;
        if (TestUtil.findAll(em, Kpi.class).isEmpty()) {
            kpis = KpiResourceIT.createEntity(em);
            em.persist(kpis);
            em.flush();
        } else {
            kpis = TestUtil.findAll(em, Kpi.class).get(0);
        }
        em.persist(kpis);
        em.flush();
        kpiRound.addKpis(kpis);
        kpiRoundRepository.saveAndFlush(kpiRound);
        Long kpisId = kpis.getId();

        // Get all the kpiRoundList where kpis equals to kpisId
        defaultKpiRoundShouldBeFound("kpisId.equals=" + kpisId);

        // Get all the kpiRoundList where kpis equals to (kpisId + 1)
        defaultKpiRoundShouldNotBeFound("kpisId.equals=" + (kpisId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKpiRoundShouldBeFound(String filter) throws Exception {
        restKpiRoundMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kpiRound.getId().intValue())))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME)))
            .andExpect(jsonPath("$.[*].totalPossibleRewards").value(hasItem(DEFAULT_TOTAL_POSSIBLE_REWARDS)))
            .andExpect(jsonPath("$.[*].councilElectedInRound").value(hasItem(DEFAULT_COUNCIL_ELECTED_IN_ROUND)))
            .andExpect(jsonPath("$.[*].councilMembers").value(hasItem(DEFAULT_COUNCIL_MEMBERS)))
            .andExpect(jsonPath("$.[*].termLength").value(hasItem(DEFAULT_TERM_LENGTH)))
            .andExpect(jsonPath("$.[*].startBlock").value(hasItem(DEFAULT_START_BLOCK)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endBlock").value(hasItem(DEFAULT_END_BLOCK)))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].termSummary").value(hasItem(DEFAULT_TERM_SUMMARY)))
            .andExpect(jsonPath("$.[*].summarySubmissionDeadline").value(hasItem(DEFAULT_SUMMARY_SUBMISSION_DEADLINE)))
            .andExpect(jsonPath("$.[*].maxFiatPoolDifference").value(hasItem(DEFAULT_MAX_FIAT_POOL_DIFFERENCE)))
            .andExpect(jsonPath("$.[*].numberOfKpis").value(hasItem(DEFAULT_NUMBER_OF_KPIS)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));

        // Check, that the count call also returns 1
        restKpiRoundMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKpiRoundShouldNotBeFound(String filter) throws Exception {
        restKpiRoundMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKpiRoundMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingKpiRound() throws Exception {
        // Get the kpiRound
        restKpiRoundMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewKpiRound() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        int databaseSizeBeforeUpdate = kpiRoundRepository.findAll().size();

        // Update the kpiRound
        KpiRound updatedKpiRound = kpiRoundRepository.findById(kpiRound.getId()).get();
        // Disconnect from session so that the updates on updatedKpiRound are not directly saved in db
        em.detach(updatedKpiRound);
        updatedKpiRound
            .displayName(UPDATED_DISPLAY_NAME)
            .totalPossibleRewards(UPDATED_TOTAL_POSSIBLE_REWARDS)
            .councilElectedInRound(UPDATED_COUNCIL_ELECTED_IN_ROUND)
            .councilMembers(UPDATED_COUNCIL_MEMBERS)
            .termLength(UPDATED_TERM_LENGTH)
            .startBlock(UPDATED_START_BLOCK)
            .startDate(UPDATED_START_DATE)
            .endBlock(UPDATED_END_BLOCK)
            .endDate(UPDATED_END_DATE)
            .termSummary(UPDATED_TERM_SUMMARY)
            .summarySubmissionDeadline(UPDATED_SUMMARY_SUBMISSION_DEADLINE)
            .maxFiatPoolDifference(UPDATED_MAX_FIAT_POOL_DIFFERENCE)
            .numberOfKpis(UPDATED_NUMBER_OF_KPIS)
            .notes(UPDATED_NOTES);

        restKpiRoundMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKpiRound.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKpiRound))
            )
            .andExpect(status().isOk());

        // Validate the KpiRound in the database
        List<KpiRound> kpiRoundList = kpiRoundRepository.findAll();
        assertThat(kpiRoundList).hasSize(databaseSizeBeforeUpdate);
        KpiRound testKpiRound = kpiRoundList.get(kpiRoundList.size() - 1);
        assertThat(testKpiRound.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testKpiRound.getTotalPossibleRewards()).isEqualTo(UPDATED_TOTAL_POSSIBLE_REWARDS);
        assertThat(testKpiRound.getCouncilElectedInRound()).isEqualTo(UPDATED_COUNCIL_ELECTED_IN_ROUND);
        assertThat(testKpiRound.getCouncilMembers()).isEqualTo(UPDATED_COUNCIL_MEMBERS);
        assertThat(testKpiRound.getTermLength()).isEqualTo(UPDATED_TERM_LENGTH);
        assertThat(testKpiRound.getStartBlock()).isEqualTo(UPDATED_START_BLOCK);
        assertThat(testKpiRound.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testKpiRound.getEndBlock()).isEqualTo(UPDATED_END_BLOCK);
        assertThat(testKpiRound.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testKpiRound.getTermSummary()).isEqualTo(UPDATED_TERM_SUMMARY);
        assertThat(testKpiRound.getSummarySubmissionDeadline()).isEqualTo(UPDATED_SUMMARY_SUBMISSION_DEADLINE);
        assertThat(testKpiRound.getMaxFiatPoolDifference()).isEqualTo(UPDATED_MAX_FIAT_POOL_DIFFERENCE);
        assertThat(testKpiRound.getNumberOfKpis()).isEqualTo(UPDATED_NUMBER_OF_KPIS);
        assertThat(testKpiRound.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void putNonExistingKpiRound() throws Exception {
        int databaseSizeBeforeUpdate = kpiRoundRepository.findAll().size();
        kpiRound.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKpiRoundMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kpiRound.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kpiRound))
            )
            .andExpect(status().isBadRequest());

        // Validate the KpiRound in the database
        List<KpiRound> kpiRoundList = kpiRoundRepository.findAll();
        assertThat(kpiRoundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKpiRound() throws Exception {
        int databaseSizeBeforeUpdate = kpiRoundRepository.findAll().size();
        kpiRound.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKpiRoundMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kpiRound))
            )
            .andExpect(status().isBadRequest());

        // Validate the KpiRound in the database
        List<KpiRound> kpiRoundList = kpiRoundRepository.findAll();
        assertThat(kpiRoundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKpiRound() throws Exception {
        int databaseSizeBeforeUpdate = kpiRoundRepository.findAll().size();
        kpiRound.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKpiRoundMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpiRound)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KpiRound in the database
        List<KpiRound> kpiRoundList = kpiRoundRepository.findAll();
        assertThat(kpiRoundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKpiRoundWithPatch() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        int databaseSizeBeforeUpdate = kpiRoundRepository.findAll().size();

        // Update the kpiRound using partial update
        KpiRound partialUpdatedKpiRound = new KpiRound();
        partialUpdatedKpiRound.setId(kpiRound.getId());

        partialUpdatedKpiRound
            .totalPossibleRewards(UPDATED_TOTAL_POSSIBLE_REWARDS)
            .councilElectedInRound(UPDATED_COUNCIL_ELECTED_IN_ROUND)
            .councilMembers(UPDATED_COUNCIL_MEMBERS)
            .startDate(UPDATED_START_DATE)
            .endBlock(UPDATED_END_BLOCK)
            .endDate(UPDATED_END_DATE)
            .termSummary(UPDATED_TERM_SUMMARY)
            .summarySubmissionDeadline(UPDATED_SUMMARY_SUBMISSION_DEADLINE)
            .notes(UPDATED_NOTES);

        restKpiRoundMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKpiRound.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKpiRound))
            )
            .andExpect(status().isOk());

        // Validate the KpiRound in the database
        List<KpiRound> kpiRoundList = kpiRoundRepository.findAll();
        assertThat(kpiRoundList).hasSize(databaseSizeBeforeUpdate);
        KpiRound testKpiRound = kpiRoundList.get(kpiRoundList.size() - 1);
        assertThat(testKpiRound.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testKpiRound.getTotalPossibleRewards()).isEqualTo(UPDATED_TOTAL_POSSIBLE_REWARDS);
        assertThat(testKpiRound.getCouncilElectedInRound()).isEqualTo(UPDATED_COUNCIL_ELECTED_IN_ROUND);
        assertThat(testKpiRound.getCouncilMembers()).isEqualTo(UPDATED_COUNCIL_MEMBERS);
        assertThat(testKpiRound.getTermLength()).isEqualTo(DEFAULT_TERM_LENGTH);
        assertThat(testKpiRound.getStartBlock()).isEqualTo(DEFAULT_START_BLOCK);
        assertThat(testKpiRound.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testKpiRound.getEndBlock()).isEqualTo(UPDATED_END_BLOCK);
        assertThat(testKpiRound.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testKpiRound.getTermSummary()).isEqualTo(UPDATED_TERM_SUMMARY);
        assertThat(testKpiRound.getSummarySubmissionDeadline()).isEqualTo(UPDATED_SUMMARY_SUBMISSION_DEADLINE);
        assertThat(testKpiRound.getMaxFiatPoolDifference()).isEqualTo(DEFAULT_MAX_FIAT_POOL_DIFFERENCE);
        assertThat(testKpiRound.getNumberOfKpis()).isEqualTo(DEFAULT_NUMBER_OF_KPIS);
        assertThat(testKpiRound.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void fullUpdateKpiRoundWithPatch() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        int databaseSizeBeforeUpdate = kpiRoundRepository.findAll().size();

        // Update the kpiRound using partial update
        KpiRound partialUpdatedKpiRound = new KpiRound();
        partialUpdatedKpiRound.setId(kpiRound.getId());

        partialUpdatedKpiRound
            .displayName(UPDATED_DISPLAY_NAME)
            .totalPossibleRewards(UPDATED_TOTAL_POSSIBLE_REWARDS)
            .councilElectedInRound(UPDATED_COUNCIL_ELECTED_IN_ROUND)
            .councilMembers(UPDATED_COUNCIL_MEMBERS)
            .termLength(UPDATED_TERM_LENGTH)
            .startBlock(UPDATED_START_BLOCK)
            .startDate(UPDATED_START_DATE)
            .endBlock(UPDATED_END_BLOCK)
            .endDate(UPDATED_END_DATE)
            .termSummary(UPDATED_TERM_SUMMARY)
            .summarySubmissionDeadline(UPDATED_SUMMARY_SUBMISSION_DEADLINE)
            .maxFiatPoolDifference(UPDATED_MAX_FIAT_POOL_DIFFERENCE)
            .numberOfKpis(UPDATED_NUMBER_OF_KPIS)
            .notes(UPDATED_NOTES);

        restKpiRoundMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKpiRound.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKpiRound))
            )
            .andExpect(status().isOk());

        // Validate the KpiRound in the database
        List<KpiRound> kpiRoundList = kpiRoundRepository.findAll();
        assertThat(kpiRoundList).hasSize(databaseSizeBeforeUpdate);
        KpiRound testKpiRound = kpiRoundList.get(kpiRoundList.size() - 1);
        assertThat(testKpiRound.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testKpiRound.getTotalPossibleRewards()).isEqualTo(UPDATED_TOTAL_POSSIBLE_REWARDS);
        assertThat(testKpiRound.getCouncilElectedInRound()).isEqualTo(UPDATED_COUNCIL_ELECTED_IN_ROUND);
        assertThat(testKpiRound.getCouncilMembers()).isEqualTo(UPDATED_COUNCIL_MEMBERS);
        assertThat(testKpiRound.getTermLength()).isEqualTo(UPDATED_TERM_LENGTH);
        assertThat(testKpiRound.getStartBlock()).isEqualTo(UPDATED_START_BLOCK);
        assertThat(testKpiRound.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testKpiRound.getEndBlock()).isEqualTo(UPDATED_END_BLOCK);
        assertThat(testKpiRound.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testKpiRound.getTermSummary()).isEqualTo(UPDATED_TERM_SUMMARY);
        assertThat(testKpiRound.getSummarySubmissionDeadline()).isEqualTo(UPDATED_SUMMARY_SUBMISSION_DEADLINE);
        assertThat(testKpiRound.getMaxFiatPoolDifference()).isEqualTo(UPDATED_MAX_FIAT_POOL_DIFFERENCE);
        assertThat(testKpiRound.getNumberOfKpis()).isEqualTo(UPDATED_NUMBER_OF_KPIS);
        assertThat(testKpiRound.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void patchNonExistingKpiRound() throws Exception {
        int databaseSizeBeforeUpdate = kpiRoundRepository.findAll().size();
        kpiRound.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKpiRoundMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kpiRound.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kpiRound))
            )
            .andExpect(status().isBadRequest());

        // Validate the KpiRound in the database
        List<KpiRound> kpiRoundList = kpiRoundRepository.findAll();
        assertThat(kpiRoundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKpiRound() throws Exception {
        int databaseSizeBeforeUpdate = kpiRoundRepository.findAll().size();
        kpiRound.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKpiRoundMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kpiRound))
            )
            .andExpect(status().isBadRequest());

        // Validate the KpiRound in the database
        List<KpiRound> kpiRoundList = kpiRoundRepository.findAll();
        assertThat(kpiRoundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKpiRound() throws Exception {
        int databaseSizeBeforeUpdate = kpiRoundRepository.findAll().size();
        kpiRound.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKpiRoundMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(kpiRound)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KpiRound in the database
        List<KpiRound> kpiRoundList = kpiRoundRepository.findAll();
        assertThat(kpiRoundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKpiRound() throws Exception {
        // Initialize the database
        kpiRoundRepository.saveAndFlush(kpiRound);

        int databaseSizeBeforeDelete = kpiRoundRepository.findAll().size();

        // Delete the kpiRound
        restKpiRoundMockMvc
            .perform(delete(ENTITY_API_URL_ID, kpiRound.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KpiRound> kpiRoundList = kpiRoundRepository.findAll();
        assertThat(kpiRoundList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
