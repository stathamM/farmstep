package com.farmstep.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.farmstep.entity.DiagnosisResultEntity;

public interface DiagnosisResultRepository extends JpaRepository<DiagnosisResultEntity, Long> {
}