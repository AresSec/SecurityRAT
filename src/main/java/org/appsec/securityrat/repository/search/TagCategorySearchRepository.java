package org.appsec.securityrat.repository.search;

import org.appsec.securityrat.domain.TagCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TagCategory} entity.
 */
public interface TagCategorySearchRepository extends ElasticsearchRepository<TagCategory, Long> {
}
