package org.appsec.securityrat.repository;

import org.appsec.securityrat.domain.TrainingGeneratedSlideNode;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TrainingGeneratedSlideNode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainingGeneratedSlideNodeRepository extends JpaRepository<TrainingGeneratedSlideNode, Long> {

}
