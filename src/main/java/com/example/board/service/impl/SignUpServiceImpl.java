package com.example.board.service.impl;

import static com.example.board.exception.ErrorCode.ALREADY_LOGIN_ID;
import static com.example.board.exception.ErrorCode.ALREADY_NICK_NAME;

import com.example.board.domain.entity.User;
import com.example.board.domain.form.UserForm.SignUp;
import com.example.board.domain.repository.UserRepository;
import com.example.board.domain.type.RankType;
import com.example.board.domain.type.UserType;
import com.example.board.exception.GlobalException;
import com.example.board.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // 회원가입
    @Transactional
    public String signUp(SignUp signUp, UserType userType) {

        // 아이디 확인
        if (userRepository.existsByLoginId(signUp.getLoginId())) {
            throw new GlobalException(ALREADY_LOGIN_ID);
        }

        // 닉네임 확인
        if (userRepository.existsByUserNickName(signUp.getUserNickName())) {
            throw new GlobalException(ALREADY_NICK_NAME);
        }

        userRepository.save(
            User.builder()
                .loginId(signUp.getLoginId())
                .userNickName(signUp.getUserNickName())
                .userPassword(passwordEncoder.encode(signUp.getUserPassword()))
                .userRank(userType.equals(UserType.USER) ? RankType.LEVEL1 : RankType.ADMIN)
                .build());

        return "회원가입이 완료 되었습니다.";
    }
}
