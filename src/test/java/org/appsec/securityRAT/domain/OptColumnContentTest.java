package org.appsec.securityrat.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.appsec.securityrat.web.rest.TestUtil;

public class OptColumnContentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptColumnContent.class);
        OptColumnContent optColumnContent1 = new OptColumnContent();
        optColumnContent1.setId(1L);
        OptColumnContent optColumnContent2 = new OptColumnContent();
        optColumnContent2.setId(optColumnContent1.getId());
        assertThat(optColumnContent1).isEqualTo(optColumnContent2);
        optColumnContent2.setId(2L);
        assertThat(optColumnContent1).isNotEqualTo(optColumnContent2);
        optColumnContent1.setId(null);
        assertThat(optColumnContent1).isNotEqualTo(optColumnContent2);
    }
}
