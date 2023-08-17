package ru.otus.bookdborm.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.bookdborm.domain.Comment;
import ru.otus.bookdborm.repository.CommentRepo;

import java.util.List;
import java.util.Optional;

@Service
public class CommentOperationsServiceImpl implements CommentOperationsService {

    private final CommentRepo commentRepo;

    public CommentOperationsServiceImpl(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }


    @Transactional
    @Override
    public Comment create(Comment comment) {
        return commentRepo.save(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> getById(long id) {
        return commentRepo.getById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getListByBookId(long id) {
        return commentRepo.getListByBookId(id);
    }

    @Transactional
    @Override
    public void updateById(long id, String comment) {
        commentRepo.updateById(id, comment);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepo.deleteById(id);
    }
}
