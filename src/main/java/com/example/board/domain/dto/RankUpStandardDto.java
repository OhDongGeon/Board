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
public class RankUpStandardDto {

    private Long standardId;
    private RankType rankName;
    private Long boardCount;
    private Long commentCount;
    private boolean autoRankUpFlag;
}
