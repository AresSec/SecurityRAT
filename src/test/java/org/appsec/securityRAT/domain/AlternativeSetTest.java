package org.appsec.securityrat.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.appsec.securityrat.web.rest.TestUtil;

public class AlternativeSetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlternativeSet.class);
        AlternativeSet alternativeSet1 = new AlternativeSet();
        alternativeSet1.setId(1L);
        AlternativeSet alternativeSet2 = new AlternativeSet();
        alternativeSet2.setId(alternativeSet1.getId());
        assertThat(alternativeSet1).isEqualTo(alternativeSet2);
        alternativeSet2.setId(2L);
        assertThat(alternativeSet1).isNotEqualTo(alternativeSet2);
        alternativeSet1.setId(null);
        assertThat(alternativeSet1).isNotEqualTo(alternativeSet2);
    }
}
