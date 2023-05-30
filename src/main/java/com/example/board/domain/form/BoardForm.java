package com.example.board.domain.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class BoardForm {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListBoard {

        private String boardTitle;
        private String nickName;
    }


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MergeBoard {

        @NotNull(message = "게시판 분류를 선택하세요.")
        private Long categoryId;
        @NotBlank(message = "게시글 제목을 입력하세요.")
        @Size(min = 5, max = 100, message = "게시글 제목은 5자 이상 100자 이하입니다.")
        private String boardTitle;
        @NotBlank(message = "게시글을 입력하세요.")
        private String boardContent;
        private boolean boardPublicFlag;
    }
}
