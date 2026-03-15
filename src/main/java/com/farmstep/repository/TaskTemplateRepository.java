package com.farmstep.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.farmstep.entity.TaskTemplateEntity;

public interface TaskTemplateRepository extends JpaRepository<TaskTemplateEntity, Long> {
    List<TaskTemplateEntity> findByDiagnosisTypeAndIsActiveTrueOrderBySortOrderAscIdAsc(String diagnosisType);
}