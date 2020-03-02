package org.appsec.securityrat.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.appsec.securityrat.web.rest.TestUtil;

public class OptColumnTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptColumnType.class);
        OptColumnType optColumnType1 = new OptColumnType();
        optColumnType1.setId(1L);
        OptColumnType optColumnType2 = new OptColumnType();
        optColumnType2.setId(optColumnType1.getId());
        assertThat(optColumnType1).isEqualTo(optColumnType2);
        optColumnType2.setId(2L);
        assertThat(optColumnType1).isNotEqualTo(optColumnType2);
        optColumnType1.setId(null);
        assertThat(optColumnType1).isNotEqualTo(optColumnType2);
    }
}
