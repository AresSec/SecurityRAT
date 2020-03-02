package org.appsec.securityrat.repository;

import org.appsec.securityrat.domain.TrainingCategoryNode;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TrainingCategoryNode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainingCategoryNodeRepository extends JpaRepository<TrainingCategoryNode, Long> {

}
