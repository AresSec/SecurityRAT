package org.appsec.securityrat.repository;

import org.appsec.securityrat.domain.CollectionInstance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CollectionInstance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollectionInstanceRepository extends JpaRepository<CollectionInstance, Long> {

}
