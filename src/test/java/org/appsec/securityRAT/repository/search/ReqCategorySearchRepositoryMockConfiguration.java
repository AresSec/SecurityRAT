package org.appsec.securityrat.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ReqCategorySearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ReqCategorySearchRepositoryMockConfiguration {

    @MockBean
    private ReqCategorySearchRepository mockReqCategorySearchRepository;

}
