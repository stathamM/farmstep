package com.farmstep.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.farmstep.dto.DiagnosisRequest;
import com.farmstep.dto.DiagnosisResponse;
import com.farmstep.service.DiagnosisService;

@Controller
public class DiagnosisController {

    private final DiagnosisService diagnosisService;

    public DiagnosisController(DiagnosisService diagnosisService) {
        this.diagnosisService = diagnosisService;
    }

    @PostMapping("/result")
    public String result(DiagnosisRequest request, Model model, HttpSession session) {

        DiagnosisResponse result = diagnosisService.diagnose(request);

        if (request.getUserId() == null) {
            session.setAttribute("pendingDiagnosisResultId", result.getDiagnosisResultId());
            session.setAttribute("pendingDiagnosisType", result.getType());
        }
        
        boolean loggedIn = request.getUserId() != null;
        model.addAttribute("loggedIn", loggedIn);

        model.addAttribute("result", result);

        return "result";
    }
}