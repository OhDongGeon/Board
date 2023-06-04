package com.example.board.controller;

import com.example.board.domain.dto.BoardDto.SearchContentBoard;
import com.example.board.domain.form.CommentForm.MergeComment;
import com.example.board.domain.form.CommentForm.SearchComment;
import com.example.board.security.TokenProvider;
import com.example.board.service.CommentService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final TokenProvider tokenProvider;
    public final String TOKEN_HEADER = "USER_TOKEN_HEADER";


    // 저장
    @PostMapping("/board/content/{boardId}")
    public ResponseEntity<SearchContentBoard> addComment(
        @RequestHeader(name = TOKEN_HEADER) String token,
        @PathVariable("boardId") Long boardId,
        @RequestBody @Valid MergeComment mergeComment) {

        return ResponseEntity.ok(commentService.addComment(
            tokenProvider.getTokenUserId(token), boardId, mergeComment));
    }


    // 수정
    @PutMapping("/board/content/{boardId}/comment/{commentId}")
    public ResponseEntity<SearchContentBoard> modifyComment(
        @RequestHeader(name = TOKEN_HEADER) String token,
        @PathVariable("boardId") Long boardId,
        @PathVariable("commentId") Long commentId,
        @RequestBody @Valid MergeComment mergeComment) {

        return ResponseEntity.ok(commentService.modifyComment(
            tokenProvider.getTokenUserId(token), boardId, commentId, mergeComment));
    }


    // 삭제
    @DeleteMapping("/board/content/{boardId}/comment/{commentId}")
    public ResponseEntity<SearchContentBoard> deleteComment(
        @RequestHeader(name = TOKEN_HEADER) String token,
        @PathVariable("boardId") Long boardId,
        @PathVariable("commentId") Long commentId) {

        return ResponseEntity.ok(commentService.deleteComment(
            tokenProvider.getTokenUserId(token), boardId, commentId));
    }
}
