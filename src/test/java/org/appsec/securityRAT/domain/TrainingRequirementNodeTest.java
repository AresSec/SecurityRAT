package org.appsec.securityrat.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.appsec.securityrat.web.rest.TestUtil;

public class TrainingRequirementNodeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingRequirementNode.class);
        TrainingRequirementNode trainingRequirementNode1 = new TrainingRequirementNode();
        trainingRequirementNode1.setId(1L);
        TrainingRequirementNode trainingRequirementNode2 = new TrainingRequirementNode();
        trainingRequirementNode2.setId(trainingRequirementNode1.getId());
        assertThat(trainingRequirementNode1).isEqualTo(trainingRequirementNode2);
        trainingRequirementNode2.setId(2L);
        assertThat(trainingRequirementNode1).isNotEqualTo(trainingRequirementNode2);
        trainingRequirementNode1.setId(null);
        assertThat(trainingRequirementNode1).isNotEqualTo(trainingRequirementNode2);
    }
}
