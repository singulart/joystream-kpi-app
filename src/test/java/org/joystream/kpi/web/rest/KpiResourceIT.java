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
import org.joystream.kpi.repository.KpiRepository;
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
