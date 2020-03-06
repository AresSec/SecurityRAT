package org.appsec.securityrat.repository;

import org.appsec.securityrat.domain.OptColumnContent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OptColumnContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OptColumnContentRepository extends JpaRepository<OptColumnContent, Long> {

}
