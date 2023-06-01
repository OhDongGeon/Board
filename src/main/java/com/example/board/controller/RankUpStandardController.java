package com.example.board.controller;

import com.example.board.domain.dto.RankUpStandardDto;
import com.example.board.domain.form.RankUpStandardForm.AddRankUpStandard;
import com.example.board.domain.form.RankUpStandardForm.ModifyRankUpStandard;
import com.example.board.security.TokenProvider;
import com.example.board.service.RankUpStandardService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class RankUpStandardController {

    private final RankUpStandardService rankUpStandardService;
    private final TokenProvider tokenProvider;
    public final String TOKEN_HEADER = "USER_TOKEN_HEADER";


    // 조회
    @GetMapping("/rankup")
    public ResponseEntity<List<RankUpStandardDto>> searchRankUpStandard() {

        return ResponseEntity.ok(rankUpStandardService.searchRankUpStandard());
    }


    // 저장
    @PostMapping("/rankup")
    public ResponseEntity<List<RankUpStandardDto>> addRankUpStandard(
        @RequestHeader(name = TOKEN_HEADER) String token,
        @RequestBody @Valid AddRankUpStandard addRankUpStandard) {

        return ResponseEntity.ok(rankUpStandardService.addRankUpStandard(
            tokenProvider.getTokenUserId(token), addRankUpStandard));
    }


    // 수정
    @PutMapping("/rankup/{standardId}")
    public ResponseEntity<List<RankUpStandardDto>> modifyRankUpStandard(
        @RequestHeader(name = TOKEN_HEADER) String token,
        @PathVariable("standardId") Long standardId,
        @RequestBody @Valid ModifyRankUpStandard modifyRankUpStandard) {

        return ResponseEntity.ok(rankUpStandardService.modifyRankUpStandard(
            tokenProvider.getTokenUserId(token), standardId, modifyRankUpStandard));
    }


    // 삭제
    @DeleteMapping("/rankup/{standardId}")
    public ResponseEntity<List<RankUpStandardDto>> deleteRankUpStandard(
        @RequestHeader(name = TOKEN_HEADER) String token,
        @PathVariable("standardId") Long standardId) {

        return ResponseEntity.ok(rankUpStandardService.deleteRankUpStandard(
            tokenProvider.getTokenUserId(token), standardId));
    }
}
