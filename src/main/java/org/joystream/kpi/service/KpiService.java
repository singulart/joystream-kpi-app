package org.joystream.kpi.service;

import java.util.Optional;
import org.joystream.kpi.domain.Kpi;
import org.joystream.kpi.repository.KpiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Kpi}.
 */
@Service
@Transactional
public class KpiService {

    private final Logger log = LoggerFactory.getLogger(KpiService.class);

    private final KpiRepository kpiRepository;

    public KpiService(KpiRepository kpiRepository) {
        this.kpiRepository = kpiRepository;
    }

    /**
     * Save a kpi.
     *
     * @param kpi the entity to save.
     * @return the persisted entity.
     */
    public Kpi save(Kpi kpi) {
        log.debug("Request to save Kpi : {}", kpi);
        return kpiRepository.save(kpi);
    }

    /**
     * Partially update a kpi.
     *
     * @param kpi the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Kpi> partialUpdate(Kpi kpi) {
        log.debug("Request to partially update Kpi : {}", kpi);

        return kpiRepository
            .findById(kpi.getId())
            .map(existingKpi -> {
                if (kpi.getTitle() != null) {
                    existingKpi.setTitle(kpi.getTitle());
                }
                if (kpi.getReward() != null) {
                    existingKpi.setReward(kpi.getReward());
                }
                if (kpi.getRewardDistribution() != null) {
                    existingKpi.setRewardDistribution(kpi.getRewardDistribution());
                }
                if (kpi.getGradingProcess() != null) {
                    existingKpi.setGradingProcess(kpi.getGradingProcess());
                }
                if (kpi.getActive() != null) {
                    existingKpi.setActive(kpi.getActive());
                }
                if (kpi.getPurpose() != null) {
                    existingKpi.setPurpose(kpi.getPurpose());
                }
                if (kpi.getScopeOfWork() != null) {
                    existingKpi.setScopeOfWork(kpi.getScopeOfWork());
                }
                if (kpi.getRewardDistributionInfo() != null) {
                    existingKpi.setRewardDistributionInfo(kpi.getRewardDistributionInfo());
                }
                if (kpi.getReporting() != null) {
                    existingKpi.setReporting(kpi.getReporting());
                }
                if (kpi.getFiatPoolFactor() != null) {
                    existingKpi.setFiatPoolFactor(kpi.getFiatPoolFactor());
                }
                if (kpi.getGrading() != null) {
                    existingKpi.setGrading(kpi.getGrading());
                }

                return existingKpi;
            })
            .map(kpiRepository::save);
    }

    /**
     * Get all the kpis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Kpi> findAll(Pageable pageable) {
        log.debug("Request to get all Kpis");
        return kpiRepository.findAll(pageable);
    }

    /**
     * Get one kpi by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Kpi> findOne(Long id) {
        log.debug("Request to get Kpi : {}", id);
        return kpiRepository.findById(id);
    }

    /**
     * Delete the kpi by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Kpi : {}", id);
        kpiRepository.deleteById(id);
    }
}
