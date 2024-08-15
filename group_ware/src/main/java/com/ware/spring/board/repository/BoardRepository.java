package com.ware.spring.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ware.spring.board.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
