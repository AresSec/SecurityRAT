package org.appsec.securityrat.repository;

import org.appsec.securityrat.domain.ReqCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ReqCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReqCategoryRepository extends JpaRepository<ReqCategory, Long> {

}
