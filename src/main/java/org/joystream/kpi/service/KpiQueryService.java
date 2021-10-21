package org.joystream.kpi.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.joystream.kpi.domain.*; // for static metamodels
import org.joystream.kpi.domain.Kpi;
import org.joystream.kpi.repository.KpiRepository;
import org.joystream.kpi.service.criteria.KpiCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Kpi} entities in the database.
 * The main input is a {@link KpiCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Kpi} or a {@link Page} of {@link Kpi} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class KpiQueryService extends QueryService<Kpi> {

    private final Logger log = LoggerFactory.getLogger(KpiQueryService.class);

    private final KpiRepository kpiRepository;

    public KpiQueryService(KpiRepository kpiRepository) {
        this.kpiRepository = kpiRepository;
    }

    /**
     * Return a {@link List} of {@link Kpi} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Kpi> findByCriteria(KpiCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Kpi> specification = createSpecification(criteria);
        return kpiRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Kpi} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Kpi> findByCriteria(KpiCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Kpi> specification = createSpecification(criteria);
        return kpiRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(KpiCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Kpi> specification = createSpecification(criteria);
        return kpiRepository.count(specification);
    }

    /**
     * Function to convert {@link KpiCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Kpi> createSpecification(KpiCriteria criteria) {
        Specification<Kpi> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Kpi_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Kpi_.title));
            }
            if (criteria.getReward() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReward(), Kpi_.reward));
            }
            if (criteria.getRewardDistribution() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRewardDistribution(), Kpi_.rewardDistribution));
            }
            if (criteria.getGradingProcess() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGradingProcess(), Kpi_.gradingProcess));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActive(), Kpi_.active));
            }
            if (criteria.getPurpose() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPurpose(), Kpi_.purpose));
            }
            if (criteria.getScopeOfWork() != null) {
                specification = specification.and(buildStringSpecification(criteria.getScopeOfWork(), Kpi_.scopeOfWork));
            }
            if (criteria.getRewardDistributionInfo() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getRewardDistributionInfo(), Kpi_.rewardDistributionInfo));
            }
            if (criteria.getReporting() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReporting(), Kpi_.reporting));
            }
            if (criteria.getFiatPoolFactor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFiatPoolFactor(), Kpi_.fiatPoolFactor));
            }
            if (criteria.getGrading() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGrading(), Kpi_.grading));
            }
            if (criteria.getKpiRoundId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getKpiRoundId(), root -> root.join(Kpi_.kpiRound, JoinType.LEFT).get(KpiRound_.id))
                    );
            }
        }
        return specification;
    }
}
