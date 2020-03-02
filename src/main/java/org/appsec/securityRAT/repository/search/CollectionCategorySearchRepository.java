package org.appsec.securityrat.repository.search;

import org.appsec.securityrat.domain.CollectionCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CollectionCategory} entity.
 */
public interface CollectionCategorySearchRepository extends ElasticsearchRepository<CollectionCategory, Long> {
}
