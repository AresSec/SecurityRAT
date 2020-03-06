package org.appsec.securityrat.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link TrainingCustomSlideNodeSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class TrainingCustomSlideNodeSearchRepositoryMockConfiguration {

    @MockBean
    private TrainingCustomSlideNodeSearchRepository mockTrainingCustomSlideNodeSearchRepository;

}
