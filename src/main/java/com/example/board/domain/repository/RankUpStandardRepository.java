package com.example.board.domain.repository;

import com.example.board.domain.entity.RankUpStandard;
import com.example.board.domain.type.RankType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RankUpStandardRepository extends JpaRepository<RankUpStandard, Long> {

    List<RankUpStandard> findAllByOrderByRankName();

    Optional<RankUpStandard> findByStandardId(Long standardId);

    Optional<RankUpStandard> findTop1ByRankNameLessThanOrderByRankNameDesc(RankType rankName);

    Optional<RankUpStandard> findTop1ByRankNameGreaterThanOrderByRankName(RankType rankName);

    // 자동 등급 업 다음 등급 조회
    @Query(value = "    SELECT * "
                 + "      FROM Rank_Up_Standard "
                 + "     WHERE rank_Name     > :rankName "
                 + "       AND board_Count   <= :boardCount "
                 + "       AND comment_Count <= :commentCount "
                 + "  ORDER BY rank_Name "
                 + "     LIMIT 1 ", nativeQuery = true)
    Optional<RankUpStandard> findNextRank(String rankName, int boardCount, int commentCount);

    boolean existsByRankName(RankType rankName);


}
