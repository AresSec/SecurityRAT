package org.appsec.securityrat.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.appsec.securityrat.web.rest.TestUtil;

public class OptColumnTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptColumn.class);
        OptColumn optColumn1 = new OptColumn();
        optColumn1.setId(1L);
        OptColumn optColumn2 = new OptColumn();
        optColumn2.setId(optColumn1.getId());
        assertThat(optColumn1).isEqualTo(optColumn2);
        optColumn2.setId(2L);
        assertThat(optColumn1).isNotEqualTo(optColumn2);
        optColumn1.setId(null);
        assertThat(optColumn1).isNotEqualTo(optColumn2);
    }
}
