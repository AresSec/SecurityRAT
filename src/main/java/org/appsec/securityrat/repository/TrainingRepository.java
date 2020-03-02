package org.appsec.securityrat.repository;

import org.appsec.securityrat.domain.Training;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Training entity.
 */
@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

    @Query(value = "select distinct training from Training training left join fetch training.optColumns left join fetch training.collections left join fetch training.projectTypes",
        countQuery = "select count(distinct training) from Training training")
    Page<Training> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct training from Training training left join fetch training.optColumns left join fetch training.collections left join fetch training.projectTypes")
    List<Training> findAllWithEagerRelationships();

    @Query("select training from Training training left join fetch training.optColumns left join fetch training.collections left join fetch training.projectTypes where training.id =:id")
    Optional<Training> findOneWithEagerRelationships(@Param("id") Long id);

}
