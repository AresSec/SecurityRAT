package org.appsec.securityrat.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.appsec.securityrat.web.rest.TestUtil;

public class TrainingBranchNodeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingBranchNode.class);
        TrainingBranchNode trainingBranchNode1 = new TrainingBranchNode();
        trainingBranchNode1.setId(1L);
        TrainingBranchNode trainingBranchNode2 = new TrainingBranchNode();
        trainingBranchNode2.setId(trainingBranchNode1.getId());
        assertThat(trainingBranchNode1).isEqualTo(trainingBranchNode2);
        trainingBranchNode2.setId(2L);
        assertThat(trainingBranchNode1).isNotEqualTo(trainingBranchNode2);
        trainingBranchNode1.setId(null);
        assertThat(trainingBranchNode1).isNotEqualTo(trainingBranchNode2);
    }
}
