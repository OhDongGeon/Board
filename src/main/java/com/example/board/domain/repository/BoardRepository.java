package com.example.board.domain.repository;

import com.example.board.domain.entity.Board;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query(value = "SELECT bo.boardId, bo.boardTitle, bo.user.userId "
                 + "     , bo.user.userNickName , bo.createDate "
                 + "  FROM Board bo "
                 + " WHERE (bo.user.userId = :userId    OR bo.boardPublicFlag = true) "
                 + "   AND bo.boardTitle LIKE %:title% "
                 + "   AND bo.user.userNickName LIKE %:nickName%")
    List<Object[]> findBoardList(Long userId, String title, String nickName, Pageable pageable);

    Optional<Board> findByBoardId(Long boardId);

}
