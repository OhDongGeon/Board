package com.example.board.controller;

import com.example.board.domain.dto.UserDto;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> userSignUp(@RequestBody UserDto.SignUp signUp) {
        return ResponseEntity.ok(userService.userSignUp(signUp));
    }


    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<String> memberSignIn(@RequestBody UserDto.SignIn signIn) {
        return ResponseEntity.ok(userService.userSignIn(signIn));
    }
}
