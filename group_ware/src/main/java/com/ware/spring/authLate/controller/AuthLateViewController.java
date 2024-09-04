package com.ware.spring.authLate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ware.spring.authLate.domain.AuthLateDto;
import com.ware.spring.authLate.service.AuthLateService;

@Controller
public class AuthLateViewController {

    private final AuthLateService authLateService;

    @Autowired
    public AuthLateViewController(AuthLateService authLateService) {
        this.authLateService = authLateService;
    }

    // 목록 페이지로 이동
    @GetMapping("/authLateList")
    public String selectAuthLateList(Model model) {
        List<AuthLateDto> resultList = authLateService.selectAuthLateList();
        model.addAttribute("resultList", resultList);
        return "authorization/authorizationLateList";
    }

    // 상세 페이지로 이동
    @GetMapping("/authLate")
    public String selectAuthLate() {
        return "authLate/authLateView";
    }

    @GetMapping("/authLate/authLateCreate")
    public String createAuthLatePage() {
        return "authorization/authorizationLate";
    }

    // 모달 페이지 이동
    @GetMapping("/authLate/authLateModal")
    public String showAuthLateModal() {
        return "authLate/authLateModal";
    }
}
