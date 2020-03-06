package org.appsec.securityrat.repository;

import org.appsec.securityrat.domain.OptColumn;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OptColumn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OptColumnRepository extends JpaRepository<OptColumn, Long> {

}
