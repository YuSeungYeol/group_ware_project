package com.ware.spring.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ware.spring.board.domain.Board;
import com.ware.spring.board.domain.BoardDto;
import com.ware.spring.board.repository.BoardRepository;
 
@Service
public class BoardService {

	private final BoardRepository boardRepository;
	
	@Autowired
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	
	public List<BoardDto> selectBoardList(){
		List<Board> boardList = boardRepository.findAll();
		List<BoardDto> boardDtoList = new ArrayList<BoardDto>();
		for(Board board : boardList) {
			BoardDto boardDto = new BoardDto().toDto(board);
			boardDtoList.add(boardDto);
		}
		return boardDtoList;
	}	
}
