package org.appsec.securityrat.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.appsec.securityrat.web.rest.TestUtil;

public class CollectionInstanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollectionInstance.class);
        CollectionInstance collectionInstance1 = new CollectionInstance();
        collectionInstance1.setId(1L);
        CollectionInstance collectionInstance2 = new CollectionInstance();
        collectionInstance2.setId(collectionInstance1.getId());
        assertThat(collectionInstance1).isEqualTo(collectionInstance2);
        collectionInstance2.setId(2L);
        assertThat(collectionInstance1).isNotEqualTo(collectionInstance2);
        collectionInstance1.setId(null);
        assertThat(collectionInstance1).isNotEqualTo(collectionInstance2);
    }
}
