package org.appsec.securityrat.repository.search;

import org.appsec.securityrat.domain.AlternativeInstance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AlternativeInstance} entity.
 */
public interface AlternativeInstanceSearchRepository extends ElasticsearchRepository<AlternativeInstance, Long> {
}
