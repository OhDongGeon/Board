package com.example.board.domain.repository;

import com.example.board.domain.dto.BoardDto.SearchList;
import com.example.board.domain.entity.Board;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query(value = "SELECT bo.boardId               AS boardId "
                 + "     , bo.boardTitle            AS boardTitle "
                 + "     , bo.user.userId           AS userId "
                 + "     , bo.user.userNickName     AS userNickName "
                 + "     , bo.createDate            AS createDate "
                 + "  FROM Board bo "
                 + " WHERE (bo.user.userId      = :userId "
                 + "    OR bo.boardPublicFlag   = true) "
                 + "   AND bo.boardTitle        LIKE %:title% "
                 + "   AND bo.user.userNickName LIKE %:nickName%")
    List<SearchList> findAllBoardList(Long userId, String title, String nickName, Pageable pageable);

    @Query(value = "SELECT bo.boardId               AS boardId "
                 + "     , bo.boardTitle            AS boardTitle "
                 + "     , bo.user.userId           AS userId "
                 + "     , bo.user.userNickName     AS userNickName "
                 + "     , bo.createDate            AS createDate "
                 + "  FROM Board bo "
                 + " WHERE (bo.user.userId          = :userId "
                 + "    OR bo.boardPublicFlag       = true) "
                 + "   AND bo.category.categoryId   = :categoryId "
                 + "   AND bo.boardTitle            LIKE %:title% "
                 + "   AND bo.user.userNickName     LIKE %:nickName%")
    List<SearchList> findBoardList(Long userId, Long categoryId, String title, String nickName, Pageable pageable);

    Optional<Board> findByBoardId(Long boardId);

}
