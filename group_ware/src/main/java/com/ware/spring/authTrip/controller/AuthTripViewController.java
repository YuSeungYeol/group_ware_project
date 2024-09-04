package com.ware.spring.authTrip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ware.spring.authTrip.domain.AuthTripDto;
import com.ware.spring.authTrip.service.AuthTripService;

@Controller
public class AuthTripViewController {

    private final AuthTripService authTripService;

    @Autowired
    public AuthTripViewController(AuthTripService authTripService) {
        this.authTripService = authTripService;
    }

    // 목록 페이지로 이동
    @GetMapping("/authTripList")
    public String selectAuthTripList(Model model) {
        List<AuthTripDto> resultList = authTripService.selectAuthTripList();
        model.addAttribute("resultList", resultList);
        return "authorization/authorizationTripList";
    }

    // 상세 페이지로 이동
    @GetMapping("/authTrip")
    public String selectAuthTrip() {
        return "authTrip/authTripView";
    }

    @GetMapping("/authTrip/authTripCreate")
    public String createAuthTripPage() {
        return "authorization/authorizationTrip";
    }

    // 모달 페이지 이동
    @GetMapping("/authTrip/authTripModal")
    public String showAuthTripModal() {
        return "authTrip/authTripModal";
    }
}
