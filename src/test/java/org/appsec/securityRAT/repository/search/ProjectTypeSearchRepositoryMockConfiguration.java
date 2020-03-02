package org.appsec.securityrat.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ProjectTypeSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ProjectTypeSearchRepositoryMockConfiguration {

    @MockBean
    private ProjectTypeSearchRepository mockProjectTypeSearchRepository;

}
