package com.example.board.service;

import com.example.board.domain.form.UserForm.SignIn;

public interface SignInService {

    // 로그인
    String signIn(SignIn signIn);
}
