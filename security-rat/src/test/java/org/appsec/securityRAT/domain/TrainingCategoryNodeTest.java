package org.appsec.securityrat.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.appsec.securityrat.web.rest.TestUtil;

public class TrainingCategoryNodeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingCategoryNode.class);
        TrainingCategoryNode trainingCategoryNode1 = new TrainingCategoryNode();
        trainingCategoryNode1.setId(1L);
        TrainingCategoryNode trainingCategoryNode2 = new TrainingCategoryNode();
        trainingCategoryNode2.setId(trainingCategoryNode1.getId());
        assertThat(trainingCategoryNode1).isEqualTo(trainingCategoryNode2);
        trainingCategoryNode2.setId(2L);
        assertThat(trainingCategoryNode1).isNotEqualTo(trainingCategoryNode2);
        trainingCategoryNode1.setId(null);
        assertThat(trainingCategoryNode1).isNotEqualTo(trainingCategoryNode2);
    }
}
