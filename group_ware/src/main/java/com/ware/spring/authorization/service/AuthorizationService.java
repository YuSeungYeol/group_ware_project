package com.ware.spring.authorization.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ware.spring.authOff.domain.AuthOff;
import com.ware.spring.authorization.domain.Authorization;
import com.ware.spring.authorization.domain.AuthorizationDto;
import com.ware.spring.authorization.repository.AuthorizationRepository;
import com.ware.spring.member.domain.Member;
import com.ware.spring.member.repository.MemberRepository;

@Service
public class AuthorizationService {

//    private final AuthorizationRepository authorizationRepository;
//    private final MemberRepository memberRepository;
//    
//    @Autowired
//    public AuthorizationService(AuthorizationRepository authorizationRepository, 
//                                MemberRepository memberRepository) {
//        this.authorizationRepository = authorizationRepository;
//        this.memberRepository = memberRepository;
//    }
//
//    public List<AuthorizationDto> selectAuthorizationList() {
//        List<Authorization> authorizationList = authorizationRepository.findAll();
//        List<AuthorizationDto> authorizationDtoList = new ArrayList<>();
//        for (Authorization a : authorizationList) {
//            AuthorizationDto dto = AuthorizationDto.toDto(a);
//            authorizationDtoList.add(dto);
//        }
//        return authorizationDtoList;
//    }
//    
//    // DTO를 통해 Authorization 생성
//    public Authorization createAuthorization(AuthorizationDto dto) {
//        try {
//            Long memberNo = dto.getMemberNo();
//            Member member = memberRepository.findById(memberNo).orElse(null);
//
//            // member가 null인 경우에 대한 처리
//            if (member == null) {
//                // 예외를 발생시키거나, null일 경우 기본 동작 정의 (여기서는 null 반환)
//                return null;  // 또는 기본값 처리
//            }
//
//            // DTO에서 직접 엔티티를 생성하지 않고, Builder를 통해 생성
//            Authorization authorization = Authorization.builder()
//                    .authorName(dto.getAuthorName())
//                    .authorStatus(dto.getAuthorStatus())
//                    .authorOriThumbnail(dto.getAuthorOriThumbnail())
//                    .authorNewThumbnail(dto.getAuthorNewThumbnail())
// //                   .member(member)
//                    .build();
//                    
//            // 생성된 Authorization 엔티티를 데이터베이스에 저장
//            return authorizationRepository.save(authorization);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e; // 예외를 다시 던져 호출자에게 알림
//        }
//    }
//
//
//    
//    // Authorization 엔티티를 직접 받아서 생성(off 연차 생성
//    public Authorization createAuthorization(Authorization authorization) {
//        try {
//            // 연관된 AuthOff 또는 다른 객체가 있는지 확인
//            AuthOff authOff = authorization.getAuthOff();
//
//            // Authorization 객체 저장
//            Authorization savedAuthorization = authorizationRepository.save(authorization);
//            
//            // AuthOff와 Authorization의 관계가 올바르게 설정되었는지 확인
//            if (authOff != null) {
//                authOff.setAuthorization(savedAuthorization);  // AuthOff에 Authorization 설정
//                authorizationRepository.save(savedAuthorization);  // 최종적으로 다시 저장
//            }
//
//            return savedAuthorization;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
//
//}
	private final AuthorizationRepository authorizationRepository;
    private final MemberRepository memberRepository;
    
    @Autowired
    public AuthorizationService(AuthorizationRepository authorizationRepository, 
                                MemberRepository memberRepository) {
        this.authorizationRepository = authorizationRepository;
        this.memberRepository = memberRepository;
    }

    public List<AuthorizationDto> selectAuthorizationList() {
        List<Authorization> authorizationList = authorizationRepository.findAll();
        List<AuthorizationDto> authorizationDtoList = new ArrayList<>();
        for (Authorization a : authorizationList) {
            AuthorizationDto dto = AuthorizationDto.toDto(a);
            authorizationDtoList.add(dto);
        }
        return authorizationDtoList;
    }
    
    // DTO를 통해 Authorization 생성
    public Authorization createAuthorization(AuthorizationDto dto) {
        try {
//            Member member = null;
//            if (dto.getMemberNo() != null) {
//                member = memberRepository.findById(dto.getMemberNo()).orElse(null);
//            }

            // DTO에서 직접 엔티티를 생성하지 않고, Builder를 통해 생성
            Authorization authorization = Authorization.builder()
                    .authorName(dto.getAuthorName())
                    .authorStatus(dto.getAuthorStatus())
                    .authorOriThumbnail(dto.getAuthorOriThumbnail())
                    .authorNewThumbnail(dto.getAuthorNewThumbnail())
//                    .member(member)  // member가 null일 경우에도 처리
                    .build();
                    
            // 생성된 Authorization 엔티티를 데이터베이스에 저장
            return authorizationRepository.save(authorization);

        } catch (Exception e) {
            e.printStackTrace();
            throw e; // 예외를 다시 던져 호출자에게 알림
        }
    }

    // Authorization 엔티티를 직접 받아서 생성
    public Authorization createAuthorization(Authorization authorization) {
        try {
            // 연관된 AuthOff 또는 다른 객체가 있는지 확인
            AuthOff authOff = authorization.getAuthOff();

            // Authorization 객체 저장
            Authorization savedAuthorization = authorizationRepository.save(authorization);
            
            // AuthOff와 Authorization의 관계가 올바르게 설정되었는지 확인
            if (authOff != null) {
                authOff.setAuthorization(savedAuthorization);  // AuthOff에 Authorization 설정
                authorizationRepository.save(savedAuthorization);  // 최종적으로 다시 저장
            }

            return savedAuthorization;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
