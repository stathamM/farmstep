package com.farmstep.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.farmstep.dto.ResourceLinkDto;
import com.farmstep.entity.UserTaskEntity;
import com.farmstep.service.TaskService;

@Controller
public class MyPageController {

    private final TaskService taskService;

    public MyPageController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        List<UserTaskEntity> tasks = taskService.findTasksByUserId(userId);

        Map<Long, List<ResourceLinkDto>> taskResources = new HashMap<>();

        for (UserTaskEntity task : tasks) {
            List<ResourceLinkDto> links =
                    taskService.findResourcesByTaskId(task.getId());

            taskResources.put(task.getId(), links);
        }

        model.addAttribute("tasks", tasks);
        model.addAttribute("taskResources", taskResources);
        model.addAttribute("userName", session.getAttribute("userName"));

        return "mypage";
    }
    
    @PostMapping("/task/complete")
    public String completeTask(
            @RequestParam Long taskId
    ) {

        taskService.updateTaskStatus(taskId, "DONE");

        return "redirect:/mypage";
    }
}