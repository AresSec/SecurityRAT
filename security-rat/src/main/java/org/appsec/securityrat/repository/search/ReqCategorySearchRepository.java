package org.appsec.securityrat.repository.search;

import org.appsec.securityrat.domain.ReqCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ReqCategory} entity.
 */
public interface ReqCategorySearchRepository extends ElasticsearchRepository<ReqCategory, Long> {
}
