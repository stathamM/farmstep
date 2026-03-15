package com.farmstep.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.farmstep.entity.UserEntity;
import com.farmstep.service.TaskService;
import com.farmstep.service.UserService;

@Controller
public class AuthController {

    private final UserService userService;
    private final TaskService taskService;

    public AuthController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }


    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session
    ) {
        UserEntity user = userService.register(name, email, password);

        session.setAttribute("userId", user.getId());
        session.setAttribute("userName", user.getName());

        Long pendingDiagnosisResultId = (Long) session.getAttribute("pendingDiagnosisResultId");
        String pendingDiagnosisType = (String) session.getAttribute("pendingDiagnosisType");

        if (pendingDiagnosisResultId != null && pendingDiagnosisType != null) {
            taskService.createInitialTasks(
                    user.getId(),
                    pendingDiagnosisResultId,
                    pendingDiagnosisType
            );

            session.removeAttribute("pendingDiagnosisResultId");
            session.removeAttribute("pendingDiagnosisType");
        }

        return "redirect:/mypage";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model
    ) {

        UserEntity user = userService.login(email, password);

        if (user == null) {
            model.addAttribute("error", "メールアドレスまたはパスワードが違います");
            return "login";
        }

        session.setAttribute("userId", user.getId());
        session.setAttribute("userName", user.getName());

        Long pendingDiagnosisResultId = (Long) session.getAttribute("pendingDiagnosisResultId");
        String pendingDiagnosisType = (String) session.getAttribute("pendingDiagnosisType");

        if (pendingDiagnosisResultId != null && pendingDiagnosisType != null) {
            taskService.createInitialTasks(
                    user.getId(),
                    pendingDiagnosisResultId,
                    pendingDiagnosisType
            );

            session.removeAttribute("pendingDiagnosisResultId");
            session.removeAttribute("pendingDiagnosisType");
        }

        return "redirect:/mypage";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}