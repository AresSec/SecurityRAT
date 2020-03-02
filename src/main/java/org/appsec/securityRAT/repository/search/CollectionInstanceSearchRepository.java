package org.appsec.securityrat.repository.search;

import org.appsec.securityrat.domain.CollectionInstance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CollectionInstance} entity.
 */
public interface CollectionInstanceSearchRepository extends ElasticsearchRepository<CollectionInstance, Long> {
}
