package com.example.board.service.impl;

import static com.example.board.domain.type.RankType.ADMIN;
import static com.example.board.exception.ErrorCode.ALREADY_RANK;
import static com.example.board.exception.ErrorCode.NOT_ADD_RANK;
import static com.example.board.exception.ErrorCode.NOT_FIND_RANK;
import static com.example.board.exception.ErrorCode.OVER_RANK_CHECK_BOARD_COMMENT;
import static com.example.board.exception.ErrorCode.REGISTERED_RANK_CATEGORY;
import static com.example.board.exception.ErrorCode.UNDER_RANK_CHECK_BOARD_COMMENT;

import com.example.board.domain.dto.RankUpStandardDto;
import com.example.board.domain.entity.RankUpStandard;
import com.example.board.domain.entity.User;
import com.example.board.domain.form.RankUpStandardForm.AddRankUpStandard;
import com.example.board.domain.form.RankUpStandardForm.ModifyRankUpStandard;
import com.example.board.domain.repository.CategoryRepository;
import com.example.board.domain.repository.RankUpStandardRepository;
import com.example.board.domain.type.RankType;
import com.example.board.exception.ErrorCode;
import com.example.board.exception.GlobalException;
import com.example.board.service.RankUpStandardService;
import com.example.board.service.UserTypeCheckService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class RankUpStandardImpl implements RankUpStandardService {

    private final UserTypeCheckService userTypeCheckService;
    private final RankUpStandardRepository rankUpStandardRepository;
    private final CategoryRepository categoryRepository;


    // 조회
    public List<RankUpStandardDto> searchRankUpStandard() {

        List<RankUpStandardDto> infoRankUpStandards = new ArrayList<>();

        for (RankUpStandard rankUpStandard : rankUpStandardRepository.findAllByOrderByRankName()) {
            RankUpStandardDto searchRankUpStandard = RankUpStandardDto.builder()
                .standardId(rankUpStandard.getStandardId())
                .rankName(rankUpStandard.getRankName())
                .boardCount(rankUpStandard.getBoardCount())
                .commentCount(rankUpStandard.getCommentCount())
                .autoRankUpFlag(rankUpStandard.isAutoRankUpFlag())
                .build();

            infoRankUpStandards.add(searchRankUpStandard);
        }

        return infoRankUpStandards;
    }


    // 저장
    @Transactional
    public List<RankUpStandardDto> addRankUpStandard(
        Long userId, AddRankUpStandard addRankUpStandard) {

        if (rankUpStandardRepository.existsByRankName(addRankUpStandard.getRankName())) {
            throw new GlobalException(ALREADY_RANK);
        }
        if (addRankUpStandard.getRankName().equals(ADMIN)) {
            throw new GlobalException(NOT_ADD_RANK);
        }

        // 입력 받은 등급의 전후 등급 확인
        checkUnderOverRank(addRankUpStandard.getRankName(), addRankUpStandard.getBoardCount(),
            addRankUpStandard.getCommentCount());

        User user = userTypeCheckService.userTypeAdmin(userId);

        RankUpStandard rankUpStandard = RankUpStandard.builder()
            .rankName(addRankUpStandard.getRankName())
            .boardCount(addRankUpStandard.getBoardCount())
            .commentCount(addRankUpStandard.getCommentCount())
            .autoRankUpFlag(addRankUpStandard.isAutoRankUpFlag())
            .build();

        user.getRankUpStandards().add(rankUpStandard);

        return searchRankUpStandard();
    }


    // 수정
    @Transactional
    public List<RankUpStandardDto> modifyRankUpStandard(
        Long userId, Long standardId, ModifyRankUpStandard modifyRankUpStandard) {

        User user = userTypeCheckService.userTypeAdmin(userId);

        RankUpStandard rankUpStandard = rankUpStandardRepository.findByStandardId(standardId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_RANK));

        // 입력 받은 등급의 전후 등급 확인
        checkUnderOverRank(rankUpStandard.getRankName(), rankUpStandard.getBoardCount(),
            rankUpStandard.getCommentCount());

        rankUpStandard.setBoardCount(modifyRankUpStandard.getBoardCount());
        rankUpStandard.setCommentCount(modifyRankUpStandard.getCommentCount());
        rankUpStandard.setAutoRankUpFlag(modifyRankUpStandard.isAutoRankUpFlag());
        user.getRankUpStandards().add(rankUpStandard);

        return searchRankUpStandard();
    }


    // 삭제
    @Transactional
    public List<RankUpStandardDto> deleteRankUpStandard(Long userId, Long standardId) {

        userTypeCheckService.userTypeAdmin(userId);

        RankUpStandard rankUpStandard = rankUpStandardRepository.findByStandardId(standardId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_RANK));

        if (categoryRepository.existsByCategoryRank(rankUpStandard.getRankName())) {
            throw new GlobalException(REGISTERED_RANK_CATEGORY);
        }

        rankUpStandardRepository.delete(rankUpStandard);

        return searchRankUpStandard();
    }


    // 입력 받은 등급의 전후 등급 확인
    public void checkUnderOverRank(RankType rankType, Long BoardCount, Long CommentCount) {

        // 입력 받은 등급 보다 작은 등급 중 가장 높은 등급 조회
        RankUpStandard underRank =
            rankUpStandardRepository.findTop1ByRankNameLessThanOrderByRankNameDesc(rankType)
                .orElse(null);

        if (underRank != null &&
            (underRank.getBoardCount() >= BoardCount ||
                underRank.getCommentCount() >= CommentCount)) {
            throw new GlobalException(UNDER_RANK_CHECK_BOARD_COMMENT);
        }

        // 입력 받은 등급 보다 큰 등급 중 가장 낮은 등급 조회
        RankUpStandard overRank =
            rankUpStandardRepository.findTop1ByRankNameGreaterThanOrderByRankName(rankType)
                .orElse(null);

        if (overRank != null &&
            (overRank.getBoardCount() <= BoardCount ||
                overRank.getCommentCount() <= CommentCount)) {
            throw new GlobalException(OVER_RANK_CHECK_BOARD_COMMENT);
        }
    }
}
