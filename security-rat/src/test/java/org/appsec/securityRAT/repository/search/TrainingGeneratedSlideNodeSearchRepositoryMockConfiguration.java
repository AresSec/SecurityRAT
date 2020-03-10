package org.appsec.securityrat.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link TrainingGeneratedSlideNodeSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class TrainingGeneratedSlideNodeSearchRepositoryMockConfiguration {

    @MockBean
    private TrainingGeneratedSlideNodeSearchRepository mockTrainingGeneratedSlideNodeSearchRepository;

}