package org.appsec.securityrat.repository.search;

import org.appsec.securityrat.domain.Training;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Training} entity.
 */
public interface TrainingSearchRepository extends ElasticsearchRepository<Training, Long> {
}
