package org.appsec.securityrat.repository.search;

import org.appsec.securityrat.domain.RequirementSkeleton;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link RequirementSkeleton} entity.
 */
public interface RequirementSkeletonSearchRepository extends ElasticsearchRepository<RequirementSkeleton, Long> {
}
