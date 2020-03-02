package org.appsec.securityrat.repository.search;

import org.appsec.securityrat.domain.TrainingCategoryNode;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TrainingCategoryNode} entity.
 */
public interface TrainingCategoryNodeSearchRepository extends ElasticsearchRepository<TrainingCategoryNode, Long> {
}
