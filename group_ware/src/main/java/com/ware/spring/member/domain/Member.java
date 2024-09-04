package com.ware.spring.member.domain;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ware.spring.ranks.domain.Ranks;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name="member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="mem_no")
	private Long memNo;
	
	@Column(name="mem_id")
	private String memId;
	
	@Column(name="mem_pw")
	private String memPw;
	
	@Column(name="mem_name")
	private String memName;
	
	@Column(name="mem_phone")
	private String memPhone;
	
	@Column(name="mem_email")
	private String memEmail;
	
	
	@Column(name="mem_reg_date")
	@CreationTimestamp
	private LocalDateTime memRegDate;
	
	@Column(name="mem_mod_date")
	@UpdateTimestamp
	private LocalDateTime memModDate;
	
	@Column(name="profile_ori_name")
	private String profileOriName;

	@Column(name="mem_off")
	private double memOff;
	
	@Column(name="mem_use_off")
	private double memUseOff;
	
	@ManyToOne
	@JoinColumn(name="ranks_no")
	private Ranks ranks;
}
