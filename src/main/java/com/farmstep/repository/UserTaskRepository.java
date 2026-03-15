package com.farmstep.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.farmstep.entity.UserTaskEntity;

public interface UserTaskRepository extends JpaRepository<UserTaskEntity, Long> {
    List<UserTaskEntity> findByUserIdOrderBySortOrderAscIdAsc(Long userId);
}