package org.appsec.securityrat.repository;

import org.appsec.securityrat.domain.TrainingCustomSlideNode;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TrainingCustomSlideNode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainingCustomSlideNodeRepository extends JpaRepository<TrainingCustomSlideNode, Long> {

}
