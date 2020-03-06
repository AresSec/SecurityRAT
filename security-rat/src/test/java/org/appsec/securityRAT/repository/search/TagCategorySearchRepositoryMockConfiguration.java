package org.appsec.securityrat.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link TagCategorySearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class TagCategorySearchRepositoryMockConfiguration {

    @MockBean
    private TagCategorySearchRepository mockTagCategorySearchRepository;

}
