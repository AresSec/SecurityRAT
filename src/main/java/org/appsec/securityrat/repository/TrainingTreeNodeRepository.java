package org.appsec.securityrat.repository;

import org.appsec.securityrat.domain.TrainingTreeNode;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TrainingTreeNode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainingTreeNodeRepository extends JpaRepository<TrainingTreeNode, Long> {

}
