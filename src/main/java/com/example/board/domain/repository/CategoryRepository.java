package com.example.board.domain.repository;

import com.example.board.domain.entity.Category;
import com.example.board.domain.type.RankType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByOrderByCategoryRank();

    Optional<Category> findByCategoryId(Long categoryId);

    boolean existsByCategoryRank(RankType categoryRank);
}
