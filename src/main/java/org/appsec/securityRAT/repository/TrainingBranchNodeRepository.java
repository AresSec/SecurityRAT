package org.appsec.securityrat.repository;

import org.appsec.securityrat.domain.TrainingBranchNode;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TrainingBranchNode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainingBranchNodeRepository extends JpaRepository<TrainingBranchNode, Long> {

}
