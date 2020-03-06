package org.appsec.securityrat.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.appsec.securityrat.web.rest.TestUtil;

public class TrainingGeneratedSlideNodeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingGeneratedSlideNode.class);
        TrainingGeneratedSlideNode trainingGeneratedSlideNode1 = new TrainingGeneratedSlideNode();
        trainingGeneratedSlideNode1.setId(1L);
        TrainingGeneratedSlideNode trainingGeneratedSlideNode2 = new TrainingGeneratedSlideNode();
        trainingGeneratedSlideNode2.setId(trainingGeneratedSlideNode1.getId());
        assertThat(trainingGeneratedSlideNode1).isEqualTo(trainingGeneratedSlideNode2);
        trainingGeneratedSlideNode2.setId(2L);
        assertThat(trainingGeneratedSlideNode1).isNotEqualTo(trainingGeneratedSlideNode2);
        trainingGeneratedSlideNode1.setId(null);
        assertThat(trainingGeneratedSlideNode1).isNotEqualTo(trainingGeneratedSlideNode2);
    }
}
