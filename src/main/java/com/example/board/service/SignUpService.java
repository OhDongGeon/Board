package com.example.board.service;

import com.example.board.domain.dto.UserDto;
import com.example.board.domain.type.UserType;


public interface SignUpService {

    // 회원가입
    String signUp(UserDto.SignUp signUp, UserType signLocation);
}
