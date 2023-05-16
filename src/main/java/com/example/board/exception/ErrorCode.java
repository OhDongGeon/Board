package com.example.board.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    ALREADY_LOGIN_ID("이미 사용중인 아이디 입니다."),
    ALREADY_NICK_NAME("이미 사용중인 닉네임 입니다."),
    BLANK_LOGIN_ID("아이디를 입력하세요."),
    BLANK_NICK_NAME("닉네임을 입력하세요."),
    BLANK_PASSWORD("비밀번호를 입력하세요."),


    NOT_FIND_LOGIN_ID("존재하지 않는 아이디 입니다."),
    WRONG_LOGIN_PASSWORD("비밀번호가 일치하지 않습니다.");


    private final String MESSAGE;
}
