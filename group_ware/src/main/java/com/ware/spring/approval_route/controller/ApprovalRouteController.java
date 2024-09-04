package com.ware.spring.approval_route.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ware.spring.approval_route.domain.ApprovalRoute;
import com.ware.spring.approval_route.domain.ApprovalRouteDto;
import com.ware.spring.approval_route.service.ApprovalRouteService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/approval")
public class ApprovalRouteController {

    private final ApprovalRouteService approvalRouteService;

    @Autowired
    public ApprovalRouteController(ApprovalRouteService approvalRouteService) {
        this.approvalRouteService = approvalRouteService;
        
    }

    @GetMapping("/{authorNo}")
    public ResponseEntity<List<ApprovalRouteDto>> getApprovalRoutes(@PathVariable("authorNo") Long authorNo) {
        List<ApprovalRouteDto> approvalRouteDtos = approvalRouteService.getApprovalRoutesByAuthorNo(authorNo);
        return ResponseEntity.ok(approvalRouteDtos);
    }



    @PostMapping("/update")
    public ResponseEntity<Void> updateApprovalStatus(@RequestParam("authorNo") Long authorNo, @RequestParam("memberNo") Long memberNo, @RequestParam("status") String status) {
        approvalRouteService.updateApprovalStatus(authorNo, memberNo, status);
        return ResponseEntity.ok().build();
    }

}