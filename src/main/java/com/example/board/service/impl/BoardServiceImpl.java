package com.example.board.service.impl;

import static com.example.board.exception.ErrorCode.CLOSED_BOARD;
import static com.example.board.exception.ErrorCode.NOT_FIND_BOARD;
import static com.example.board.exception.ErrorCode.NOT_FIND_CATEGORY;
import static com.example.board.exception.ErrorCode.NOT_FIND_LOGIN_ID;
import static com.example.board.exception.ErrorCode.NOT_MATCH_LOGIN_ID;
import static com.example.board.exception.ErrorCode.NOT_MATCH_USER_RANK;

import com.example.board.domain.dto.BoardDto;
import com.example.board.domain.dto.BoardDto.SearchContentBoard;
import com.example.board.domain.dto.BoardDto.SearchListBoard;
import com.example.board.domain.entity.Board;
import com.example.board.domain.entity.Category;
import com.example.board.domain.entity.User;
import com.example.board.domain.form.BoardForm.ListBoard;
import com.example.board.domain.form.BoardForm.MergeBoard;
import com.example.board.domain.repository.BoardRepository;
import com.example.board.domain.repository.CategoryRepository;
import com.example.board.domain.repository.UserRepository;
import com.example.board.domain.type.RankType;
import com.example.board.exception.GlobalException;
import com.example.board.service.BoardService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final BoardRepository boardRepository;


    // 게시판 목록 조회
    public List<SearchListBoard> searchListBoard(
        Long userId, int page, int size, ListBoard listBoard) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("boardId").descending());

        List<SearchListBoard> searchListBoards = new ArrayList<>();

        List<Object[]> boardList = boardRepository.findBoardList(
            userId, listBoard.getBoardTitle(), listBoard.getNickName(), pageable);

        for (Object[] board : boardList) {
            SearchListBoard searchListBoard = SearchListBoard.builder()
                .boardId((Long) board[0])
                .boardTitle((String) board[1])
                .userId((Long) board[2])
                .userNickName((String) board[3])
                .createDate((LocalDateTime) board[4])
                .build();

            searchListBoards.add(searchListBoard);
        }

        return searchListBoards;
    }


    // 게시판 내용 조회
    public SearchContentBoard searchContentBoard(Long userId, Long categoryId, Long boardId) {

        Category category = categoryRepository.findByCategoryId(categoryId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_CATEGORY));

        User user = checkUser(userId, category.getCategoryRank());

        Board board = boardRepository.findByBoardId(boardId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_BOARD));

        if (!board.isBoardPublicFlag() && !userId.equals(board.getUser().getUserId())) {
            throw new GlobalException(CLOSED_BOARD);
        }

        return BoardDto.SearchContentBoard.builder()
            .userId(user.getUserId())
            .userNickName(user.getUserNickName())
            .boardId(board.getBoardId())
            .boardTitle(board.getBoardTitle())
            .boardContent(board.getBoardContent())
            .createDate(board.getCreateDate())
            .build();
    }


    // 저장
    @Transactional
    public SearchContentBoard addBoard(Long userId, MergeBoard mergeBoard) {

        Category category = checkCategory(mergeBoard.getCategoryId());

        User user = checkUser(userId, category.getCategoryRank());

        Board board = boardRepository.save(Board.builder()
            .boardTitle(mergeBoard.getBoardTitle())
            .boardContent(mergeBoard.getBoardContent())
            .boardPublicFlag(mergeBoard.isBoardPublicFlag())
            .build());

        user.getBoards().add(board);
        category.getBoards().add(board);

        return searchContentBoard(userId, category.getCategoryId(), board.getBoardId());
    }


    // 수정
    @Transactional
    public SearchContentBoard modifyBoard(
        Long userId, Long boardId, MergeBoard mergeBoard) {

        Category category = checkCategory(mergeBoard.getCategoryId());

        User user = checkUser(userId, category.getCategoryRank());

        Board board = boardRepository.findByBoardId(boardId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_BOARD));

        if (!userId.equals(board.getUser().getUserId())) {
            throw new GlobalException(NOT_MATCH_LOGIN_ID);
        }

        board.setCategory(category);
        board.setBoardTitle(mergeBoard.getBoardTitle());
        board.setBoardContent(mergeBoard.getBoardContent());
        board.setBoardPublicFlag(mergeBoard.isBoardPublicFlag());

        user.getBoards().add(board);
        category.getBoards().add(board);

        return searchContentBoard(userId, category.getCategoryId(), board.getBoardId());
    }


    // 삭제
    @Transactional
    public List<SearchListBoard> deleteBoard(Long userId, Long boardId) {

        Board board = boardRepository.findByBoardId(boardId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_BOARD));

        if (!userId.equals(board.getUser().getUserId())) {
            throw new GlobalException(NOT_MATCH_LOGIN_ID);
        }

        boardRepository.delete(board);

        ListBoard listBoard = ListBoard.builder()
            .boardTitle("")
            .nickName("")
            .build();

        return searchListBoard(userId, 0, 20, listBoard);
    }


    // 게시판 분류 확인
    public Category checkCategory(Long categoryId) {
        Category category = categoryRepository.findByCategoryId(categoryId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_CATEGORY));

        if (!category.isCategoryUesFlag()) {
            throw new GlobalException(NOT_FIND_CATEGORY);
        }

        return category;
    }


    // 사용자 확인
    public User checkUser(Long userId, RankType categoryRankType) {

        User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_LOGIN_ID));

        if (user.getUserRank().compareTo(categoryRankType) < 0) {
            throw new GlobalException(NOT_MATCH_USER_RANK);
        }

        return user;
    }
}
