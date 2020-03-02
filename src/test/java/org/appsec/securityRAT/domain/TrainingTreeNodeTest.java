package org.appsec.securityrat.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.appsec.securityrat.web.rest.TestUtil;

public class TrainingTreeNodeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingTreeNode.class);
        TrainingTreeNode trainingTreeNode1 = new TrainingTreeNode();
        trainingTreeNode1.setId(1L);
        TrainingTreeNode trainingTreeNode2 = new TrainingTreeNode();
        trainingTreeNode2.setId(trainingTreeNode1.getId());
        assertThat(trainingTreeNode1).isEqualTo(trainingTreeNode2);
        trainingTreeNode2.setId(2L);
        assertThat(trainingTreeNode1).isNotEqualTo(trainingTreeNode2);
        trainingTreeNode1.setId(null);
        assertThat(trainingTreeNode1).isNotEqualTo(trainingTreeNode2);
    }
}
