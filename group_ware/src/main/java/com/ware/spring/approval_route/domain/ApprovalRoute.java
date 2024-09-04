package com.ware.spring.approval_route.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.ware.spring.authorization.domain.Authorization;
import com.ware.spring.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "approval_route")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
@Builder
@IdClass(ApprovalRouteId.class)
public class ApprovalRoute implements Serializable {

    @Id
    @Column(name = "author_no")
    private Long authorNo;

    @Id
    @Column(name = "mem_no")
    private Long memNo;

    @ManyToOne
    @JoinColumn(name = "author_no", insertable = false, updatable = false)
    private Authorization authorization;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mem_no", insertable = false, updatable = false)
    private Member member;

    @Column(name = "approval_status")
    private String approvalStatus;

    @Column(name = "approved_date")
    private LocalDateTime approvedDate;

    // 결재 순서 관리 (int로 변경)
    @Column(name = "approval_order")
    private int approvalOrder;

    // 결재자 여부
    @Column(name = "is_approver", length = 1)
    private String isApprover;

    // 참조자 여부
    @Column(name = "is_referer", length = 1)
    private String isReferer;

}
