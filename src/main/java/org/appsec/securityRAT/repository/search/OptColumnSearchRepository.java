package org.appsec.securityrat.repository.search;

import org.appsec.securityrat.domain.OptColumn;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link OptColumn} entity.
 */
public interface OptColumnSearchRepository extends ElasticsearchRepository<OptColumn, Long> {
}
