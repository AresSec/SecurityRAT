package org.appsec.securityrat.repository;

import org.appsec.securityrat.domain.TrainingRequirementNode;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TrainingRequirementNode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainingRequirementNodeRepository extends JpaRepository<TrainingRequirementNode, Long> {

}
