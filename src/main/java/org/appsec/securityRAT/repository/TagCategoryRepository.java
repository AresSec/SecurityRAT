package org.appsec.securityrat.repository;

import org.appsec.securityrat.domain.TagCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TagCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TagCategoryRepository extends JpaRepository<TagCategory, Long> {

}
