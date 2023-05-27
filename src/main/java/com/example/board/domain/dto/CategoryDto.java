package com.example.board.domain.dto;

import com.example.board.domain.type.RankType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long categoryId;
    private String categoryTitle;
    private RankType categoryRank;
    private boolean categoryUesFlag;
}
