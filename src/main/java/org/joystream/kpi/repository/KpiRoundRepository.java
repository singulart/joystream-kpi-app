package org.joystream.kpi.repository;

import org.joystream.kpi.domain.KpiRound;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the KpiRound entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KpiRoundRepository extends JpaRepository<KpiRound, Long> {}
