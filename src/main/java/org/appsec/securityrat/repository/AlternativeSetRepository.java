package org.appsec.securityrat.repository;

import org.appsec.securityrat.domain.AlternativeSet;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AlternativeSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlternativeSetRepository extends JpaRepository<AlternativeSet, Long> {

}
