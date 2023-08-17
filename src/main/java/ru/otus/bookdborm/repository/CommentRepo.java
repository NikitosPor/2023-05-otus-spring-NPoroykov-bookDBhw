package ru.otus.bookdborm.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.bookdborm.domain.Comment;

import java.util.List;

public interface CommentRepo extends CrudRepository<Comment, Long> {
    @Modifying
    @Query("update Comment c set c.comment = :comment where c.id = :id")
    void updateById(@Param("id") long id, @Param("comment") String comment);

    List<Comment> findAllByBookId(long bookId);

}
