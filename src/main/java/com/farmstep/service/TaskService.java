package com.farmstep.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.farmstep.dto.ResourceLinkDto;
import com.farmstep.entity.TaskResourceEntity;
import com.farmstep.entity.TaskTemplateEntity;
import com.farmstep.entity.UserTaskEntity;
import com.farmstep.repository.ResourceRepository;
import com.farmstep.repository.TaskResourceRepository;
import com.farmstep.repository.TaskTemplateRepository;
import com.farmstep.repository.UserTaskRepository;

@Service
public class TaskService {

    private final TaskTemplateRepository taskTemplateRepository;
    private final UserTaskRepository userTaskRepository;
    private final TaskResourceRepository taskResourceRepository;
    private final ResourceRepository resourceRepository;

    public TaskService(
            TaskTemplateRepository taskTemplateRepository,
            UserTaskRepository userTaskRepository,
            TaskResourceRepository taskResourceRepository,
            ResourceRepository resourceRepository
    ) {
        this.taskTemplateRepository = taskTemplateRepository;
        this.userTaskRepository = userTaskRepository;
        this.taskResourceRepository = taskResourceRepository;
        this.resourceRepository = resourceRepository;
    }

    public void createInitialTasks(Long userId, Long diagnosisResultId, String diagnosisType) {
        List<TaskTemplateEntity> templates =
                taskTemplateRepository.findByDiagnosisTypeAndIsActiveTrueOrderBySortOrderAscIdAsc(diagnosisType);

        for (TaskTemplateEntity template : templates) {
            UserTaskEntity task = new UserTaskEntity();
            task.setUserId(userId);
            task.setDiagnosisResultId(diagnosisResultId);
            task.setTitle(template.getTitle());
            task.setDescription(template.getDescription());
            task.setStatus("TODO");
            task.setSortOrder(template.getSortOrder());

            userTaskRepository.save(task);
        }
    }
    
    public List<ResourceLinkDto> findResourcesByTaskId(Long taskId) {

        List<TaskResourceEntity> mappings =
        		taskResourceRepository.findByTaskId(taskId);

        List<Long> resourceIds = mappings.stream()
                .map(TaskResourceEntity::getResourceId)
                .toList();

        return resourceRepository.findAllById(resourceIds)
                .stream()
                .map(r -> new ResourceLinkDto(
                        r.getTitle(),
                        r.getDescription(),
                        r.getUrl(),
                        r.getResourceType()
                ))
                .toList();
    }

    public List<UserTaskEntity> findTasksByUserId(Long userId) {
        return userTaskRepository.findByUserIdOrderBySortOrderAscIdAsc(userId);
    }

    public void updateTaskStatus(Long taskId, String status) {
        UserTaskEntity task = userTaskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("タスクが見つかりません。"));

        task.setStatus(status);
        userTaskRepository.save(task);
    }
}