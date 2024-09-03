package com.ware.spring.member.domain;

import java.time.LocalDateTime;

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
public class MemberDto {

	private Long mem_no;
	private String mem_id;
	private String mem_pw;
	private String mem_name;
	private String mem_phone;
	private String mem_email;
	private LocalDateTime mem_reg_date;
	private LocalDateTime mem_mod_date;
	private String profile_ori_name;
	private double mem_off;
	private double mem_use_off;
	
	private Long ranks_no;
	
	public Member toEntity() {
		return Member.builder()
				.memNo(mem_no)
				.memId(mem_id)
				.memPw(mem_pw)
				.memName(mem_name)
				.memPhone(mem_phone)
				.memEmail(mem_email)
				.memRegDate(mem_reg_date)
				.memModDate(mem_mod_date)
				.profileOriName(profile_ori_name)
				.memOff(mem_off)
				.memUseOff(mem_use_off)
				.build();
	}
	
	public MemberDto toDto(Member member) {
		return MemberDto.builder()
				.mem_no(member.getMemNo())
				.mem_id(member.getMemId())
				.mem_pw(member.getMemPw())
				.mem_name(member.getMemName())
				.mem_phone(member.getMemPhone())
				.mem_email(member.getMemEmail())
				.mem_reg_date(member.getMemRegDate())
				.mem_mod_date(member.getMemModDate())
				.profile_ori_name(member.getProfileOriName())
				.mem_off(member.getMemOff())
				.mem_use_off(member.getMemUseOff())
				.build();
	}
}
