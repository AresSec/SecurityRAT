package org.appsec.securityrat.repository.search;

import org.appsec.securityrat.domain.TrainingGeneratedSlideNode;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TrainingGeneratedSlideNode} entity.
 */
public interface TrainingGeneratedSlideNodeSearchRepository extends ElasticsearchRepository<TrainingGeneratedSlideNode, Long> {
}
