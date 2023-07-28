package ru.otus.bookdborm.service;

import ru.otus.bookdborm.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentOperationsService {

    Comment create(Comment comment);

    Optional<Comment> getById(long id);

    List<Comment> getListByBookId(long id);

    void updateById(long id, String comment);

    void deleteById(long id);
}
