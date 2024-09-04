package com.ware.spring.authTrip.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ware.spring.authTrip.domain.AuthTrip;
import com.ware.spring.authTrip.domain.AuthTripDto;
import com.ware.spring.authTrip.service.AuthTripService;
import com.ware.spring.authorization.domain.Authorization;
import com.ware.spring.authorization.service.AuthorizationService;
import com.ware.spring.member.domain.Member;
import com.ware.spring.member.repository.MemberRepository;

@Controller
public class AuthTripApiController {

    private final AuthTripService authTripService;
    private final AuthorizationService authorizationService;
    private final MemberRepository memberRepository;

    @Autowired
    public AuthTripApiController(AuthTripService authTripService, AuthorizationService authorizationService, MemberRepository memberRepository) {
        this.authTripService = authTripService;
        this.authorizationService = authorizationService;
        this.memberRepository = memberRepository;
    }

    // AuthTrip 생성 요청 처리 및 Authorization과 연계
    @ResponseBody
    @PostMapping("/authTrip")
    public Map<String, String> createAuthTrip(AuthTripDto authTripDto, 
                                              @RequestParam(value = "file", required = false) MultipartFile file) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            // AuthTrip 생성 및 파일 처리
            AuthTrip authTrip = authTripService.createAuthTrip(authTripDto, file);

            // Authorization 생성 및 연계
            Member member = memberRepository.findById(authTripDto.getMemberNo())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
            Authorization authorization = Authorization.builder()
                    .authorName(authTrip.getTripTitle())
                    .authorStatus("Pending")  // 초기 상태 설정
                    .authTrip(authTrip)
                    .member(member)
                    .build();
            authorizationService.createAuthorization(authorization);

            resultMap.put("res_code", "200");
            resultMap.put("res_msg", "해외 출장 신청서가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            resultMap.put("res_code", "500");
            resultMap.put("res_msg", "해외 출장 신청서 등록 중 오류가 발생했습니다.");
        }
        return resultMap;
    }
}
