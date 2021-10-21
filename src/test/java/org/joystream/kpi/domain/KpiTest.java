package org.joystream.kpi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.joystream.kpi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KpiTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kpi.class);
        Kpi kpi1 = new Kpi();
        kpi1.setId(1L);
        Kpi kpi2 = new Kpi();
        kpi2.setId(kpi1.getId());
        assertThat(kpi1).isEqualTo(kpi2);
        kpi2.setId(2L);
        assertThat(kpi1).isNotEqualTo(kpi2);
        kpi1.setId(null);
        assertThat(kpi1).isNotEqualTo(kpi2);
    }
}
