package com.example.board.domain.dto;

import com.example.board.domain.type.RankType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class UserRankUpDto {

    public interface SearchUserRankUp {

        Long getUserId();

        String getUserNickName();

        RankType getRankName();

        Long getBoardCount();

        Long getCommentCount();

        RankType getNextRank();
    }


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchUserRankUpList {

        private Long userId;
        private String userNickName;
        private RankType rankName;
        private Long boardCount;
        private Long commentCount;
        private RankType nextRank;
    }
}
