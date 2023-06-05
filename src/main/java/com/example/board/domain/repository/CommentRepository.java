package com.example.board.domain.repository;

import com.example.board.domain.entity.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    int countByBoard_BoardIdAndCommentIdLessThanEqual(Long boardId, Long commentId);

    int countByUser_UserId(Long userId);

    Optional<Comment> findByCommentId(Long commentId);

    List<Comment> findByBoard_BoardId(Long boardId, Pageable pageable);
}
