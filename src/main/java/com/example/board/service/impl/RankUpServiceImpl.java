package com.example.board.service.impl;

import static com.example.board.exception.ErrorCode.NOT_FIND_LOGIN_ID;

import com.example.board.domain.dto.UserRankUpDto.SearchUserRankUp;
import com.example.board.domain.dto.UserRankUpDto.SearchUserRankUpList;
import com.example.board.domain.entity.RankUpStandard;
import com.example.board.domain.entity.User;
import com.example.board.domain.form.UserRankUpForm;
import com.example.board.domain.repository.BoardRepository;
import com.example.board.domain.repository.CommentRepository;
import com.example.board.domain.repository.RankUpStandardRepository;
import com.example.board.domain.repository.UserRepository;
import com.example.board.exception.GlobalException;
import com.example.board.service.RankUpService;
import com.example.board.service.UserTypeCheckService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class RankUpServiceImpl implements RankUpService {

    private final UserTypeCheckService userTypeCheckService;
    private final UserRepository userRepository;
    private final RankUpStandardRepository rankUpStandardRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;


    // 등급업 진행해야할 사용자 조회 (관리자)
    public List<SearchUserRankUpList> searchUserRankUp(Long userId) {

        userTypeCheckService.userTypeAdmin(userId);

        List<SearchUserRankUpList> userRankUpList = new ArrayList<>();

        for (SearchUserRankUp searchUserRankUp : userRepository.findNextRankUpUser()) {
            SearchUserRankUpList searchUserRankUpList = SearchUserRankUpList.builder()
                .userId(searchUserRankUp.getUserId())
                .userNickName(searchUserRankUp.getUserNickName())
                .rankName(searchUserRankUp.getRankName())
                .boardCount(searchUserRankUp.getBoardCount())
                .commentCount(searchUserRankUp.getCommentCount())
                .nextRank(searchUserRankUp.getNextRank())
                .build();

            userRankUpList.add(searchUserRankUpList);
        }

        return userRankUpList;
    }

    // 등급업 (관리자)
    @Transactional
    public List<SearchUserRankUpList> modifyUserRankUp(
        Long adminId, Long userId, UserRankUpForm userRankUpForm) {

        // 관리자 확인
        userTypeCheckService.userTypeAdmin(adminId);

        // 사용자 확인
        User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_LOGIN_ID));

        user.setUserRank(userRankUpForm.getNextRank());

        return searchUserRankUp(adminId);
    }


    // 자동 등급 업
    public void autoUserRankUp(User user) {

        int boardCount = boardRepository.countByUser_UserId(user.getUserId());
        int commentCount = commentRepository.countByUser_UserId(user.getUserId());

        Optional<RankUpStandard> nextRank = rankUpStandardRepository.findNextRank(
            String.valueOf(user.getUserRank()), boardCount, commentCount);

        // 다음 등급의 자동업데이트 칼럼이 맞다면 업데이트
        if (nextRank.isPresent()) {
            RankUpStandard rankUpStandard = nextRank.get();
            if (rankUpStandard.isAutoRankUpFlag()) {
                user.setUserRank(rankUpStandard.getRankName());
            }
        }
    }
}
