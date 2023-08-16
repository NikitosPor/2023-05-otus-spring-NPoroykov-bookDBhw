package ru.otus.bookdborm.repository;

import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import ru.otus.bookdborm.domain.Author;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepoJpa implements AuthorRepo {

    @PersistenceContext
    private final EntityManager em;

    public AuthorRepoJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public long count() {
        Query query = em.createQuery("select count(a) from Author a");
        return (long) query.getSingleResult();
    }

    @Override
    public Author save(Author author) {
        if (author.getId() <= 0) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    public Optional<Author> getById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public Optional<Author> getByName(String name) {
        Optional<Author> quiredAuthor;
        TypedQuery<Author> query = em.createQuery("select a from Author a where a.name = :name",
                Author.class);
        query.setParameter("name", name);
        try {
            quiredAuthor = Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            quiredAuthor = Optional.empty();
        }
        return quiredAuthor;
    }

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

}
