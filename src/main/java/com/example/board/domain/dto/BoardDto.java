package com.example.board.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class BoardDto {

    // interface projection 쿼리의 데이터를 받아옴 Alias(AS) 을 사용하여 칼럼명 맞춰줘야 함
    // class projection 를 시도 해보았지만 이너 클래스에선 간편하게 되질 않았음 따로 Dto 를 나눠야 했고,
    // Select 시 Dto 위치를 지정해 줘야 했음
    // ex) select new com.example.board.domain.dto(칼럼명) from 테이블
    public interface SearchList {

        Long getBoardId();

        String getBoardTitle();

        Long getUserId();

        String getUserNickName();

        LocalDateTime getCreateDate();
    }


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchListBoard {

        private Long boardId;
        private String boardTitle;
        private Long userId;
        private String userNickName;
        @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime createDate;
    }


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchContentBoard {

        private Long userId;
        private String userNickName;
        private Long categoryId;
        private String categoryTitle;
        private Long boardId;
        private String boardTitle;
        private String boardContent;
        @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime createDate;
        private List<CommentDto> commentDto;
    }
}
