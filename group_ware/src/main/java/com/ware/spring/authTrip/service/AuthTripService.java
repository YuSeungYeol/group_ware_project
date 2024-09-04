package com.ware.spring.authTrip.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ware.spring.authTrip.domain.AuthTrip;
import com.ware.spring.authTrip.domain.AuthTripDto;
import com.ware.spring.authTrip.repository.AuthTripRepository;
import com.ware.spring.authorization.domain.Authorization;
import com.ware.spring.authorization.repository.AuthorizationRepository;
import com.ware.spring.member.domain.Member;
import com.ware.spring.member.repository.MemberRepository;

@Service
public class AuthTripService {

    private final AuthTripRepository authTripRepository;
    private final MemberRepository memberRepository;
    private final AuthorizationRepository authorizationRepository;
    
    @Autowired
    public AuthTripService(AuthTripRepository authTripRepository, 
                           MemberRepository memberRepository, AuthorizationRepository authorizationRepository) {
        this.authTripRepository = authTripRepository;
        this.memberRepository = memberRepository;
        this.authorizationRepository = authorizationRepository;
    }

    public List<AuthTripDto> selectAuthTripList() {
        List<AuthTrip> authTripList = authTripRepository.findAll();
        List<AuthTripDto> authTripDtoList = new ArrayList<>();
        for (AuthTrip a : authTripList) {
            AuthTripDto dto = AuthTripDto.toDto(a);
            authTripDtoList.add(dto);
        }
        return authTripDtoList;
    }
    
    public AuthTrip createAuthTrip(AuthTripDto dto, MultipartFile file) {
        Long memberNo = dto.getMemberNo();

        Member member = memberRepository.findById(memberNo)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        AuthTrip authTrip = dto.toEntity(member);  // DTO를 통해 엔티티 생성

        // Authorization 객체를 먼저 생성하고 저장
        Authorization authorization = Authorization.builder()
                .authorName(dto.getTripTitle())
                .authorStatus("Pending")
                .member(member)
                .build();

        // Authorization을 먼저 저장하여 ID를 생성
        Authorization savedAuthorization = authorizationRepository.save(authorization);

        // AuthTrip과 Authorization 연관 관계 설정
        authTrip.setAuthorization(savedAuthorization);
        savedAuthorization.setAuthTrip(authTrip);

        // AuthTrip를 저장 (이때 Authorization과의 관계가 확립됨)
        authTrip = authTripRepository.save(authTrip);
        authorizationRepository.save(savedAuthorization);

        return authTrip;
    }
}
