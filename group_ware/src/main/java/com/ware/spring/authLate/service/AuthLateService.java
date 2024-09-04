package com.ware.spring.authLate.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ware.spring.authLate.domain.AuthLate;
import com.ware.spring.authLate.domain.AuthLateDto;
import com.ware.spring.authLate.repository.AuthLateRepository;
import com.ware.spring.authorization.domain.Authorization;
import com.ware.spring.authorization.repository.AuthorizationRepository;
import com.ware.spring.member.domain.Member;
import com.ware.spring.member.repository.MemberRepository;

@Service
public class AuthLateService {

    private final AuthLateRepository authLateRepository;
    private final MemberRepository memberRepository;
    private final AuthorizationRepository authorizationRepository;
    
    @Autowired
    public AuthLateService(AuthLateRepository authLateRepository, 
                           MemberRepository memberRepository, AuthorizationRepository authorizationRepository) {
        this.authLateRepository = authLateRepository;
        this.memberRepository = memberRepository;
        this.authorizationRepository = authorizationRepository;
    }

    public List<AuthLateDto> selectAuthLateList() {
        List<AuthLate> authLateList = authLateRepository.findAll();
        List<AuthLateDto> authLateDtoList = new ArrayList<>();
        for (AuthLate a : authLateList) {
            AuthLateDto dto = AuthLateDto.toDto(a);
            authLateDtoList.add(dto);
        }
        return authLateDtoList;
    }
    
    public AuthLate createAuthLate(AuthLateDto dto, MultipartFile file) {
        Long memberNo = dto.getMemberNo();

        Member member = memberRepository.findById(memberNo)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        AuthLate authLate = dto.toEntity(member);  // DTO를 통해 엔티티 생성

        // Authorization 객체를 먼저 생성하고 저장
        Authorization authorization = Authorization.builder()
                .authorName(dto.getLateTitle())
                .authorStatus("Pending")
                .member(member)
                .build();

        // Authorization을 먼저 저장하여 ID를 생성
        Authorization savedAuthorization = authorizationRepository.save(authorization);

        // AuthLate와 Authorization 연관 관계 설정
        authLate.setAuthorization(savedAuthorization);
        savedAuthorization.setAuthLate(authLate);

        // AuthLate를 저장 (이때 Authorization과의 관계가 확립됨)
        authLate = authLateRepository.save(authLate);
        authorizationRepository.save(savedAuthorization);

        return authLate;
    }
}
