package com.ware.spring.approval_route.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ware.spring.approval_route.domain.ApprovalRoute;
import com.ware.spring.approval_route.domain.ApprovalRouteDto;
import com.ware.spring.approval_route.repository.ApprovalRouteRepository;
import com.ware.spring.authorization.domain.Authorization;
import com.ware.spring.authorization.repository.AuthorizationRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ApprovalRouteService {

    private final ApprovalRouteRepository approvalRouteRepository;
    private final AuthorizationRepository authorizationRepository;

    @Autowired
    public ApprovalRouteService(ApprovalRouteRepository approvalRouteRepository, AuthorizationRepository authorizationRepository) {
        this.approvalRouteRepository = approvalRouteRepository;
        this.authorizationRepository = authorizationRepository;
    }

    // 특정 authorNo에 대한 결재자와 참조자 필터링 조회
    public List<ApprovalRouteDto> getApprovalRoutesByAuthorNo(Long authorNo) {
        List<ApprovalRoute> approvalRoutes = approvalRouteRepository.findByAuthorization_AuthorNo(authorNo);

        // 모든 결재자와 참조자를 포함한 리스트로 변환
        List<ApprovalRouteDto> result = approvalRoutes.stream()
            .map(ApprovalRouteDto::toDto)
            .collect(Collectors.toList());

        return result;
    }


    
    // 특정 authorNo와 memberNo에 대한 ApprovalRoute 상태 업데이트
    public void updateApprovalStatus(Long authorNo, Long memberNo, String status) {
        Optional<ApprovalRoute> optionalApprovalRoute = approvalRouteRepository.findByAuthorization_AuthorNoAndMember_MemNo(authorNo, memberNo);
        if (optionalApprovalRoute.isPresent()) {
            ApprovalRoute approvalRoute = optionalApprovalRoute.get();
            approvalRoute.setApprovalStatus(status);
            approvalRouteRepository.save(approvalRoute);
        } else {
            throw new IllegalArgumentException("Approval route not found for the given authorNo and memberNo");
        }
    }

    // ApprovalRoute 생성 및 저장
    public ApprovalRoute createApprovalRoute(Long authorNo, Long memberNo, String approvalStatus, int approvalOrder, boolean isApprover, boolean isReferer) {
        // Authorization 객체를 가져오기
        Authorization authorization = authorizationRepository.findById(authorNo)
                .orElseThrow(() -> new IllegalArgumentException("Invalid authorNo"));

        // ApprovalRoute 객체 생성
        ApprovalRoute approvalRoute = ApprovalRoute.builder()
                .authorization(authorization)
                .authorNo(authorization.getAuthorNo())
                .memNo(memberNo)
                .approvalStatus("대기중")  // 초기 상태는 "대기중"으로 설정
                .approvalOrder(approvalOrder)
                .isApprover(isApprover ? "Y" : "N")  // boolean을 "Y" 또는 "N"으로 변환
                .isReferer(isReferer ? "Y" : "N")    // boolean을 "Y" 또는 "N"으로 변환
                .build();


        // ApprovalRoute 저장
        return approvalRouteRepository.save(approvalRoute);
    }

    // 문서에 대한 결재자와 참조자 경로를 생성
    public void createApprovalRoutesForDocument(Long authorNo) {
        // 첫 번째 결재자
        createApprovalRoute(authorNo, 1L, "PENDING", 1, true, false);

        // 두 번째 결재자
        createApprovalRoute(authorNo, 2L, "PENDING", 2, true, false);

        // 참조자
        createApprovalRoute(authorNo, 3L, "PENDING", 3, false, true);
    }

    // 다음 결재자 찾기 및 결재 순서 진행
    public void moveToNextApprover(Long authorNo, int currentOrder) {
        Optional<ApprovalRoute> currentApprover = getCurrentApprover(authorNo, currentOrder);
        if (currentApprover.isPresent()) {
            ApprovalRoute approvalRoute = currentApprover.get();
            approvalRoute.setApprovalStatus("APPROVED");
            approvalRouteRepository.save(approvalRoute);

            // 다음 결재자를 찾는다
            int nextOrder = currentOrder + 1;
            Optional<ApprovalRoute> nextApprover = getCurrentApprover(authorNo, nextOrder);
            if (nextApprover.isPresent()) {
                ApprovalRoute nextApprovalRoute = nextApprover.get();
                nextApprovalRoute.setApprovalStatus("PENDING");
                approvalRouteRepository.save(nextApprovalRoute);
            } else {
                // 더 이상 결재자가 없으면 결재 완료 처리
                completeApprovalProcess(authorNo);
            }
        }
    }

    // 현재 결재 순서에 해당하는 결재자를 조회
    public Optional<ApprovalRoute> getCurrentApprover(Long authorNo, int approvalOrder) {
        return approvalRouteRepository.findByAuthorization_AuthorNoAndApprovalOrder(authorNo, approvalOrder);
    }

    // 결재 프로세스 완료 처리
    private void completeApprovalProcess(Long authorNo) {
        // 모든 결재 완료 로직 처리
        System.out.println("Approval process completed for authorNo: " + authorNo);
    }
}
