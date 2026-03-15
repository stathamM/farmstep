package com.farmstep.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.farmstep.entity.TaskResourceEntity;

public interface TaskResourceRepository extends JpaRepository<TaskResourceEntity, Long> {

    List<TaskResourceEntity> findByTaskId(Long taskId);

}