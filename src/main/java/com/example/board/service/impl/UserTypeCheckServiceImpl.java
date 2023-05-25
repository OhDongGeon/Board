package com.example.board.service.impl;

import static com.example.board.domain.type.RankType.ADMIN;
import static com.example.board.exception.ErrorCode.NOT_FIND_LOGIN_ID;
import static com.example.board.exception.ErrorCode.NOT_MATCH_USER_RANK;

import com.example.board.domain.entity.User;
import com.example.board.domain.repository.UserRepository;
import com.example.board.exception.GlobalException;
import com.example.board.service.UserTypeCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserTypeCheckServiceImpl implements UserTypeCheckService {

    private final UserRepository userRepository;


    public User userTypeAdmin(Long userId) {
        User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_LOGIN_ID));

        if (!user.getUserRank().equals(ADMIN)) {
            throw new GlobalException(NOT_MATCH_USER_RANK);
        }

        return user;
    }
}
