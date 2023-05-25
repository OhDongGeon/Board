package com.example.board.domain.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;

public enum RankType {

    // 관리자
    ADMIN,

    // 회원
    LEVEL1,
    LEVEL2,
    LEVEL3,
    LEVEL4,
    LEVEL5;

    @JsonCreator
    public static RankType parsing(String inputValue) {
        return Stream.of(RankType.values())
            .filter(category -> category.toString().equals(inputValue.toUpperCase()))
            .findFirst()
            .orElse(null);
    }
}
