package com.example.board.domain.dto;

import com.example.board.domain.type.RankType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CategoryDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchCategory {

        private Long categoryId;
        private String categoryTitle;
        private RankType categoryRank;
        private boolean categoryUesFlag;
    }
}
