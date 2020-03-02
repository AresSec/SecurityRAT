package org.appsec.securityrat.repository;

import org.appsec.securityrat.domain.StatusColumnValue;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StatusColumnValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusColumnValueRepository extends JpaRepository<StatusColumnValue, Long> {

}
