package com.ware.spring.authorization.domain;

import java.time.LocalDateTime;

import com.ware.spring.authLate.domain.AuthLateDto;
import com.ware.spring.authOff.domain.AuthOffDto;
import com.ware.spring.authTrip.domain.AuthTripDto;
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
public class AuthorizationDto {

    private Long authorNo;
    private Long memberNo;
    private String memberName;
    private String authorName;
    private String authorStatus;
    private LocalDateTime authorRegDate;
    private LocalDateTime authorModDate;
    private String authorOriThumbnail;
    private String authorNewThumbnail;

    // 새로운 문서 관련 필드들 추가
    private AuthTripDto authTripDto;
    private AuthOffDto authOffDto;
    private AuthLateDto authLateDto;

    public Authorization toEntity(Member member) {
        return Authorization.builder()
                .authorNo(null)
                .authorName(authorName)
                .authorStatus(authorStatus)
                .authorRegDate(authorRegDate)
                .authorModDate(authorModDate)
                .authorOriThumbnail(authorOriThumbnail)
                .authorNewThumbnail(authorNewThumbnail)
                .member(member)
                // 새로운 문서 테이블과의 관계를 추가
                .authTrip(authTripDto != null ? authTripDto.toEntity(member) : null)
                .authOff(authOffDto != null ? authOffDto.toEntity(member) : null)
                .authLate(authLateDto != null ? authLateDto.toEntity(member) : null)
                .build();
    }

    public static AuthorizationDto toDto(Authorization authorization) {
        return AuthorizationDto.builder()
                .authorNo(authorization.getAuthorNo())
//                .memberNo(authorization.getMember().getMemNo())
//                .memberName(authorization.getMember().getMemName())
                .authorName(authorization.getAuthorName())
                .authorStatus(authorization.getAuthorStatus())
                .authorRegDate(authorization.getAuthorRegDate())
                .authorModDate(authorization.getAuthorModDate())
                .authorOriThumbnail(authorization.getAuthorOriThumbnail())
                .authorNewThumbnail(authorization.getAuthorNewThumbnail())
                // 새로운 문서 테이블과의 관계를 DTO로 변환
                .authTripDto(authorization.getAuthTrip() != null ? AuthTripDto.toDto(authorization.getAuthTrip()) : null)
                .authOffDto(authorization.getAuthOff() != null ? AuthOffDto.toDto(authorization.getAuthOff()) : null)
                .authLateDto(authorization.getAuthLate() != null ? AuthLateDto.toDto(authorization.getAuthLate()) : null)
                .build();
    }
}
