package org.appsec.securityrat.repository;

import org.appsec.securityrat.domain.RequirementSkeleton;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the RequirementSkeleton entity.
 */
@Repository
public interface RequirementSkeletonRepository extends JpaRepository<RequirementSkeleton, Long> {

    @Query(value = "select distinct requirementSkeleton from RequirementSkeleton requirementSkeleton left join fetch requirementSkeleton.tagInstances left join fetch requirementSkeleton.collectionInstances left join fetch requirementSkeleton.projectTypes",
        countQuery = "select count(distinct requirementSkeleton) from RequirementSkeleton requirementSkeleton")
    Page<RequirementSkeleton> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct requirementSkeleton from RequirementSkeleton requirementSkeleton left join fetch requirementSkeleton.tagInstances left join fetch requirementSkeleton.collectionInstances left join fetch requirementSkeleton.projectTypes")
    List<RequirementSkeleton> findAllWithEagerRelationships();

    @Query("select requirementSkeleton from RequirementSkeleton requirementSkeleton left join fetch requirementSkeleton.tagInstances left join fetch requirementSkeleton.collectionInstances left join fetch requirementSkeleton.projectTypes where requirementSkeleton.id =:id")
    Optional<RequirementSkeleton> findOneWithEagerRelationships(@Param("id") Long id);

}
