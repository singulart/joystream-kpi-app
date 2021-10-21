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
import org.joystream.kpi.domain.KpiRound;
import org.joystream.kpi.repository.KpiRoundRepository;
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

    private static final Integer DEFAULT_COUNCIL_MEMBERS = 2;
    private static final Integer UPDATED_COUNCIL_MEMBERS = 3;

    private static final String DEFAULT_TERM_LENGTH = "AAAAAAAAAA";
    private static final String UPDATED_TERM_LENGTH = "BBBBBBBBBB";

    private static final Integer DEFAULT_START_BLOCK = 1;
    private static final Integer UPDATED_START_BLOCK = 2;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_END_BLOCK = "AAAAAAAAAA";
    private static final String UPDATED_END_BLOCK = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

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
