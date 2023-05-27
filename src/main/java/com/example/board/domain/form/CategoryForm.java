package com.example.board.domain.form;

import com.example.board.domain.type.RankType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class CategoryForm {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MergeCategory {

        @NotNull(message = "게시판 분류명을 입력하세요.")
        @Size(min = 1, max = 10, message = "게시판 분류명은 1자 이상 10자 이하입니다.")
        private String categoryTitle;
        @NotNull(message = "등급을 확인해주세요.")
        @Enumerated(EnumType.STRING)
        private RankType categoryRank;
        private boolean categoryUesFlag;
    }
}
