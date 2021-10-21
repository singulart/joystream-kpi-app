package org.joystream.kpi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.joystream.kpi.IntegrationTest;
import org.joystream.kpi.domain.Kpi;
import org.joystream.kpi.domain.KpiRound;
import org.joystream.kpi.repository.KpiRepository;
import org.joystream.kpi.service.criteria.KpiCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KpiResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KpiResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_REWARD = "AAAAAAAAAA";
    private static final String UPDATED_REWARD = "BBBBBBBBBB";

    private static final String DEFAULT_REWARD_DISTRIBUTION = "AAAAAAAAAA";
    private static final String UPDATED_REWARD_DISTRIBUTION = "BBBBBBBBBB";

    private static final String DEFAULT_GRADING_PROCESS = "AAAAAAAAAA";
    private static final String UPDATED_GRADING_PROCESS = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVE = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVE = "BBBBBBBBBB";

    private static final String DEFAULT_PURPOSE = "AAAAAAAAAA";
    private static final String UPDATED_PURPOSE = "BBBBBBBBBB";

    private static final String DEFAULT_SCOPE_OF_WORK = "AAAAAAAAAA";
    private static final String UPDATED_SCOPE_OF_WORK = "BBBBBBBBBB";

    private static final String DEFAULT_REWARD_DISTRIBUTION_INFO = "AAAAAAAAAA";
    private static final String UPDATED_REWARD_DISTRIBUTION_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_REPORTING = "AAAAAAAAAA";
    private static final String UPDATED_REPORTING = "BBBBBBBBBB";

    private static final Float DEFAULT_FIAT_POOL_FACTOR = 1F;
    private static final Float UPDATED_FIAT_POOL_FACTOR = 2F;
    private static final Float SMALLER_FIAT_POOL_FACTOR = 1F - 1F;

    private static final String DEFAULT_GRADING = "AAAAAAAAAA";
    private static final String UPDATED_GRADING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/kpis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KpiRepository kpiRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKpiMockMvc;

    private Kpi kpi;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kpi createEntity(EntityManager em) {
        Kpi kpi = new Kpi()
            .title(DEFAULT_TITLE)
            .reward(DEFAULT_REWARD)
            .rewardDistribution(DEFAULT_REWARD_DISTRIBUTION)
            .gradingProcess(DEFAULT_GRADING_PROCESS)
            .active(DEFAULT_ACTIVE)
            .purpose(DEFAULT_PURPOSE)
            .scopeOfWork(DEFAULT_SCOPE_OF_WORK)
            .rewardDistributionInfo(DEFAULT_REWARD_DISTRIBUTION_INFO)
            .reporting(DEFAULT_REPORTING)
            .fiatPoolFactor(DEFAULT_FIAT_POOL_FACTOR)
            .grading(DEFAULT_GRADING);
        return kpi;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kpi createUpdatedEntity(EntityManager em) {
        Kpi kpi = new Kpi()
            .title(UPDATED_TITLE)
            .reward(UPDATED_REWARD)
            .rewardDistribution(UPDATED_REWARD_DISTRIBUTION)
            .gradingProcess(UPDATED_GRADING_PROCESS)
            .active(UPDATED_ACTIVE)
            .purpose(UPDATED_PURPOSE)
            .scopeOfWork(UPDATED_SCOPE_OF_WORK)
            .rewardDistributionInfo(UPDATED_REWARD_DISTRIBUTION_INFO)
            .reporting(UPDATED_REPORTING)
            .fiatPoolFactor(UPDATED_FIAT_POOL_FACTOR)
            .grading(UPDATED_GRADING);
        return kpi;
    }

    @BeforeEach
    public void initTest() {
        kpi = createEntity(em);
    }

    @Test
    @Transactional
    void createKpi() throws Exception {
        int databaseSizeBeforeCreate = kpiRepository.findAll().size();
        // Create the Kpi
        restKpiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpi)))
            .andExpect(status().isCreated());

        // Validate the Kpi in the database
        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeCreate + 1);
        Kpi testKpi = kpiList.get(kpiList.size() - 1);
        assertThat(testKpi.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testKpi.getReward()).isEqualTo(DEFAULT_REWARD);
        assertThat(testKpi.getRewardDistribution()).isEqualTo(DEFAULT_REWARD_DISTRIBUTION);
        assertThat(testKpi.getGradingProcess()).isEqualTo(DEFAULT_GRADING_PROCESS);
        assertThat(testKpi.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testKpi.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testKpi.getScopeOfWork()).isEqualTo(DEFAULT_SCOPE_OF_WORK);
        assertThat(testKpi.getRewardDistributionInfo()).isEqualTo(DEFAULT_REWARD_DISTRIBUTION_INFO);
        assertThat(testKpi.getReporting()).isEqualTo(DEFAULT_REPORTING);
        assertThat(testKpi.getFiatPoolFactor()).isEqualTo(DEFAULT_FIAT_POOL_FACTOR);
        assertThat(testKpi.getGrading()).isEqualTo(DEFAULT_GRADING);
    }

    @Test
    @Transactional
    void createKpiWithExistingId() throws Exception {
        // Create the Kpi with an existing ID
        kpi.setId(1L);

        int databaseSizeBeforeCreate = kpiRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKpiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpi)))
            .andExpect(status().isBadRequest());

        // Validate the Kpi in the database
        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = kpiRepository.findAll().size();
        // set the field null
        kpi.setTitle(null);

        // Create the Kpi, which fails.

        restKpiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpi)))
            .andExpect(status().isBadRequest());

        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRewardIsRequired() throws Exception {
        int databaseSizeBeforeTest = kpiRepository.findAll().size();
        // set the field null
        kpi.setReward(null);

        // Create the Kpi, which fails.

        restKpiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpi)))
            .andExpect(status().isBadRequest());

        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRewardDistributionIsRequired() throws Exception {
        int databaseSizeBeforeTest = kpiRepository.findAll().size();
        // set the field null
        kpi.setRewardDistribution(null);

        // Create the Kpi, which fails.

        restKpiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpi)))
            .andExpect(status().isBadRequest());

        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGradingProcessIsRequired() throws Exception {
        int databaseSizeBeforeTest = kpiRepository.findAll().size();
        // set the field null
        kpi.setGradingProcess(null);

        // Create the Kpi, which fails.

        restKpiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpi)))
            .andExpect(status().isBadRequest());

        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = kpiRepository.findAll().size();
        // set the field null
        kpi.setActive(null);

        // Create the Kpi, which fails.

        restKpiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpi)))
            .andExpect(status().isBadRequest());

        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllKpis() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList
        restKpiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kpi.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].reward").value(hasItem(DEFAULT_REWARD)))
            .andExpect(jsonPath("$.[*].rewardDistribution").value(hasItem(DEFAULT_REWARD_DISTRIBUTION)))
            .andExpect(jsonPath("$.[*].gradingProcess").value(hasItem(DEFAULT_GRADING_PROCESS)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE)))
            .andExpect(jsonPath("$.[*].scopeOfWork").value(hasItem(DEFAULT_SCOPE_OF_WORK)))
            .andExpect(jsonPath("$.[*].rewardDistributionInfo").value(hasItem(DEFAULT_REWARD_DISTRIBUTION_INFO)))
            .andExpect(jsonPath("$.[*].reporting").value(hasItem(DEFAULT_REPORTING)))
            .andExpect(jsonPath("$.[*].fiatPoolFactor").value(hasItem(DEFAULT_FIAT_POOL_FACTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].grading").value(hasItem(DEFAULT_GRADING)));
    }

    @Test
    @Transactional
    void getKpi() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get the kpi
        restKpiMockMvc
            .perform(get(ENTITY_API_URL_ID, kpi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kpi.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.reward").value(DEFAULT_REWARD))
            .andExpect(jsonPath("$.rewardDistribution").value(DEFAULT_REWARD_DISTRIBUTION))
            .andExpect(jsonPath("$.gradingProcess").value(DEFAULT_GRADING_PROCESS))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.purpose").value(DEFAULT_PURPOSE))
            .andExpect(jsonPath("$.scopeOfWork").value(DEFAULT_SCOPE_OF_WORK))
            .andExpect(jsonPath("$.rewardDistributionInfo").value(DEFAULT_REWARD_DISTRIBUTION_INFO))
            .andExpect(jsonPath("$.reporting").value(DEFAULT_REPORTING))
            .andExpect(jsonPath("$.fiatPoolFactor").value(DEFAULT_FIAT_POOL_FACTOR.doubleValue()))
            .andExpect(jsonPath("$.grading").value(DEFAULT_GRADING));
    }

    @Test
    @Transactional
    void getKpisByIdFiltering() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        Long id = kpi.getId();

        defaultKpiShouldBeFound("id.equals=" + id);
        defaultKpiShouldNotBeFound("id.notEquals=" + id);

        defaultKpiShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKpiShouldNotBeFound("id.greaterThan=" + id);

        defaultKpiShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKpiShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllKpisByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where title equals to DEFAULT_TITLE
        defaultKpiShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the kpiList where title equals to UPDATED_TITLE
        defaultKpiShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllKpisByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where title not equals to DEFAULT_TITLE
        defaultKpiShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the kpiList where title not equals to UPDATED_TITLE
        defaultKpiShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllKpisByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultKpiShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the kpiList where title equals to UPDATED_TITLE
        defaultKpiShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllKpisByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where title is not null
        defaultKpiShouldBeFound("title.specified=true");

        // Get all the kpiList where title is null
        defaultKpiShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllKpisByTitleContainsSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where title contains DEFAULT_TITLE
        defaultKpiShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the kpiList where title contains UPDATED_TITLE
        defaultKpiShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllKpisByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where title does not contain DEFAULT_TITLE
        defaultKpiShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the kpiList where title does not contain UPDATED_TITLE
        defaultKpiShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllKpisByRewardIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where reward equals to DEFAULT_REWARD
        defaultKpiShouldBeFound("reward.equals=" + DEFAULT_REWARD);

        // Get all the kpiList where reward equals to UPDATED_REWARD
        defaultKpiShouldNotBeFound("reward.equals=" + UPDATED_REWARD);
    }

    @Test
    @Transactional
    void getAllKpisByRewardIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where reward not equals to DEFAULT_REWARD
        defaultKpiShouldNotBeFound("reward.notEquals=" + DEFAULT_REWARD);

        // Get all the kpiList where reward not equals to UPDATED_REWARD
        defaultKpiShouldBeFound("reward.notEquals=" + UPDATED_REWARD);
    }

    @Test
    @Transactional
    void getAllKpisByRewardIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where reward in DEFAULT_REWARD or UPDATED_REWARD
        defaultKpiShouldBeFound("reward.in=" + DEFAULT_REWARD + "," + UPDATED_REWARD);

        // Get all the kpiList where reward equals to UPDATED_REWARD
        defaultKpiShouldNotBeFound("reward.in=" + UPDATED_REWARD);
    }

    @Test
    @Transactional
    void getAllKpisByRewardIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where reward is not null
        defaultKpiShouldBeFound("reward.specified=true");

        // Get all the kpiList where reward is null
        defaultKpiShouldNotBeFound("reward.specified=false");
    }

    @Test
    @Transactional
    void getAllKpisByRewardContainsSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where reward contains DEFAULT_REWARD
        defaultKpiShouldBeFound("reward.contains=" + DEFAULT_REWARD);

        // Get all the kpiList where reward contains UPDATED_REWARD
        defaultKpiShouldNotBeFound("reward.contains=" + UPDATED_REWARD);
    }

    @Test
    @Transactional
    void getAllKpisByRewardNotContainsSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where reward does not contain DEFAULT_REWARD
        defaultKpiShouldNotBeFound("reward.doesNotContain=" + DEFAULT_REWARD);

        // Get all the kpiList where reward does not contain UPDATED_REWARD
        defaultKpiShouldBeFound("reward.doesNotContain=" + UPDATED_REWARD);
    }

    @Test
    @Transactional
    void getAllKpisByRewardDistributionIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where rewardDistribution equals to DEFAULT_REWARD_DISTRIBUTION
        defaultKpiShouldBeFound("rewardDistribution.equals=" + DEFAULT_REWARD_DISTRIBUTION);

        // Get all the kpiList where rewardDistribution equals to UPDATED_REWARD_DISTRIBUTION
        defaultKpiShouldNotBeFound("rewardDistribution.equals=" + UPDATED_REWARD_DISTRIBUTION);
    }

    @Test
    @Transactional
    void getAllKpisByRewardDistributionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where rewardDistribution not equals to DEFAULT_REWARD_DISTRIBUTION
        defaultKpiShouldNotBeFound("rewardDistribution.notEquals=" + DEFAULT_REWARD_DISTRIBUTION);

        // Get all the kpiList where rewardDistribution not equals to UPDATED_REWARD_DISTRIBUTION
        defaultKpiShouldBeFound("rewardDistribution.notEquals=" + UPDATED_REWARD_DISTRIBUTION);
    }

    @Test
    @Transactional
    void getAllKpisByRewardDistributionIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where rewardDistribution in DEFAULT_REWARD_DISTRIBUTION or UPDATED_REWARD_DISTRIBUTION
        defaultKpiShouldBeFound("rewardDistribution.in=" + DEFAULT_REWARD_DISTRIBUTION + "," + UPDATED_REWARD_DISTRIBUTION);

        // Get all the kpiList where rewardDistribution equals to UPDATED_REWARD_DISTRIBUTION
        defaultKpiShouldNotBeFound("rewardDistribution.in=" + UPDATED_REWARD_DISTRIBUTION);
    }

    @Test
    @Transactional
    void getAllKpisByRewardDistributionIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where rewardDistribution is not null
        defaultKpiShouldBeFound("rewardDistribution.specified=true");

        // Get all the kpiList where rewardDistribution is null
        defaultKpiShouldNotBeFound("rewardDistribution.specified=false");
    }

    @Test
    @Transactional
    void getAllKpisByRewardDistributionContainsSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where rewardDistribution contains DEFAULT_REWARD_DISTRIBUTION
        defaultKpiShouldBeFound("rewardDistribution.contains=" + DEFAULT_REWARD_DISTRIBUTION);

        // Get all the kpiList where rewardDistribution contains UPDATED_REWARD_DISTRIBUTION
        defaultKpiShouldNotBeFound("rewardDistribution.contains=" + UPDATED_REWARD_DISTRIBUTION);
    }

    @Test
    @Transactional
    void getAllKpisByRewardDistributionNotContainsSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where rewardDistribution does not contain DEFAULT_REWARD_DISTRIBUTION
        defaultKpiShouldNotBeFound("rewardDistribution.doesNotContain=" + DEFAULT_REWARD_DISTRIBUTION);

        // Get all the kpiList where rewardDistribution does not contain UPDATED_REWARD_DISTRIBUTION
        defaultKpiShouldBeFound("rewardDistribution.doesNotContain=" + UPDATED_REWARD_DISTRIBUTION);
    }

    @Test
    @Transactional
    void getAllKpisByGradingProcessIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where gradingProcess equals to DEFAULT_GRADING_PROCESS
        defaultKpiShouldBeFound("gradingProcess.equals=" + DEFAULT_GRADING_PROCESS);

        // Get all the kpiList where gradingProcess equals to UPDATED_GRADING_PROCESS
        defaultKpiShouldNotBeFound("gradingProcess.equals=" + UPDATED_GRADING_PROCESS);
    }

    @Test
    @Transactional
    void getAllKpisByGradingProcessIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where gradingProcess not equals to DEFAULT_GRADING_PROCESS
        defaultKpiShouldNotBeFound("gradingProcess.notEquals=" + DEFAULT_GRADING_PROCESS);

        // Get all the kpiList where gradingProcess not equals to UPDATED_GRADING_PROCESS
        defaultKpiShouldBeFound("gradingProcess.notEquals=" + UPDATED_GRADING_PROCESS);
    }

    @Test
    @Transactional
    void getAllKpisByGradingProcessIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where gradingProcess in DEFAULT_GRADING_PROCESS or UPDATED_GRADING_PROCESS
        defaultKpiShouldBeFound("gradingProcess.in=" + DEFAULT_GRADING_PROCESS + "," + UPDATED_GRADING_PROCESS);

        // Get all the kpiList where gradingProcess equals to UPDATED_GRADING_PROCESS
        defaultKpiShouldNotBeFound("gradingProcess.in=" + UPDATED_GRADING_PROCESS);
    }

    @Test
    @Transactional
    void getAllKpisByGradingProcessIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where gradingProcess is not null
        defaultKpiShouldBeFound("gradingProcess.specified=true");

        // Get all the kpiList where gradingProcess is null
        defaultKpiShouldNotBeFound("gradingProcess.specified=false");
    }

    @Test
    @Transactional
    void getAllKpisByGradingProcessContainsSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where gradingProcess contains DEFAULT_GRADING_PROCESS
        defaultKpiShouldBeFound("gradingProcess.contains=" + DEFAULT_GRADING_PROCESS);

        // Get all the kpiList where gradingProcess contains UPDATED_GRADING_PROCESS
        defaultKpiShouldNotBeFound("gradingProcess.contains=" + UPDATED_GRADING_PROCESS);
    }

    @Test
    @Transactional
    void getAllKpisByGradingProcessNotContainsSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where gradingProcess does not contain DEFAULT_GRADING_PROCESS
        defaultKpiShouldNotBeFound("gradingProcess.doesNotContain=" + DEFAULT_GRADING_PROCESS);

        // Get all the kpiList where gradingProcess does not contain UPDATED_GRADING_PROCESS
        defaultKpiShouldBeFound("gradingProcess.doesNotContain=" + UPDATED_GRADING_PROCESS);
    }

    @Test
    @Transactional
    void getAllKpisByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where active equals to DEFAULT_ACTIVE
        defaultKpiShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the kpiList where active equals to UPDATED_ACTIVE
        defaultKpiShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllKpisByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where active not equals to DEFAULT_ACTIVE
        defaultKpiShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the kpiList where active not equals to UPDATED_ACTIVE
        defaultKpiShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllKpisByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultKpiShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the kpiList where active equals to UPDATED_ACTIVE
        defaultKpiShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllKpisByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where active is not null
        defaultKpiShouldBeFound("active.specified=true");

        // Get all the kpiList where active is null
        defaultKpiShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    void getAllKpisByActiveContainsSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where active contains DEFAULT_ACTIVE
        defaultKpiShouldBeFound("active.contains=" + DEFAULT_ACTIVE);

        // Get all the kpiList where active contains UPDATED_ACTIVE
        defaultKpiShouldNotBeFound("active.contains=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllKpisByActiveNotContainsSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where active does not contain DEFAULT_ACTIVE
        defaultKpiShouldNotBeFound("active.doesNotContain=" + DEFAULT_ACTIVE);

        // Get all the kpiList where active does not contain UPDATED_ACTIVE
        defaultKpiShouldBeFound("active.doesNotContain=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllKpisByPurposeIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where purpose equals to DEFAULT_PURPOSE
        defaultKpiShouldBeFound("purpose.equals=" + DEFAULT_PURPOSE);

        // Get all the kpiList where purpose equals to UPDATED_PURPOSE
        defaultKpiShouldNotBeFound("purpose.equals=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllKpisByPurposeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where purpose not equals to DEFAULT_PURPOSE
        defaultKpiShouldNotBeFound("purpose.notEquals=" + DEFAULT_PURPOSE);

        // Get all the kpiList where purpose not equals to UPDATED_PURPOSE
        defaultKpiShouldBeFound("purpose.notEquals=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllKpisByPurposeIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where purpose in DEFAULT_PURPOSE or UPDATED_PURPOSE
        defaultKpiShouldBeFound("purpose.in=" + DEFAULT_PURPOSE + "," + UPDATED_PURPOSE);

        // Get all the kpiList where purpose equals to UPDATED_PURPOSE
        defaultKpiShouldNotBeFound("purpose.in=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllKpisByPurposeIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where purpose is not null
        defaultKpiShouldBeFound("purpose.specified=true");

        // Get all the kpiList where purpose is null
        defaultKpiShouldNotBeFound("purpose.specified=false");
    }

    @Test
    @Transactional
    void getAllKpisByPurposeContainsSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where purpose contains DEFAULT_PURPOSE
        defaultKpiShouldBeFound("purpose.contains=" + DEFAULT_PURPOSE);

        // Get all the kpiList where purpose contains UPDATED_PURPOSE
        defaultKpiShouldNotBeFound("purpose.contains=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllKpisByPurposeNotContainsSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where purpose does not contain DEFAULT_PURPOSE
        defaultKpiShouldNotBeFound("purpose.doesNotContain=" + DEFAULT_PURPOSE);

        // Get all the kpiList where purpose does not contain UPDATED_PURPOSE
        defaultKpiShouldBeFound("purpose.doesNotContain=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllKpisByScopeOfWorkIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where scopeOfWork equals to DEFAULT_SCOPE_OF_WORK
        defaultKpiShouldBeFound("scopeOfWork.equals=" + DEFAULT_SCOPE_OF_WORK);

        // Get all the kpiList where scopeOfWork equals to UPDATED_SCOPE_OF_WORK
        defaultKpiShouldNotBeFound("scopeOfWork.equals=" + UPDATED_SCOPE_OF_WORK);
    }

    @Test
    @Transactional
    void getAllKpisByScopeOfWorkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where scopeOfWork not equals to DEFAULT_SCOPE_OF_WORK
        defaultKpiShouldNotBeFound("scopeOfWork.notEquals=" + DEFAULT_SCOPE_OF_WORK);

        // Get all the kpiList where scopeOfWork not equals to UPDATED_SCOPE_OF_WORK
        defaultKpiShouldBeFound("scopeOfWork.notEquals=" + UPDATED_SCOPE_OF_WORK);
    }

    @Test
    @Transactional
    void getAllKpisByScopeOfWorkIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where scopeOfWork in DEFAULT_SCOPE_OF_WORK or UPDATED_SCOPE_OF_WORK
        defaultKpiShouldBeFound("scopeOfWork.in=" + DEFAULT_SCOPE_OF_WORK + "," + UPDATED_SCOPE_OF_WORK);

        // Get all the kpiList where scopeOfWork equals to UPDATED_SCOPE_OF_WORK
        defaultKpiShouldNotBeFound("scopeOfWork.in=" + UPDATED_SCOPE_OF_WORK);
    }

    @Test
    @Transactional
    void getAllKpisByScopeOfWorkIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where scopeOfWork is not null
        defaultKpiShouldBeFound("scopeOfWork.specified=true");

        // Get all the kpiList where scopeOfWork is null
        defaultKpiShouldNotBeFound("scopeOfWork.specified=false");
    }

    @Test
    @Transactional
    void getAllKpisByScopeOfWorkContainsSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where scopeOfWork contains DEFAULT_SCOPE_OF_WORK
        defaultKpiShouldBeFound("scopeOfWork.contains=" + DEFAULT_SCOPE_OF_WORK);

        // Get all the kpiList where scopeOfWork contains UPDATED_SCOPE_OF_WORK
        defaultKpiShouldNotBeFound("scopeOfWork.contains=" + UPDATED_SCOPE_OF_WORK);
    }

    @Test
    @Transactional
    void getAllKpisByScopeOfWorkNotContainsSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where scopeOfWork does not contain DEFAULT_SCOPE_OF_WORK
        defaultKpiShouldNotBeFound("scopeOfWork.doesNotContain=" + DEFAULT_SCOPE_OF_WORK);

        // Get all the kpiList where scopeOfWork does not contain UPDATED_SCOPE_OF_WORK
        defaultKpiShouldBeFound("scopeOfWork.doesNotContain=" + UPDATED_SCOPE_OF_WORK);
    }

    @Test
    @Transactional
    void getAllKpisByRewardDistributionInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where rewardDistributionInfo equals to DEFAULT_REWARD_DISTRIBUTION_INFO
        defaultKpiShouldBeFound("rewardDistributionInfo.equals=" + DEFAULT_REWARD_DISTRIBUTION_INFO);

        // Get all the kpiList where rewardDistributionInfo equals to UPDATED_REWARD_DISTRIBUTION_INFO
        defaultKpiShouldNotBeFound("rewardDistributionInfo.equals=" + UPDATED_REWARD_DISTRIBUTION_INFO);
    }

    @Test
    @Transactional
    void getAllKpisByRewardDistributionInfoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where rewardDistributionInfo not equals to DEFAULT_REWARD_DISTRIBUTION_INFO
        defaultKpiShouldNotBeFound("rewardDistributionInfo.notEquals=" + DEFAULT_REWARD_DISTRIBUTION_INFO);

        // Get all the kpiList where rewardDistributionInfo not equals to UPDATED_REWARD_DISTRIBUTION_INFO
        defaultKpiShouldBeFound("rewardDistributionInfo.notEquals=" + UPDATED_REWARD_DISTRIBUTION_INFO);
    }

    @Test
    @Transactional
    void getAllKpisByRewardDistributionInfoIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where rewardDistributionInfo in DEFAULT_REWARD_DISTRIBUTION_INFO or UPDATED_REWARD_DISTRIBUTION_INFO
        defaultKpiShouldBeFound("rewardDistributionInfo.in=" + DEFAULT_REWARD_DISTRIBUTION_INFO + "," + UPDATED_REWARD_DISTRIBUTION_INFO);

        // Get all the kpiList where rewardDistributionInfo equals to UPDATED_REWARD_DISTRIBUTION_INFO
        defaultKpiShouldNotBeFound("rewardDistributionInfo.in=" + UPDATED_REWARD_DISTRIBUTION_INFO);
    }

    @Test
    @Transactional
    void getAllKpisByRewardDistributionInfoIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where rewardDistributionInfo is not null
        defaultKpiShouldBeFound("rewardDistributionInfo.specified=true");

        // Get all the kpiList where rewardDistributionInfo is null
        defaultKpiShouldNotBeFound("rewardDistributionInfo.specified=false");
    }

    @Test
    @Transactional
    void getAllKpisByRewardDistributionInfoContainsSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where rewardDistributionInfo contains DEFAULT_REWARD_DISTRIBUTION_INFO
        defaultKpiShouldBeFound("rewardDistributionInfo.contains=" + DEFAULT_REWARD_DISTRIBUTION_INFO);

        // Get all the kpiList where rewardDistributionInfo contains UPDATED_REWARD_DISTRIBUTION_INFO
        defaultKpiShouldNotBeFound("rewardDistributionInfo.contains=" + UPDATED_REWARD_DISTRIBUTION_INFO);
    }

    @Test
    @Transactional
    void getAllKpisByRewardDistributionInfoNotContainsSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where rewardDistributionInfo does not contain DEFAULT_REWARD_DISTRIBUTION_INFO
        defaultKpiShouldNotBeFound("rewardDistributionInfo.doesNotContain=" + DEFAULT_REWARD_DISTRIBUTION_INFO);

        // Get all the kpiList where rewardDistributionInfo does not contain UPDATED_REWARD_DISTRIBUTION_INFO
        defaultKpiShouldBeFound("rewardDistributionInfo.doesNotContain=" + UPDATED_REWARD_DISTRIBUTION_INFO);
    }

    @Test
    @Transactional
    void getAllKpisByReportingIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where reporting equals to DEFAULT_REPORTING
        defaultKpiShouldBeFound("reporting.equals=" + DEFAULT_REPORTING);

        // Get all the kpiList where reporting equals to UPDATED_REPORTING
        defaultKpiShouldNotBeFound("reporting.equals=" + UPDATED_REPORTING);
    }

    @Test
    @Transactional
    void getAllKpisByReportingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where reporting not equals to DEFAULT_REPORTING
        defaultKpiShouldNotBeFound("reporting.notEquals=" + DEFAULT_REPORTING);

        // Get all the kpiList where reporting not equals to UPDATED_REPORTING
        defaultKpiShouldBeFound("reporting.notEquals=" + UPDATED_REPORTING);
    }

    @Test
    @Transactional
    void getAllKpisByReportingIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where reporting in DEFAULT_REPORTING or UPDATED_REPORTING
        defaultKpiShouldBeFound("reporting.in=" + DEFAULT_REPORTING + "," + UPDATED_REPORTING);

        // Get all the kpiList where reporting equals to UPDATED_REPORTING
        defaultKpiShouldNotBeFound("reporting.in=" + UPDATED_REPORTING);
    }

    @Test
    @Transactional
    void getAllKpisByReportingIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where reporting is not null
        defaultKpiShouldBeFound("reporting.specified=true");

        // Get all the kpiList where reporting is null
        defaultKpiShouldNotBeFound("reporting.specified=false");
    }

    @Test
    @Transactional
    void getAllKpisByReportingContainsSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where reporting contains DEFAULT_REPORTING
        defaultKpiShouldBeFound("reporting.contains=" + DEFAULT_REPORTING);

        // Get all the kpiList where reporting contains UPDATED_REPORTING
        defaultKpiShouldNotBeFound("reporting.contains=" + UPDATED_REPORTING);
    }

    @Test
    @Transactional
    void getAllKpisByReportingNotContainsSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where reporting does not contain DEFAULT_REPORTING
        defaultKpiShouldNotBeFound("reporting.doesNotContain=" + DEFAULT_REPORTING);

        // Get all the kpiList where reporting does not contain UPDATED_REPORTING
        defaultKpiShouldBeFound("reporting.doesNotContain=" + UPDATED_REPORTING);
    }

    @Test
    @Transactional
    void getAllKpisByFiatPoolFactorIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where fiatPoolFactor equals to DEFAULT_FIAT_POOL_FACTOR
        defaultKpiShouldBeFound("fiatPoolFactor.equals=" + DEFAULT_FIAT_POOL_FACTOR);

        // Get all the kpiList where fiatPoolFactor equals to UPDATED_FIAT_POOL_FACTOR
        defaultKpiShouldNotBeFound("fiatPoolFactor.equals=" + UPDATED_FIAT_POOL_FACTOR);
    }

    @Test
    @Transactional
    void getAllKpisByFiatPoolFactorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where fiatPoolFactor not equals to DEFAULT_FIAT_POOL_FACTOR
        defaultKpiShouldNotBeFound("fiatPoolFactor.notEquals=" + DEFAULT_FIAT_POOL_FACTOR);

        // Get all the kpiList where fiatPoolFactor not equals to UPDATED_FIAT_POOL_FACTOR
        defaultKpiShouldBeFound("fiatPoolFactor.notEquals=" + UPDATED_FIAT_POOL_FACTOR);
    }

    @Test
    @Transactional
    void getAllKpisByFiatPoolFactorIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where fiatPoolFactor in DEFAULT_FIAT_POOL_FACTOR or UPDATED_FIAT_POOL_FACTOR
        defaultKpiShouldBeFound("fiatPoolFactor.in=" + DEFAULT_FIAT_POOL_FACTOR + "," + UPDATED_FIAT_POOL_FACTOR);

        // Get all the kpiList where fiatPoolFactor equals to UPDATED_FIAT_POOL_FACTOR
        defaultKpiShouldNotBeFound("fiatPoolFactor.in=" + UPDATED_FIAT_POOL_FACTOR);
    }

    @Test
    @Transactional
    void getAllKpisByFiatPoolFactorIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where fiatPoolFactor is not null
        defaultKpiShouldBeFound("fiatPoolFactor.specified=true");

        // Get all the kpiList where fiatPoolFactor is null
        defaultKpiShouldNotBeFound("fiatPoolFactor.specified=false");
    }

    @Test
    @Transactional
    void getAllKpisByFiatPoolFactorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where fiatPoolFactor is greater than or equal to DEFAULT_FIAT_POOL_FACTOR
        defaultKpiShouldBeFound("fiatPoolFactor.greaterThanOrEqual=" + DEFAULT_FIAT_POOL_FACTOR);

        // Get all the kpiList where fiatPoolFactor is greater than or equal to UPDATED_FIAT_POOL_FACTOR
        defaultKpiShouldNotBeFound("fiatPoolFactor.greaterThanOrEqual=" + UPDATED_FIAT_POOL_FACTOR);
    }

    @Test
    @Transactional
    void getAllKpisByFiatPoolFactorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where fiatPoolFactor is less than or equal to DEFAULT_FIAT_POOL_FACTOR
        defaultKpiShouldBeFound("fiatPoolFactor.lessThanOrEqual=" + DEFAULT_FIAT_POOL_FACTOR);

        // Get all the kpiList where fiatPoolFactor is less than or equal to SMALLER_FIAT_POOL_FACTOR
        defaultKpiShouldNotBeFound("fiatPoolFactor.lessThanOrEqual=" + SMALLER_FIAT_POOL_FACTOR);
    }

    @Test
    @Transactional
    void getAllKpisByFiatPoolFactorIsLessThanSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where fiatPoolFactor is less than DEFAULT_FIAT_POOL_FACTOR
        defaultKpiShouldNotBeFound("fiatPoolFactor.lessThan=" + DEFAULT_FIAT_POOL_FACTOR);

        // Get all the kpiList where fiatPoolFactor is less than UPDATED_FIAT_POOL_FACTOR
        defaultKpiShouldBeFound("fiatPoolFactor.lessThan=" + UPDATED_FIAT_POOL_FACTOR);
    }

    @Test
    @Transactional
    void getAllKpisByFiatPoolFactorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where fiatPoolFactor is greater than DEFAULT_FIAT_POOL_FACTOR
        defaultKpiShouldNotBeFound("fiatPoolFactor.greaterThan=" + DEFAULT_FIAT_POOL_FACTOR);

        // Get all the kpiList where fiatPoolFactor is greater than SMALLER_FIAT_POOL_FACTOR
        defaultKpiShouldBeFound("fiatPoolFactor.greaterThan=" + SMALLER_FIAT_POOL_FACTOR);
    }

    @Test
    @Transactional
    void getAllKpisByGradingIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where grading equals to DEFAULT_GRADING
        defaultKpiShouldBeFound("grading.equals=" + DEFAULT_GRADING);

        // Get all the kpiList where grading equals to UPDATED_GRADING
        defaultKpiShouldNotBeFound("grading.equals=" + UPDATED_GRADING);
    }

    @Test
    @Transactional
    void getAllKpisByGradingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where grading not equals to DEFAULT_GRADING
        defaultKpiShouldNotBeFound("grading.notEquals=" + DEFAULT_GRADING);

        // Get all the kpiList where grading not equals to UPDATED_GRADING
        defaultKpiShouldBeFound("grading.notEquals=" + UPDATED_GRADING);
    }

    @Test
    @Transactional
    void getAllKpisByGradingIsInShouldWork() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where grading in DEFAULT_GRADING or UPDATED_GRADING
        defaultKpiShouldBeFound("grading.in=" + DEFAULT_GRADING + "," + UPDATED_GRADING);

        // Get all the kpiList where grading equals to UPDATED_GRADING
        defaultKpiShouldNotBeFound("grading.in=" + UPDATED_GRADING);
    }

    @Test
    @Transactional
    void getAllKpisByGradingIsNullOrNotNull() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where grading is not null
        defaultKpiShouldBeFound("grading.specified=true");

        // Get all the kpiList where grading is null
        defaultKpiShouldNotBeFound("grading.specified=false");
    }

    @Test
    @Transactional
    void getAllKpisByGradingContainsSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where grading contains DEFAULT_GRADING
        defaultKpiShouldBeFound("grading.contains=" + DEFAULT_GRADING);

        // Get all the kpiList where grading contains UPDATED_GRADING
        defaultKpiShouldNotBeFound("grading.contains=" + UPDATED_GRADING);
    }

    @Test
    @Transactional
    void getAllKpisByGradingNotContainsSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList where grading does not contain DEFAULT_GRADING
        defaultKpiShouldNotBeFound("grading.doesNotContain=" + DEFAULT_GRADING);

        // Get all the kpiList where grading does not contain UPDATED_GRADING
        defaultKpiShouldBeFound("grading.doesNotContain=" + UPDATED_GRADING);
    }

    @Test
    @Transactional
    void getAllKpisByKpiRoundIsEqualToSomething() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);
        KpiRound kpiRound;
        if (TestUtil.findAll(em, KpiRound.class).isEmpty()) {
            kpiRound = KpiRoundResourceIT.createEntity(em);
            em.persist(kpiRound);
            em.flush();
        } else {
            kpiRound = TestUtil.findAll(em, KpiRound.class).get(0);
        }
        em.persist(kpiRound);
        em.flush();
        kpi.setKpiRound(kpiRound);
        kpiRepository.saveAndFlush(kpi);
        Long kpiRoundId = kpiRound.getId();

        // Get all the kpiList where kpiRound equals to kpiRoundId
        defaultKpiShouldBeFound("kpiRoundId.equals=" + kpiRoundId);

        // Get all the kpiList where kpiRound equals to (kpiRoundId + 1)
        defaultKpiShouldNotBeFound("kpiRoundId.equals=" + (kpiRoundId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKpiShouldBeFound(String filter) throws Exception {
        restKpiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kpi.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].reward").value(hasItem(DEFAULT_REWARD)))
            .andExpect(jsonPath("$.[*].rewardDistribution").value(hasItem(DEFAULT_REWARD_DISTRIBUTION)))
            .andExpect(jsonPath("$.[*].gradingProcess").value(hasItem(DEFAULT_GRADING_PROCESS)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE)))
            .andExpect(jsonPath("$.[*].scopeOfWork").value(hasItem(DEFAULT_SCOPE_OF_WORK)))
            .andExpect(jsonPath("$.[*].rewardDistributionInfo").value(hasItem(DEFAULT_REWARD_DISTRIBUTION_INFO)))
            .andExpect(jsonPath("$.[*].reporting").value(hasItem(DEFAULT_REPORTING)))
            .andExpect(jsonPath("$.[*].fiatPoolFactor").value(hasItem(DEFAULT_FIAT_POOL_FACTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].grading").value(hasItem(DEFAULT_GRADING)));

        // Check, that the count call also returns 1
        restKpiMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKpiShouldNotBeFound(String filter) throws Exception {
        restKpiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKpiMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingKpi() throws Exception {
        // Get the kpi
        restKpiMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewKpi() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        int databaseSizeBeforeUpdate = kpiRepository.findAll().size();

        // Update the kpi
        Kpi updatedKpi = kpiRepository.findById(kpi.getId()).get();
        // Disconnect from session so that the updates on updatedKpi are not directly saved in db
        em.detach(updatedKpi);
        updatedKpi
            .title(UPDATED_TITLE)
            .reward(UPDATED_REWARD)
            .rewardDistribution(UPDATED_REWARD_DISTRIBUTION)
            .gradingProcess(UPDATED_GRADING_PROCESS)
            .active(UPDATED_ACTIVE)
            .purpose(UPDATED_PURPOSE)
            .scopeOfWork(UPDATED_SCOPE_OF_WORK)
            .rewardDistributionInfo(UPDATED_REWARD_DISTRIBUTION_INFO)
            .reporting(UPDATED_REPORTING)
            .fiatPoolFactor(UPDATED_FIAT_POOL_FACTOR)
            .grading(UPDATED_GRADING);

        restKpiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKpi.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKpi))
            )
            .andExpect(status().isOk());

        // Validate the Kpi in the database
        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeUpdate);
        Kpi testKpi = kpiList.get(kpiList.size() - 1);
        assertThat(testKpi.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testKpi.getReward()).isEqualTo(UPDATED_REWARD);
        assertThat(testKpi.getRewardDistribution()).isEqualTo(UPDATED_REWARD_DISTRIBUTION);
        assertThat(testKpi.getGradingProcess()).isEqualTo(UPDATED_GRADING_PROCESS);
        assertThat(testKpi.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testKpi.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testKpi.getScopeOfWork()).isEqualTo(UPDATED_SCOPE_OF_WORK);
        assertThat(testKpi.getRewardDistributionInfo()).isEqualTo(UPDATED_REWARD_DISTRIBUTION_INFO);
        assertThat(testKpi.getReporting()).isEqualTo(UPDATED_REPORTING);
        assertThat(testKpi.getFiatPoolFactor()).isEqualTo(UPDATED_FIAT_POOL_FACTOR);
        assertThat(testKpi.getGrading()).isEqualTo(UPDATED_GRADING);
    }

    @Test
    @Transactional
    void putNonExistingKpi() throws Exception {
        int databaseSizeBeforeUpdate = kpiRepository.findAll().size();
        kpi.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKpiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kpi.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpi))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kpi in the database
        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKpi() throws Exception {
        int databaseSizeBeforeUpdate = kpiRepository.findAll().size();
        kpi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKpiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kpi))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kpi in the database
        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKpi() throws Exception {
        int databaseSizeBeforeUpdate = kpiRepository.findAll().size();
        kpi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKpiMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kpi)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kpi in the database
        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKpiWithPatch() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        int databaseSizeBeforeUpdate = kpiRepository.findAll().size();

        // Update the kpi using partial update
        Kpi partialUpdatedKpi = new Kpi();
        partialUpdatedKpi.setId(kpi.getId());

        partialUpdatedKpi
            .title(UPDATED_TITLE)
            .reward(UPDATED_REWARD)
            .active(UPDATED_ACTIVE)
            .purpose(UPDATED_PURPOSE)
            .scopeOfWork(UPDATED_SCOPE_OF_WORK)
            .rewardDistributionInfo(UPDATED_REWARD_DISTRIBUTION_INFO)
            .fiatPoolFactor(UPDATED_FIAT_POOL_FACTOR)
            .grading(UPDATED_GRADING);

        restKpiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKpi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKpi))
            )
            .andExpect(status().isOk());

        // Validate the Kpi in the database
        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeUpdate);
        Kpi testKpi = kpiList.get(kpiList.size() - 1);
        assertThat(testKpi.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testKpi.getReward()).isEqualTo(UPDATED_REWARD);
        assertThat(testKpi.getRewardDistribution()).isEqualTo(DEFAULT_REWARD_DISTRIBUTION);
        assertThat(testKpi.getGradingProcess()).isEqualTo(DEFAULT_GRADING_PROCESS);
        assertThat(testKpi.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testKpi.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testKpi.getScopeOfWork()).isEqualTo(UPDATED_SCOPE_OF_WORK);
        assertThat(testKpi.getRewardDistributionInfo()).isEqualTo(UPDATED_REWARD_DISTRIBUTION_INFO);
        assertThat(testKpi.getReporting()).isEqualTo(DEFAULT_REPORTING);
        assertThat(testKpi.getFiatPoolFactor()).isEqualTo(UPDATED_FIAT_POOL_FACTOR);
        assertThat(testKpi.getGrading()).isEqualTo(UPDATED_GRADING);
    }

    @Test
    @Transactional
    void fullUpdateKpiWithPatch() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        int databaseSizeBeforeUpdate = kpiRepository.findAll().size();

        // Update the kpi using partial update
        Kpi partialUpdatedKpi = new Kpi();
        partialUpdatedKpi.setId(kpi.getId());

        partialUpdatedKpi
            .title(UPDATED_TITLE)
            .reward(UPDATED_REWARD)
            .rewardDistribution(UPDATED_REWARD_DISTRIBUTION)
            .gradingProcess(UPDATED_GRADING_PROCESS)
            .active(UPDATED_ACTIVE)
            .purpose(UPDATED_PURPOSE)
            .scopeOfWork(UPDATED_SCOPE_OF_WORK)
            .rewardDistributionInfo(UPDATED_REWARD_DISTRIBUTION_INFO)
            .reporting(UPDATED_REPORTING)
            .fiatPoolFactor(UPDATED_FIAT_POOL_FACTOR)
            .grading(UPDATED_GRADING);

        restKpiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKpi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKpi))
            )
            .andExpect(status().isOk());

        // Validate the Kpi in the database
        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeUpdate);
        Kpi testKpi = kpiList.get(kpiList.size() - 1);
        assertThat(testKpi.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testKpi.getReward()).isEqualTo(UPDATED_REWARD);
        assertThat(testKpi.getRewardDistribution()).isEqualTo(UPDATED_REWARD_DISTRIBUTION);
        assertThat(testKpi.getGradingProcess()).isEqualTo(UPDATED_GRADING_PROCESS);
        assertThat(testKpi.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testKpi.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testKpi.getScopeOfWork()).isEqualTo(UPDATED_SCOPE_OF_WORK);
        assertThat(testKpi.getRewardDistributionInfo()).isEqualTo(UPDATED_REWARD_DISTRIBUTION_INFO);
        assertThat(testKpi.getReporting()).isEqualTo(UPDATED_REPORTING);
        assertThat(testKpi.getFiatPoolFactor()).isEqualTo(UPDATED_FIAT_POOL_FACTOR);
        assertThat(testKpi.getGrading()).isEqualTo(UPDATED_GRADING);
    }

    @Test
    @Transactional
    void patchNonExistingKpi() throws Exception {
        int databaseSizeBeforeUpdate = kpiRepository.findAll().size();
        kpi.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKpiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kpi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kpi))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kpi in the database
        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKpi() throws Exception {
        int databaseSizeBeforeUpdate = kpiRepository.findAll().size();
        kpi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKpiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kpi))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kpi in the database
        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKpi() throws Exception {
        int databaseSizeBeforeUpdate = kpiRepository.findAll().size();
        kpi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKpiMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(kpi)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kpi in the database
        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKpi() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        int databaseSizeBeforeDelete = kpiRepository.findAll().size();

        // Delete the kpi
        restKpiMockMvc.perform(delete(ENTITY_API_URL_ID, kpi.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
