package com.example.board.service;

import com.example.board.domain.dto.BoardDto.SearchContentBoard;
import com.example.board.domain.form.CommentForm.MergeComment;


public interface CommentService {

    // 저장
    SearchContentBoard addComment(Long userId, Long boardId, MergeComment mergeComment);

    // 수정
    SearchContentBoard modifyComment(
        Long userId, Long boardId, Long commentId, MergeComment mergeComment);

    // 삭제
    SearchContentBoard deleteComment(
        Long userId, Long boardId, Long commentId);
}
