package com.example.board.service;

import com.example.board.domain.dto.BoardDto.SearchContentBoard;
import com.example.board.domain.dto.BoardDto.SearchListBoard;
import com.example.board.domain.form.BoardForm.ContentBoard;
import com.example.board.domain.form.BoardForm.ListBoard;
import com.example.board.domain.form.BoardForm.MergeBoard;
import java.util.List;


public interface BoardService {

    // 게시판 목록 조회
    List<SearchListBoard> searchListBoard(Long userId, ListBoard listBoard);

    // 게시판 내용 조회
    SearchContentBoard searchContentBoard(Long userId, ContentBoard contentBoard);

    // 저장
    SearchContentBoard addBoard(Long userId, MergeBoard mergeBoard);

    // 수정
    SearchContentBoard modifyBoard(Long userId, Long boardId, MergeBoard mergeBoard);

    // 삭제
    List<SearchListBoard> deleteBoard(Long userId, Long boardId);
}
