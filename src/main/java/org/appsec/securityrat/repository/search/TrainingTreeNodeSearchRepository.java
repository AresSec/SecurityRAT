package org.appsec.securityrat.repository.search;

import org.appsec.securityrat.domain.TrainingTreeNode;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TrainingTreeNode} entity.
 */
public interface TrainingTreeNodeSearchRepository extends ElasticsearchRepository<TrainingTreeNode, Long> {
}
