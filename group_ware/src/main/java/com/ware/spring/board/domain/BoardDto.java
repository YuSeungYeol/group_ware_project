package com.ware.spring.board.domain;

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
public class BoardDto {

	private Long board_no;
	private String board_title;
	private String board_content;
	private Long board_writer_no;
	private String board_writer_name;
	
	private LocalDateTime reg_date;
	private LocalDateTime mod_date;
	private String ori_thumbnail;
	private String new_thumbnail;
	private int search_type = 1;
	private String search_text;
	
	public Board toEntity() {
		return Board.builder()
				.boardNo(board_no)
				.boardTitle(board_title)
				.boardContent(board_content)
				.oriThumbnail(ori_thumbnail)
				.newThumbnail(new_thumbnail)
				.regDate(reg_date)
				.build();
	}
	
	public BoardDto toDto(Board board) {
		return BoardDto.builder()
				.board_no(board.getBoardNo())
				.board_title(board.getBoardTitle())
				.board_content(board.getBoardContent())
				.ori_thumbnail(board.getOriThumbnail())
				.new_thumbnail(board.getNewThumbnail())
				.reg_date(board.getRegDate())
				.build();
	}
}
