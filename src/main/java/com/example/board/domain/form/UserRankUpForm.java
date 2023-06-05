package com.example.board.domain.form;

import com.example.board.domain.type.RankType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRankUpForm {

    @NotNull(message = "등급을 확인해주세요.")
    @Enumerated(EnumType.STRING)
    private RankType nextRank;
}
