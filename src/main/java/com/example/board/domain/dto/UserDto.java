package com.example.board.domain.dto;

import com.example.board.domain.entity.User;
import com.example.board.domain.type.RankType;
import com.example.board.domain.type.UserType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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

        @NotBlank(message = "아이디를 입력하세요.")
        @Size(min = 5, max = 30, message = "아이디는 5자 이상 30자 이하입니다.")
        private String loginId;
        @NotBlank(message = "닉네임을 입력하세요.")
        @Size(min = 2, max = 30, message = "닉네임은 2자 이상 30자 이하입니다.")
        private String userNickName;
        @NotBlank(message = "비밀번호를 입력하세요.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{10,}",
                 message = "비밀번호는 10자 이상, 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String userPassword;

        public User save(SignUp signUp, UserType userType) {
            return User.builder()
                .loginId(signUp.getLoginId())
                .userNickName(signUp.getUserNickName())
                .userPassword(signUp.getUserPassword())
                .userRank(userType.equals(UserType.USER) ? RankType.LEVEL1 : RankType.ADMIN)
                .build();
        }
    }


    @Getter
    @Setter
    public static class SignIn {

        @NotBlank(message = "아이디를 입력하세요.")
        private String loginId;
        @NotBlank(message = "비밀번호를 입력하세요.")
        private String userPassword;
    }
}
