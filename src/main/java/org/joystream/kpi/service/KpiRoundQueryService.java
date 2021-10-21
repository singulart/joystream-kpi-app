package org.joystream.kpi.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.joystream.kpi.domain.*; // for static metamodels
import org.joystream.kpi.domain.KpiRound;
import org.joystream.kpi.repository.KpiRoundRepository;
import org.joystream.kpi.service.criteria.KpiRoundCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link KpiRound} entities in the database.
 * The main input is a {@link KpiRoundCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link KpiRound} or a {@link Page} of {@link KpiRound} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class KpiRoundQueryService extends QueryService<KpiRound> {

    private final Logger log = LoggerFactory.getLogger(KpiRoundQueryService.class);

    private final KpiRoundRepository kpiRoundRepository;

    public KpiRoundQueryService(KpiRoundRepository kpiRoundRepository) {
        this.kpiRoundRepository = kpiRoundRepository;
    }

    /**
     * Return a {@link List} of {@link KpiRound} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<KpiRound> findByCriteria(KpiRoundCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<KpiRound> specification = createSpecification(criteria);
        return kpiRoundRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link KpiRound} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<KpiRound> findByCriteria(KpiRoundCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<KpiRound> specification = createSpecification(criteria);
        return kpiRoundRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(KpiRoundCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<KpiRound> specification = createSpecification(criteria);
        return kpiRoundRepository.count(specification);
    }

    /**
     * Function to convert {@link KpiRoundCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<KpiRound> createSpecification(KpiRoundCriteria criteria) {
        Specification<KpiRound> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), KpiRound_.id));
            }
            if (criteria.getDisplayName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDisplayName(), KpiRound_.displayName));
            }
            if (criteria.getTotalPossibleRewards() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getTotalPossibleRewards(), KpiRound_.totalPossibleRewards));
            }
            if (criteria.getCouncilElectedInRound() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCouncilElectedInRound(), KpiRound_.councilElectedInRound));
            }
            if (criteria.getCouncilMembers() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCouncilMembers(), KpiRound_.councilMembers));
            }
            if (criteria.getTermLength() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTermLength(), KpiRound_.termLength));
            }
            if (criteria.getStartBlock() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartBlock(), KpiRound_.startBlock));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), KpiRound_.startDate));
            }
            if (criteria.getEndBlock() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndBlock(), KpiRound_.endBlock));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), KpiRound_.endDate));
            }
            if (criteria.getTermSummary() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTermSummary(), KpiRound_.termSummary));
            }
            if (criteria.getSummarySubmissionDeadline() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getSummarySubmissionDeadline(), KpiRound_.summarySubmissionDeadline)
                    );
            }
            if (criteria.getMaxFiatPoolDifference() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getMaxFiatPoolDifference(), KpiRound_.maxFiatPoolDifference));
            }
            if (criteria.getNumberOfKpis() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumberOfKpis(), KpiRound_.numberOfKpis));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), KpiRound_.notes));
            }
            if (criteria.getKpisId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getKpisId(), root -> root.join(KpiRound_.kpis, JoinType.LEFT).get(Kpi_.id))
                    );
            }
        }
        return specification;
    }
}
