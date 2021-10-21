package org.joystream.kpi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.joystream.kpi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KpiRoundTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KpiRound.class);
        KpiRound kpiRound1 = new KpiRound();
        kpiRound1.setId(1L);
        KpiRound kpiRound2 = new KpiRound();
        kpiRound2.setId(kpiRound1.getId());
        assertThat(kpiRound1).isEqualTo(kpiRound2);
        kpiRound2.setId(2L);
        assertThat(kpiRound1).isNotEqualTo(kpiRound2);
        kpiRound1.setId(null);
        assertThat(kpiRound1).isNotEqualTo(kpiRound2);
    }
}
