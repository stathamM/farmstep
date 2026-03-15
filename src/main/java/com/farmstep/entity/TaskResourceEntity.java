package com.farmstep.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "task_resources")
public class TaskResourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "resource_id")
    private Long resourceId;

    public Long getId() {
        return id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
}