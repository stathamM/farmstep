package com.farmstep.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.farmstep.entity.RegionEntity;

public interface RegionRepository extends JpaRepository<RegionEntity, Long> {
}