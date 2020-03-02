package org.appsec.securityrat.repository.search;

import org.appsec.securityrat.domain.TrainingBranchNode;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TrainingBranchNode} entity.
 */
public interface TrainingBranchNodeSearchRepository extends ElasticsearchRepository<TrainingBranchNode, Long> {
}
