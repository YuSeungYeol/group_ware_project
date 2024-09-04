package com.ware.spring.approval_route.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ware.spring.approval_route.domain.ApprovalRoute;
import com.ware.spring.approval_route.domain.ApprovalRouteId;

public interface ApprovalRouteRepository extends JpaRepository<ApprovalRoute, ApprovalRouteId> {
    List<ApprovalRoute> findByAuthorization_AuthorNo(Long authorNo);
    List<ApprovalRoute> findByMember_MemNo(Long memNo);

    Optional<ApprovalRoute> findByAuthorization_AuthorNoAndMember_MemNo(Long authorNo, Long memNo);
    
	Optional<ApprovalRoute> findByAuthorization_AuthorNoAndApprovalOrder(Long authorNo, int approvalOrder);
}
