package org.appsec.securityrat.repository;

import org.appsec.securityrat.domain.TagInstance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TagInstance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TagInstanceRepository extends JpaRepository<TagInstance, Long> {

}
