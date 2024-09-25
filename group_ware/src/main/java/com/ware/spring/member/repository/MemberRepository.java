package com.ware.spring.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ware.spring.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

	 Member findByBoards_BoardNo(Long board_no);
	    
	 Member findByNotices_NoticeNo(Long notice_no);
}
