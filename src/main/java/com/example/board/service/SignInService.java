package com.example.board.service;

import com.example.board.domain.dto.UserDto;

public interface SignInService {

    // 로그인
    String signIn(UserDto.SignIn signIn);
}
