package ru.otus.bookdborm.repository;

import ru.otus.bookdborm.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepo {

    long count();

    Comment save(Comment comment);

    Optional<Comment> getById(long id);

    void updateById(long id, String title);

    void deleteById(long id);

    List<Comment> getListByBookId(long bookId);

}
