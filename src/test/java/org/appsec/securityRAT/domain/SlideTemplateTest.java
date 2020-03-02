package org.appsec.securityrat.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.appsec.securityrat.web.rest.TestUtil;

public class SlideTemplateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SlideTemplate.class);
        SlideTemplate slideTemplate1 = new SlideTemplate();
        slideTemplate1.setId(1L);
        SlideTemplate slideTemplate2 = new SlideTemplate();
        slideTemplate2.setId(slideTemplate1.getId());
        assertThat(slideTemplate1).isEqualTo(slideTemplate2);
        slideTemplate2.setId(2L);
        assertThat(slideTemplate1).isNotEqualTo(slideTemplate2);
        slideTemplate1.setId(null);
        assertThat(slideTemplate1).isNotEqualTo(slideTemplate2);
    }
}
