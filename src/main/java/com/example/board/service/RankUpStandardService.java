package com.example.board.service;

import com.example.board.domain.dto.RankUpStandardDto.SearchRankUpStandard;
import com.example.board.domain.form.RankUpStandardForm.AddRankUpStandard;
import com.example.board.domain.form.RankUpStandardForm.ModifyRankUpStandard;
import java.util.List;


public interface RankUpStandardService {

    // 조회
    List<SearchRankUpStandard> searchRankUpStandard();


    // 저장
    List<SearchRankUpStandard> addRankUpStandard(
        Long userId, AddRankUpStandard addRankUpStandard);


    // 수정
    List<SearchRankUpStandard> modifyRankUpStandard(
        Long userId, Long standardId, ModifyRankUpStandard modifyRankUpStandard);


    // 삭제
    List<SearchRankUpStandard> deleteRankUpStandard(Long userId, Long standardId);

}
