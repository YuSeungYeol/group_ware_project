package com.ware.spring.authorization.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ware.spring.authTrip.domain.AuthTrip;
import com.ware.spring.authOff.domain.AuthOff;
import com.ware.spring.authLate.domain.AuthLate;
import com.ware.spring.member.domain.Member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "authorization")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
@Builder
public class Authorization {

    @Id
    @Column(name = "author_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorNo;

    @ManyToOne
    @JoinColumn(name = "mem_no")
    private Member member;  // 전자결재 요청한 직원

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "author_status")
    private String authorStatus;
    
    @Column(name="author_reg_date")
    @CreationTimestamp
    private LocalDateTime authorRegDate;
    
    @Column(name="author_mod_date")
    @UpdateTimestamp
    private LocalDateTime authorModDate;

    @Column(name = "author_ori_thumbnail")
    private String authorOriThumbnail;
    
    @Column(name="author_new_thumbnail")
    private String authorNewThumbnail;

    @OneToOne(mappedBy = "authorization", cascade = CascadeType.ALL)
    private AuthTrip authTrip;  // 해외출장 문서 테이블과의 관계

    @OneToOne(mappedBy = "authorization", cascade = CascadeType.ALL)
    private AuthOff authOff;  // 휴가결재 문서 테이블과의 관계

    @OneToOne(mappedBy = "authorization", cascade = CascadeType.ALL)
    private AuthLate authLate;  // 지각사유서 문서 테이블과의 관계

	

}
