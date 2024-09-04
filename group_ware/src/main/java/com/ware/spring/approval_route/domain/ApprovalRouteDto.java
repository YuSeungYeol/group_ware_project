package com.ware.spring.approval_route.domain;

import java.time.LocalDateTime;

import com.ware.spring.authorization.domain.Authorization;
import com.ware.spring.member.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ApprovalRouteDto {

    private Long authorNo;
    private Long memberNo;
    private String memberName;
    private String approvalStatus;
    private LocalDateTime approvedDate;
    private int approvalOrder;
    private String isApprover;  // "Y" or "N"
    private String isReferer;   // "Y" or "N"

    public static ApprovalRouteDto toDto(ApprovalRoute approvalRoute) {
        return ApprovalRouteDto.builder()
            .authorNo(approvalRoute.getAuthorization().getAuthorNo())
            .memberNo(approvalRoute.getMember().getMemNo())
            .memberName(approvalRoute.getMember().getMemName())
            .approvalStatus(approvalRoute.getApprovalStatus())
            .approvedDate(approvalRoute.getApprovedDate())
            .approvalOrder(approvalRoute.getApprovalOrder())
            .isApprover(approvalRoute.getIsApprover())  // "Y" or "N"
            .isReferer(approvalRoute.getIsReferer())    // "Y" or "N"
            .build();
    }

    public ApprovalRoute toEntity(Member member, Authorization authorization) {
        return ApprovalRoute.builder()
            .authorization(authorization)
            .member(member)
            .approvalStatus(approvalStatus)
            .approvedDate(approvedDate)
            .approvalOrder(approvalOrder)
            .isApprover(isApprover)  // "Y" or "N"
            .isReferer(isReferer)    // "Y" or "N"
            .build();
    }
}
