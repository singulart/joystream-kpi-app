package org.joystream.kpi.repository;

import org.joystream.kpi.domain.Kpi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Kpi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KpiRepository extends JpaRepository<Kpi, Long> {}
