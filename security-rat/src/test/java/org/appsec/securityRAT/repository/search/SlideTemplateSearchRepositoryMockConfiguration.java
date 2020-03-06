package org.appsec.securityrat.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link SlideTemplateSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class SlideTemplateSearchRepositoryMockConfiguration {

    @MockBean
    private SlideTemplateSearchRepository mockSlideTemplateSearchRepository;

}
