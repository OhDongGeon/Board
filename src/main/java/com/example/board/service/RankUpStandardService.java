package com.example.board.service;

import com.example.board.domain.dto.RankUpStandardDto;
import com.example.board.domain.form.RankUpStandardForm.AddRankUpStandard;
import com.example.board.domain.form.RankUpStandardForm.ModifyRankUpStandard;
import java.util.List;


public interface RankUpStandardService {

    // 조회
    List<RankUpStandardDto> searchRankUpStandard();


    // 저장
    List<RankUpStandardDto> addRankUpStandard(
        Long userId, AddRankUpStandard addRankUpStandard);


    // 수정
    List<RankUpStandardDto> modifyRankUpStandard(
        Long userId, Long standardId, ModifyRankUpStandard modifyRankUpStandard);


    // 삭제
    List<RankUpStandardDto> deleteRankUpStandard(Long userId, Long standardId);
}
