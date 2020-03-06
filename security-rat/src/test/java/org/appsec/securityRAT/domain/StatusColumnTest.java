package org.appsec.securityrat.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.appsec.securityrat.web.rest.TestUtil;

public class StatusColumnTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatusColumn.class);
        StatusColumn statusColumn1 = new StatusColumn();
        statusColumn1.setId(1L);
        StatusColumn statusColumn2 = new StatusColumn();
        statusColumn2.setId(statusColumn1.getId());
        assertThat(statusColumn1).isEqualTo(statusColumn2);
        statusColumn2.setId(2L);
        assertThat(statusColumn1).isNotEqualTo(statusColumn2);
        statusColumn1.setId(null);
        assertThat(statusColumn1).isNotEqualTo(statusColumn2);
    }
}
