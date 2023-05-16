package com.example.board.domain.dto;

import com.example.board.domain.entity.User;
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
        private String userRank;

        public User save(SignUp signUp) {
            return User.builder()
                .loginId(signUp.getLoginId())
                .userNickName(signUp.getUserNickName())
                .userPassword(signUp.getUserPassword())
                .userRank(signUp.getUserRank())
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
