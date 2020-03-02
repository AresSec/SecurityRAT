package org.appsec.securityrat.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.appsec.securityrat.web.rest.TestUtil;

public class RequirementSkeletonTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequirementSkeleton.class);
        RequirementSkeleton requirementSkeleton1 = new RequirementSkeleton();
        requirementSkeleton1.setId(1L);
        RequirementSkeleton requirementSkeleton2 = new RequirementSkeleton();
        requirementSkeleton2.setId(requirementSkeleton1.getId());
        assertThat(requirementSkeleton1).isEqualTo(requirementSkeleton2);
        requirementSkeleton2.setId(2L);
        assertThat(requirementSkeleton1).isNotEqualTo(requirementSkeleton2);
        requirementSkeleton1.setId(null);
        assertThat(requirementSkeleton1).isNotEqualTo(requirementSkeleton2);
    }
}
