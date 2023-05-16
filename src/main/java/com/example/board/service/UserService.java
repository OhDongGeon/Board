package com.example.board.service;

import com.example.board.domain.dto.UserDto;


public interface UserService {

    // 회원가입
    String userSignUp(UserDto.SignUp signUp);

    // 로그인
    String userSignIn(UserDto.SignIn signIn);
}
