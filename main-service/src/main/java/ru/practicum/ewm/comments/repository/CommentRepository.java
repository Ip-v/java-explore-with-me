package ru.practicum.ewm.comments.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.ewm.comments.model.Comment;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.utils.State;

import java.util.List;

/**
 * Репозиторий категорий
 */
public interface CommentRepository extends JpaRepository<Comment, Long>, QuerydslPredicateExecutor<Comment> {
    List<Comment> findByAuthor(User author);

    List<Comment> findAllByAuthorAndState(User author, State state, Pageable pageable);
}
