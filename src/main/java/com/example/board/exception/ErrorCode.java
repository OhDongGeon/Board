package com.example.board.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 회원 관련
    ALREADY_LOGIN_ID("이미 사용중인 아이디 입니다."),
    ALREADY_NICK_NAME("이미 사용중인 닉네임 입니다."),
    NOT_FIND_LOGIN_ID("존재하지 않는 아이디 입니다."),
    WRONG_LOGIN_PASSWORD("비밀번호가 일치하지 않습니다."),


    // 등급 관련
    ALREADY_RANK("이미 등록된 등급 입니다."),
    UNREGISTERED_RANK("등록되지 않은 등급 입니다."),
    NOT_ADD_RANK("등록할 수 없는 등급 입니다."),
    NOT_FIND_RANK("존재하지 않는 등급 입니다."),
    NOT_MATCH_USER_RANK("회원 등급이 맞지 않습니다."),
    UNDER_RANK_CHECK_BOARD_COMMENT("이전 등급의 게시글 수, 댓글 수 보다 크게 입력하세요."),
    OVER_RANK_CHECK_BOARD_COMMENT("다음 등급의 게시글 수, 댓글 수 보다 적게 입력하세요."),


    // 게시판 분류 관련
    REGISTERED_RANK_CATEGORY("해당 등급에 등록된 게시판 분류가 있습니다."),
    NOT_FIND_CATEGORY("존재하지 않는 게시판 분류 입니다."),

    // 게시글
    NOT_FIND_BOARD("존재하지 않는 게시글 입니다."),
    NOT_MATCH_LOGIN_ID("게시글의 작성자가 아닙니다."),
    CLOSED_BOARD("비공개 게시글 입니다.");


    private final String MESSAGE;
}
