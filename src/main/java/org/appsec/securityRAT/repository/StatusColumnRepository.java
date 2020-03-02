package org.appsec.securityrat.repository;

import org.appsec.securityrat.domain.StatusColumn;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StatusColumn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusColumnRepository extends JpaRepository<StatusColumn, Long> {

}
