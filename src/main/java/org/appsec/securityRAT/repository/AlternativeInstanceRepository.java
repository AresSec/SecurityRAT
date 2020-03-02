package org.appsec.securityrat.repository;

import org.appsec.securityrat.domain.AlternativeInstance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AlternativeInstance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlternativeInstanceRepository extends JpaRepository<AlternativeInstance, Long> {

}
