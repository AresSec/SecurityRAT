package org.appsec.securityrat.repository.search;

import org.appsec.securityrat.domain.ProjectType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ProjectType} entity.
 */
public interface ProjectTypeSearchRepository extends ElasticsearchRepository<ProjectType, Long> {
}
