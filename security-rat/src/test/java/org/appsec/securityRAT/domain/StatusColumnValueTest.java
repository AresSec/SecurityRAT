package org.appsec.securityrat.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.appsec.securityrat.web.rest.TestUtil;

public class StatusColumnValueTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatusColumnValue.class);
        StatusColumnValue statusColumnValue1 = new StatusColumnValue();
        statusColumnValue1.setId(1L);
        StatusColumnValue statusColumnValue2 = new StatusColumnValue();
        statusColumnValue2.setId(statusColumnValue1.getId());
        assertThat(statusColumnValue1).isEqualTo(statusColumnValue2);
        statusColumnValue2.setId(2L);
        assertThat(statusColumnValue1).isNotEqualTo(statusColumnValue2);
        statusColumnValue1.setId(null);
        assertThat(statusColumnValue1).isNotEqualTo(statusColumnValue2);
    }
}
