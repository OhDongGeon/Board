package com.example.board.domain.repository;

import com.example.board.domain.entity.RankUpStandard;
import com.example.board.domain.type.RankType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankUpStandardRepository extends JpaRepository<RankUpStandard, Long> {

    List<RankUpStandard> findAllByOrderByRankName();

    Optional<RankUpStandard> findByStandardId(Long standardId);

    Optional<RankUpStandard> findTop1ByRankNameLessThanOrderByRankNameDesc(RankType rankName);

    Optional<RankUpStandard> findTop1ByRankNameGreaterThanOrderByRankName(RankType rankName);

    boolean existsByRankName(RankType rankName);
}
