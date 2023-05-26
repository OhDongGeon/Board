package com.example.board.domain.repository;

import com.example.board.domain.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByLoginId(String loginId);

    boolean existsByUserNickName(String userNickName);

    Optional<User> findByUserId(Long userId);

    Optional<User> findByLoginId(String loginId);
}
