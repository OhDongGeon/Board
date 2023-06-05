package com.example.board.controller;

import com.example.board.domain.dto.UserRankUpDto.SearchUserRankUpList;
import com.example.board.domain.form.UserRankUpForm;
import com.example.board.security.TokenProvider;
import com.example.board.service.RankUpService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class RankUpController {

    private final RankUpService rankUpService;
    private final TokenProvider tokenProvider;
    public final String TOKEN_HEADER = "USER_TOKEN_HEADER";


    // 등급업 진행해야할 사용자 조회 (관리자)
    @GetMapping("/rankup/user")
    public ResponseEntity<List<SearchUserRankUpList>> searchUserRankUp(
        @RequestHeader(name = TOKEN_HEADER) String token) {

        return ResponseEntity.ok(rankUpService.searchUserRankUp(
            tokenProvider.getTokenUserId(token)));
    }

    // 등급업 (관리자)
    @PutMapping("/rankup/user/{userId}")
    public ResponseEntity<List<SearchUserRankUpList>> modifyUserRankUp(
        @RequestHeader(name = TOKEN_HEADER) String token,
        @PathVariable("userId") Long userId,
        @RequestBody @Valid UserRankUpForm userRankUpForm) {

        return ResponseEntity.ok(rankUpService.modifyUserRankUp(
            tokenProvider.getTokenUserId(token), userId, userRankUpForm));
    }
}
