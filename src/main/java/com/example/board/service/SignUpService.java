package com.example.board.service;

import com.example.board.domain.form.UserForm.SignUp;
import com.example.board.domain.type.UserType;


public interface SignUpService {

    // 회원가입
    String signUp(SignUp signUp, UserType signLocation);
}
