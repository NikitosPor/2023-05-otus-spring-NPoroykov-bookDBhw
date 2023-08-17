package ru.otus.bookdborm.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.bookdborm.domain.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepoJpa implements CommentRepo {

    @PersistenceContext
    private final EntityManager em;

    public CommentRepoJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public long count() {
        Query query = em.createQuery("select count(c) from Comment c");
        long resultCount = (long) query.getSingleResult();
        return resultCount;
    }

    @Override
    @Transactional
    public Comment save(Comment Comment) {
        if (Comment.getId() <= 0) {
            em.persist(Comment);
        } else {
            em.merge(Comment);
        }
        return Comment;
    }

    @Override
    public Optional<Comment> getById(long id) {
        Optional<Comment> comment = Optional.ofNullable(em.find(Comment.class, id));
        return comment;
    }

    @Override
    public void deleteById(long id) {
        Comment removedComment = em.find(Comment.class, id);
        em.remove(removedComment);
    }

    @Override
    public void updateById(long id, String comment) {
        Comment updatedComment = em.find(Comment.class, id);
        updatedComment.setComment(comment);
        this.save(updatedComment);
    }

    public List<Comment> getListByBookId(long bookId) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.book.id = :bookId",
                Comment.class);
        query.setParameter("bookId", bookId);
        List<Comment> listOfComments = query.getResultList();
        return listOfComments;
    }

}
