package org.appsec.securityrat.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.appsec.securityrat.web.rest.TestUtil;

public class TagCategoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TagCategory.class);
        TagCategory tagCategory1 = new TagCategory();
        tagCategory1.setId(1L);
        TagCategory tagCategory2 = new TagCategory();
        tagCategory2.setId(tagCategory1.getId());
        assertThat(tagCategory1).isEqualTo(tagCategory2);
        tagCategory2.setId(2L);
        assertThat(tagCategory1).isNotEqualTo(tagCategory2);
        tagCategory1.setId(null);
        assertThat(tagCategory1).isNotEqualTo(tagCategory2);
    }
}
