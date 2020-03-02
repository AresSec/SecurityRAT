package org.appsec.securityrat.repository.search;

import org.appsec.securityrat.domain.StatusColumnValue;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link StatusColumnValue} entity.
 */
public interface StatusColumnValueSearchRepository extends ElasticsearchRepository<StatusColumnValue, Long> {
}
