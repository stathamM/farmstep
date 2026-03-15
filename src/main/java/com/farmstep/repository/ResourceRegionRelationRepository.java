package com.farmstep.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.farmstep.entity.ResourceEntity;
import com.farmstep.entity.ResourceRegionRelationEntity;

public interface ResourceRegionRelationRepository extends JpaRepository<ResourceRegionRelationEntity, Long> {

    List<ResourceRegionRelationEntity> findByResource(ResourceEntity resource);
}