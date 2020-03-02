package org.appsec.securityrat.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.appsec.securityrat.web.rest.TestUtil;

public class TrainingCustomSlideNodeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingCustomSlideNode.class);
        TrainingCustomSlideNode trainingCustomSlideNode1 = new TrainingCustomSlideNode();
        trainingCustomSlideNode1.setId(1L);
        TrainingCustomSlideNode trainingCustomSlideNode2 = new TrainingCustomSlideNode();
        trainingCustomSlideNode2.setId(trainingCustomSlideNode1.getId());
        assertThat(trainingCustomSlideNode1).isEqualTo(trainingCustomSlideNode2);
        trainingCustomSlideNode2.setId(2L);
        assertThat(trainingCustomSlideNode1).isNotEqualTo(trainingCustomSlideNode2);
        trainingCustomSlideNode1.setId(null);
        assertThat(trainingCustomSlideNode1).isNotEqualTo(trainingCustomSlideNode2);
    }
}
