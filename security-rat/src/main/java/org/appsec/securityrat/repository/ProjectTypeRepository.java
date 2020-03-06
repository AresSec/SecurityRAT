package org.appsec.securityrat.repository;

import org.appsec.securityrat.domain.ProjectType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ProjectType entity.
 */
@Repository
public interface ProjectTypeRepository extends JpaRepository<ProjectType, Long> {

    @Query(value = "select distinct projectType from ProjectType projectType left join fetch projectType.statusColumns left join fetch projectType.optColumns",
        countQuery = "select count(distinct projectType) from ProjectType projectType")
    Page<ProjectType> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct projectType from ProjectType projectType left join fetch projectType.statusColumns left join fetch projectType.optColumns")
    List<ProjectType> findAllWithEagerRelationships();

    @Query("select projectType from ProjectType projectType left join fetch projectType.statusColumns left join fetch projectType.optColumns where projectType.id =:id")
    Optional<ProjectType> findOneWithEagerRelationships(@Param("id") Long id);

}
