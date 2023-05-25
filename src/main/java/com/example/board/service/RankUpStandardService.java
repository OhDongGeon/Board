package com.example.board.service;

import com.example.board.domain.dto.RankUpStandardDto.AddRankUpStandard;
import com.example.board.domain.dto.RankUpStandardDto.ModifyRankUpStandard;
import com.example.board.domain.dto.RankUpStandardDto.SearchRankUpStandard;
import java.util.List;


public interface RankUpStandardService {

    // 조회
    List<SearchRankUpStandard> searchRankUpStandard(Long userId, boolean userTypeCheck);


    // 저장
    List<SearchRankUpStandard> addRankUpStandard(
        Long userId, AddRankUpStandard addRankUpStandard);


    // 수정
    List<SearchRankUpStandard> modifyRankUpStandard(
        Long userId, Long standardId, ModifyRankUpStandard modifyRankUpStandard);


    // 삭제
    List<SearchRankUpStandard> deleteRankUpStandard(Long userId, Long standardId);

}
