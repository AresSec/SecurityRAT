package org.appsec.securityrat.repository;

import org.appsec.securityrat.domain.CollectionCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CollectionCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollectionCategoryRepository extends JpaRepository<CollectionCategory, Long> {

}
