package org.appsec.securityrat.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.appsec.securityrat.web.rest.TestUtil;

public class TagInstanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TagInstance.class);
        TagInstance tagInstance1 = new TagInstance();
        tagInstance1.setId(1L);
        TagInstance tagInstance2 = new TagInstance();
        tagInstance2.setId(tagInstance1.getId());
        assertThat(tagInstance1).isEqualTo(tagInstance2);
        tagInstance2.setId(2L);
        assertThat(tagInstance1).isNotEqualTo(tagInstance2);
        tagInstance1.setId(null);
        assertThat(tagInstance1).isNotEqualTo(tagInstance2);
    }
}
