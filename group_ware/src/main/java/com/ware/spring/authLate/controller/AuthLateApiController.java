package com.ware.spring.authLate.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ware.spring.authLate.domain.AuthLate;
import com.ware.spring.authLate.domain.AuthLateDto;
import com.ware.spring.authLate.service.AuthLateService;
import com.ware.spring.authorization.domain.Authorization;
import com.ware.spring.authorization.service.AuthorizationService;
import com.ware.spring.member.domain.Member;
import com.ware.spring.member.repository.MemberRepository;

@Controller
public class AuthLateApiController {

    private final AuthLateService authLateService;
    private final AuthorizationService authorizationService;
    private final MemberRepository memberRepository;

    @Autowired
    public AuthLateApiController(AuthLateService authLateService, AuthorizationService authorizationService, MemberRepository memberRepository) {
        this.authLateService = authLateService;
        this.authorizationService = authorizationService;
        this.memberRepository = memberRepository;
    }

    // AuthLate 생성 요청 처리 및 Authorization과 연계
    @ResponseBody
    @PostMapping("/authLate")
    public Map<String, String> createAuthLate(AuthLateDto authLateDto, 
                                              @RequestParam(value = "file", required = false) MultipartFile file) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            // AuthLate 생성 및 파일 처리
            AuthLate authLate = authLateService.createAuthLate(authLateDto, file);

            // Authorization 생성 및 연계
            Member member = memberRepository.findById(authLateDto.getMemberNo())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
            Authorization authorization = Authorization.builder()
                    .authorName(authLate.getLateTitle())
                    .authorStatus("Pending")  // 초기 상태 설정
                    .authLate(authLate)
                    .member(member)
                    .build();
            authorizationService.createAuthorization(authorization);

            resultMap.put("res_code", "200");
            resultMap.put("res_msg", "지각 사유서가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            resultMap.put("res_code", "500");
            resultMap.put("res_msg", "지각 사유서 등록 중 오류가 발생했습니다.");
        }
        
        int a = 10;
        
        return resultMap;
    }
}
