package ru.den.shareitserver.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.den.shareitserver.item.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByItemId(Long itemId);
}
