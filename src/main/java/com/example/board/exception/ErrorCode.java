package com.example.board.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    ALREADY_LOGIN_ID("이미 사용중인 아이디 입니다."),
    NOT_FIND_LOGIN_ID("존재하지 않는 아이디 입니다."),
    WRONG_LOGIN_PASSWORD("비밀번호가 일치하지 않습니다.");


    private final String MESSAGE;
}
