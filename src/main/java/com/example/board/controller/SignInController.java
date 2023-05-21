package com.example.board.controller;

import com.example.board.domain.dto.UserDto;
import com.example.board.service.SignInService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class SignInController {

    private final SignInService signInService;


    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<String> memberSignIn(@RequestBody @Valid UserDto.SignIn signIn) {
        return ResponseEntity.ok(signInService.signIn(signIn));
    }
}
