package com.farmstep.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.farmstep.entity.ResourceEntity;

public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {

    List<ResourceEntity> findByDiagnosisTypeAndIsActiveTrue(String diagnosisType);
}