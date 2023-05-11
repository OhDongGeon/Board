package com.example.board.service;

import static com.example.board.exception.ErrorCode.ALREADY_LOGIN_ID;
import static com.example.board.exception.ErrorCode.NOT_FIND_LOGIN_ID;
import static com.example.board.exception.ErrorCode.WRONG_LOGIN_PASSWORD;

import com.example.board.domain.dto.UserDto;
import com.example.board.domain.entity.User;
import com.example.board.domain.repository.UserRepository;
import com.example.board.exception.GlobalException;
import com.example.board.security.TokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;


    // 회원가입
    @Transactional
    public String userSignUp(UserDto.SignUp signUp) {
        if (userRepository.existsByLoginId(signUp.getLoginId())) {
            throw new GlobalException(ALREADY_LOGIN_ID);
        }
        signUp.setUserPassword(passwordEncoder.encode(signUp.getUserPassword()));
        userRepository.save(signUp.save(signUp));

        return "회원가입이 완료 되었습니다.";
    }


    // 로그인
    public String userSignIn(UserDto.SignIn signIn) {
        User user = userRepository.findByLoginId(signIn.getLoginId())
            .orElseThrow(() -> new GlobalException(NOT_FIND_LOGIN_ID));

        if (!passwordEncoder.matches(signIn.getUserPassword(), user.getUserPassword())) {
            throw new GlobalException(WRONG_LOGIN_PASSWORD);
        }

        return tokenProvider.createToken(user.getLoginId(), user.getUserRank());
    }
}
