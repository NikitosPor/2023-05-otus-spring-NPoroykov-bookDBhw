package ru.otus.bookdborm.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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
    @Transactional(readOnly = true)
    public long count() {
        Query query = em.createQuery("select count(c) from Comment c");
        long resultCount = (long) query.getSingleResult();
        return resultCount;
    }

    @Override
    @Transactional
    public Comment insert(Comment Comment) {
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
        Query query = em.createQuery("delete from Comment c where c.id= :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void updateById(long id, String title) {
        Query query = em.createQuery("update Comment c set c.comment = :comment where c.id = :id");
        query.setParameter("comment", title);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public List<Comment> getListByBookId(long bookId) {
        Query query = em.createQuery("select distinct c from Comment c where c.bookId = :bookId");
        query.setParameter("bookId", bookId);
        List<Comment> listOfComments = query.getResultList();
        return listOfComments;
    }

}
