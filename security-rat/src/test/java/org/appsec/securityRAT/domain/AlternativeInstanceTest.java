package org.appsec.securityrat.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.appsec.securityrat.web.rest.TestUtil;

public class AlternativeInstanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlternativeInstance.class);
        AlternativeInstance alternativeInstance1 = new AlternativeInstance();
        alternativeInstance1.setId(1L);
        AlternativeInstance alternativeInstance2 = new AlternativeInstance();
        alternativeInstance2.setId(alternativeInstance1.getId());
        assertThat(alternativeInstance1).isEqualTo(alternativeInstance2);
        alternativeInstance2.setId(2L);
        assertThat(alternativeInstance1).isNotEqualTo(alternativeInstance2);
        alternativeInstance1.setId(null);
        assertThat(alternativeInstance1).isNotEqualTo(alternativeInstance2);
    }
}
