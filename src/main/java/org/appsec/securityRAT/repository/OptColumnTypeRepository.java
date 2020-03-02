package org.appsec.securityrat.repository;

import org.appsec.securityrat.domain.OptColumnType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OptColumnType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OptColumnTypeRepository extends JpaRepository<OptColumnType, Long> {

}
