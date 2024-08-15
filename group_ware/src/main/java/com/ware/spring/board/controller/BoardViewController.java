package com.ware.spring.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ware.spring.board.domain.BoardDto;
import com.ware.spring.board.service.BoardService;
	
@Controller
public class BoardViewController {
	
	private final BoardService boardService;
	
	@Autowired
	public BoardViewController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@GetMapping("/board")
	public String selectBoardList(Model model) {
		List<BoardDto> resultList = boardService.selectBoardList();
		model.addAttribute("resultList",resultList);
		return "/board/list";
	}
}
