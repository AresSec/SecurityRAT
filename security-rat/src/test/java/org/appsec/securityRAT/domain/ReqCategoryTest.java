package org.appsec.securityrat.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.appsec.securityrat.web.rest.TestUtil;

public class ReqCategoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReqCategory.class);
        ReqCategory reqCategory1 = new ReqCategory();
        reqCategory1.setId(1L);
        ReqCategory reqCategory2 = new ReqCategory();
        reqCategory2.setId(reqCategory1.getId());
        assertThat(reqCategory1).isEqualTo(reqCategory2);
        reqCategory2.setId(2L);
        assertThat(reqCategory1).isNotEqualTo(reqCategory2);
        reqCategory1.setId(null);
        assertThat(reqCategory1).isNotEqualTo(reqCategory2);
    }
}
