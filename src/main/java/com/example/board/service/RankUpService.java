package com.example.board.service;

import com.example.board.domain.dto.UserRankUpDto.SearchUserRankUpList;
import com.example.board.domain.entity.User;
import com.example.board.domain.form.UserRankUpForm;
import java.util.List;


public interface RankUpService {

    // 등급업 진행해야할 사용자 조회 (관리자)
    List<SearchUserRankUpList> searchUserRankUp(Long userId);

    // 등급업 (관리자)
    List<SearchUserRankUpList> modifyUserRankUp(
        Long adminId, Long userId, UserRankUpForm userRankUpForm);

    // 자동 등급 업
    void autoUserRankUp(User user);
}
