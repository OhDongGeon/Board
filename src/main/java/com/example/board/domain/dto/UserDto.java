package com.example.board.domain.dto;

import com.example.board.domain.entity.User;
import com.example.board.domain.type.RankType;
import com.example.board.domain.type.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class UserDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignUp {

        private String loginId;
        private String userNickName;
        private String userPassword;

        public User save(SignUp signUp, UserType userType) {
            RankType rankType = RankType.LEVEL1;
            if (userType.equals(UserType.ADMIN)) {
                rankType = RankType.ADMIN;
            }

            return User.builder()
                .loginId(signUp.getLoginId())
                .userNickName(signUp.getUserNickName())
                .userPassword(signUp.getUserPassword())
                .userRank(rankType)
                .build();
        }
    }


    @Getter
    @Setter
    public static class SignIn {

        private String loginId;
        private String userPassword;
    }
}
