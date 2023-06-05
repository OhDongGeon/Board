package com.example.board.domain.repository;

import com.example.board.domain.dto.UserRankUpDto.SearchUserRankUp;
import com.example.board.domain.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByLoginId(String loginId);

    boolean existsByUserNickName(String userNickName);

    Optional<User> findByUserId(Long userId);

    Optional<User> findByLoginId(String loginId);

    // 관리자가 등급업 진행할 수 있는 사용자 조회
    @Query(value = " WITH posting_count AS ( "
                 + "    SELECT us.user_id "
                 + "         , us.user_rank "
                 + "         , us.user_nick_name"
                 + "         , (SELECT COUNT(board_content)   FROM board   WHERE user_id = us.user_id) AS board_count "
                 + "         , (SELECT COUNT(comment_content) FROM comment WHERE user_id = us.user_id) AS comment_count "
                 + "      FROM user         AS us "
                 + "     WHERE us.user_rank <> 'ADMIN' "
                 + "  GROUP BY us.user_id "
                 + " ), next_rank AS ( "
                 + "    SELECT pc.user_id "
                 + "         , pc.user_nick_name"
                 + "         , pc.user_rank "
                 + "         , pc.board_count "
                 + "         , pc.comment_count "
                 + "         , rus.rank_name "
                 + "         , ROW_NUMBER() OVER(PARTITION BY pc.user_id ORDER BY rus.rank_name) rownum "
                 + "      FROM posting_count         pc "
                 + "INNER JOIN rank_up_standard      rus "
                 + "        ON rus.auto_rank_up_flag = 0 "
                 + "       AND rus.board_count       <= pc.board_count "
                 + "       AND rus.comment_count     <= pc.comment_count "
                 + "       AND rus.rank_name         > pc.user_rank) "
                 + " SELECT user_id userId, user_nick_name userNickName, user_rank rankName"
                 + "      , board_count boardCount, comment_count commentCount, rank_name nextRank"
                 + "   FROM next_rank "
                 + "  WHERE rownum = 1" , nativeQuery = true)
    List<SearchUserRankUp> findNextRankUpUser();
}
