package org.joystream.kpi.service;

import java.util.Optional;
import org.joystream.kpi.domain.KpiRound;
import org.joystream.kpi.repository.KpiRoundRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link KpiRound}.
 */
@Service
@Transactional
public class KpiRoundService {

    private final Logger log = LoggerFactory.getLogger(KpiRoundService.class);

    private final KpiRoundRepository kpiRoundRepository;

    public KpiRoundService(KpiRoundRepository kpiRoundRepository) {
        this.kpiRoundRepository = kpiRoundRepository;
    }

    /**
     * Save a kpiRound.
     *
     * @param kpiRound the entity to save.
     * @return the persisted entity.
     */
    public KpiRound save(KpiRound kpiRound) {
        log.debug("Request to save KpiRound : {}", kpiRound);
        return kpiRoundRepository.save(kpiRound);
    }

    /**
     * Partially update a kpiRound.
     *
     * @param kpiRound the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<KpiRound> partialUpdate(KpiRound kpiRound) {
        log.debug("Request to partially update KpiRound : {}", kpiRound);

        return kpiRoundRepository
            .findById(kpiRound.getId())
            .map(existingKpiRound -> {
                if (kpiRound.getDisplayName() != null) {
                    existingKpiRound.setDisplayName(kpiRound.getDisplayName());
                }
                if (kpiRound.getTotalPossibleRewards() != null) {
                    existingKpiRound.setTotalPossibleRewards(kpiRound.getTotalPossibleRewards());
                }
                if (kpiRound.getCouncilElectedInRound() != null) {
                    existingKpiRound.setCouncilElectedInRound(kpiRound.getCouncilElectedInRound());
                }
                if (kpiRound.getCouncilMembers() != null) {
                    existingKpiRound.setCouncilMembers(kpiRound.getCouncilMembers());
                }
                if (kpiRound.getTermLength() != null) {
                    existingKpiRound.setTermLength(kpiRound.getTermLength());
                }
                if (kpiRound.getStartBlock() != null) {
                    existingKpiRound.setStartBlock(kpiRound.getStartBlock());
                }
                if (kpiRound.getStartDate() != null) {
                    existingKpiRound.setStartDate(kpiRound.getStartDate());
                }
                if (kpiRound.getEndBlock() != null) {
                    existingKpiRound.setEndBlock(kpiRound.getEndBlock());
                }
                if (kpiRound.getEndDate() != null) {
                    existingKpiRound.setEndDate(kpiRound.getEndDate());
                }
                if (kpiRound.getTermSummary() != null) {
                    existingKpiRound.setTermSummary(kpiRound.getTermSummary());
                }
                if (kpiRound.getSummarySubmissionDeadline() != null) {
                    existingKpiRound.setSummarySubmissionDeadline(kpiRound.getSummarySubmissionDeadline());
                }
                if (kpiRound.getMaxFiatPoolDifference() != null) {
                    existingKpiRound.setMaxFiatPoolDifference(kpiRound.getMaxFiatPoolDifference());
                }
                if (kpiRound.getNumberOfKpis() != null) {
                    existingKpiRound.setNumberOfKpis(kpiRound.getNumberOfKpis());
                }
                if (kpiRound.getNotes() != null) {
                    existingKpiRound.setNotes(kpiRound.getNotes());
                }

                return existingKpiRound;
            })
            .map(kpiRoundRepository::save);
    }

    /**
     * Get all the kpiRounds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<KpiRound> findAll(Pageable pageable) {
        log.debug("Request to get all KpiRounds");
        return kpiRoundRepository.findAll(pageable);
    }

    /**
     * Get one kpiRound by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<KpiRound> findOne(Long id) {
        log.debug("Request to get KpiRound : {}", id);
        return kpiRoundRepository.findById(id);
    }

    /**
     * Delete the kpiRound by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete KpiRound : {}", id);
        kpiRoundRepository.deleteById(id);
    }
}
