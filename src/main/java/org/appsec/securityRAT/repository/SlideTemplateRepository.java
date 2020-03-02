package org.appsec.securityrat.repository;

import org.appsec.securityrat.domain.SlideTemplate;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SlideTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SlideTemplateRepository extends JpaRepository<SlideTemplate, Long> {

}
