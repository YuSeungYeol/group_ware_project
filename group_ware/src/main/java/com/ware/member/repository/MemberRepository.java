package com.ware.spring.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ware.spring.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

}
