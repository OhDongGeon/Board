package com.example.board.service.impl;

import static com.example.board.exception.ErrorCode.BLANK_LOGIN_ID;
import static com.example.board.exception.ErrorCode.BLANK_PASSWORD;
import static com.example.board.exception.ErrorCode.NOT_FIND_LOGIN_ID;
import static com.example.board.exception.ErrorCode.WRONG_LOGIN_PASSWORD;

import com.example.board.domain.dto.UserDto;
import com.example.board.domain.entity.User;
import com.example.board.domain.repository.UserRepository;
import com.example.board.exception.GlobalException;
import com.example.board.security.TokenProvider;
import com.example.board.service.SignInService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SignInServiceImpl implements SignInService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;


    // 로그인
    public String signIn(UserDto.SignIn signIn) {

        // 입력값 확인
        if (signIn.getLoginId().equals("")) {
            throw new GlobalException(BLANK_LOGIN_ID);
        }
        if (signIn.getUserPassword().equals("")) {
            throw new GlobalException(BLANK_PASSWORD);
        }

        User user = userRepository.findByLoginId(signIn.getLoginId())
            .orElseThrow(() -> new GlobalException(NOT_FIND_LOGIN_ID));

        if (!passwordEncoder.matches(signIn.getUserPassword(), user.getUserPassword())) {
            throw new GlobalException(WRONG_LOGIN_PASSWORD);
        }

        return tokenProvider.createToken(user.getLoginId(), user.getUserRank());
    }
}
