package com.example.board.service.impl;

import static com.example.board.exception.ErrorCode.CLOSED_BOARD;
import static com.example.board.exception.ErrorCode.NOT_FIND_BOARD;
import static com.example.board.exception.ErrorCode.NOT_FIND_COMMENT;
import static com.example.board.exception.ErrorCode.NOT_FIND_LOGIN_ID;
import static com.example.board.exception.ErrorCode.NOT_MATCH_LOGIN_ID;

import com.example.board.domain.dto.BoardDto;
import com.example.board.domain.dto.BoardDto.SearchContentBoard;
import com.example.board.domain.dto.CommentDto;
import com.example.board.domain.entity.Board;
import com.example.board.domain.entity.Comment;
import com.example.board.domain.entity.User;
import com.example.board.domain.form.BoardForm.ContentBoard;
import com.example.board.domain.form.CommentForm.MergeComment;
import com.example.board.domain.form.CommentForm.SearchComment;
import com.example.board.domain.repository.BoardRepository;
import com.example.board.domain.repository.CommentRepository;
import com.example.board.domain.repository.UserRepository;
import com.example.board.exception.GlobalException;
import com.example.board.service.BoardService;
import com.example.board.service.CommentService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final BoardService boardService;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;


    // 게시판 내용, 댓글 저장된 페이지 조회
    public SearchContentBoard searchComment(Long userId, Long boardId,
        SearchComment searchComment) {

        User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_LOGIN_ID));

        Board board = boardRepository.findByBoardId(boardId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_BOARD));

        if (!board.isBoardPublicFlag() && !userId.equals(board.getUser().getUserId())) {
            throw new GlobalException(CLOSED_BOARD);
        }

        // 댓글 기준 카운트
        int commentCount = commentRepository.countByBoard_BoardIdAndCommentIdLessThanEqual(
            boardId, searchComment.getCommentId());

        int size = 50;
        int page = commentCount % size == 0 ? commentCount / size - 1 : commentCount / size;

        Pageable pageable = PageRequest.of(page, size);

        List<CommentDto> commentDto = new ArrayList<>();

        for (Comment comment : commentRepository.findByBoard_BoardId(boardId, pageable)) {
            CommentDto commentContent = CommentDto.builder()
                .commentId(comment.getCommentId())
                .commentContent(comment.getCommentContent())
                .userId(user.getUserId())
                .userNickName(user.getUserNickName())
                .createDate(comment.getCreateDate())
                .build();

            commentDto.add(commentContent);
        }

        return BoardDto.SearchContentBoard.builder()
            .userId(board.getUser().getUserId())
            .userNickName(board.getUser().getUserNickName())
            .categoryId(board.getCategory().getCategoryId())
            .categoryTitle(board.getCategory().getCategoryTitle())
            .boardId(board.getBoardId())
            .boardTitle(board.getBoardTitle())
            .boardContent(board.getBoardContent())
            .createDate(board.getCreateDate())
            .commentDto(commentDto)
            .build();
    }


    // 저장
    @Transactional
    public SearchContentBoard addComment(Long userId, Long boardId, MergeComment mergeComment) {

        User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_LOGIN_ID));

        Board board = boardRepository.findByBoardId(boardId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_BOARD));

        Comment comment = commentRepository.save(Comment.builder()
            .commentContent(mergeComment.getCommentContent())
            .build());

        user.getComments().add(comment);
        board.getComments().add(comment);

        SearchComment searchComment = SearchComment.builder()
            .commentId(comment.getCommentId()).build();

        return searchComment(userId, boardId, searchComment);
    }


    // 수정
    @Transactional
    public SearchContentBoard modifyComment(
        Long userId, Long boardId, Long commentId, MergeComment mergeComment) {

        User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_LOGIN_ID));

        Board board = boardRepository.findByBoardId(boardId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_BOARD));

        Comment comment = commentRepository.findByCommentId(commentId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_COMMENT));

        if (!userId.equals(comment.getUser().getUserId())) {
            throw new GlobalException(NOT_MATCH_LOGIN_ID);
        }

        comment.setCommentContent(mergeComment.getCommentContent());

        user.getComments().add(comment);
        board.getComments().add(comment);

        SearchComment searchComment = SearchComment.builder()
            .commentId(commentId).build();

        return searchComment(userId, boardId, searchComment);
    }


    // 삭제
    @Transactional
    public SearchContentBoard deleteComment(Long userId, Long boardId, Long commentId) {

        userRepository.findByUserId(userId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_LOGIN_ID));

        Board board = boardRepository.findByBoardId(boardId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_BOARD));

        Comment comment = commentRepository.findByCommentId(commentId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_COMMENT));

        if (!userId.equals(comment.getUser().getUserId())) {
            throw new GlobalException(NOT_MATCH_LOGIN_ID);
        }

        commentRepository.delete(comment);

        ContentBoard contentBoard = ContentBoard.builder()
            .categoryId(board.getCategory().getCategoryId())
            .boardId(boardId)
            .build();

        return boardService.searchContentBoard(userId, contentBoard);
    }
}
