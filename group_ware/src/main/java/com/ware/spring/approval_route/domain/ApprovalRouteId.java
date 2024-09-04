package com.ware.spring.approval_route.domain;

import java.io.Serializable;
import java.util.Objects;

public class ApprovalRouteId implements Serializable {

    private Long authorNo;
    private Long memNo;

    public ApprovalRouteId() {}

    public ApprovalRouteId(Long authorNo, Long memNo) {
        this.authorNo = authorNo;
        this.memNo = memNo;
    }

    public Long getAuthorNo() {
        return authorNo;
    }

    public void setAuthorNo(Long authorNo) {
        this.authorNo = authorNo;
    }

    public Long getMemNo() {
        return memNo;
    }

    public void setMemNo(Long memNo) {
        this.memNo = memNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApprovalRouteId that = (ApprovalRouteId) o;
        return Objects.equals(authorNo, that.authorNo) &&
               Objects.equals(memNo, that.memNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorNo, memNo);
    }
}
