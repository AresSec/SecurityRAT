package org.appsec.securityrat.repository.search;

import org.appsec.securityrat.domain.SlideTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link SlideTemplate} entity.
 */
public interface SlideTemplateSearchRepository extends ElasticsearchRepository<SlideTemplate, Long> {
}
