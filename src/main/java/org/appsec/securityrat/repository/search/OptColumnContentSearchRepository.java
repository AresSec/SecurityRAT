package org.appsec.securityrat.repository.search;

import org.appsec.securityrat.domain.OptColumnContent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link OptColumnContent} entity.
 */
public interface OptColumnContentSearchRepository extends ElasticsearchRepository<OptColumnContent, Long> {
}
