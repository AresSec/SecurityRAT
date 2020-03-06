package org.appsec.securityrat.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link CollectionCategorySearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CollectionCategorySearchRepositoryMockConfiguration {

    @MockBean
    private CollectionCategorySearchRepository mockCollectionCategorySearchRepository;

}
