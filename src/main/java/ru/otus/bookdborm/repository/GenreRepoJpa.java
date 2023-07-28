package ru.otus.bookdborm.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;
import ru.otus.bookdborm.domain.Genre;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepoJpa implements GenreRepo {

    @PersistenceContext
    private final EntityManager em;

    public GenreRepoJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public long count() {
        Query query = em.createQuery("select count(g) from Genre g");
        return (long) query.getSingleResult();
    }

    @Override
    public Optional<Genre> getByTitle(String title) {
        Optional<Genre> quiredGenre;
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.title = :title",
                Genre.class);
        query.setParameter("title", title);
        try {
            quiredGenre = Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            quiredGenre = Optional.empty();
        }
        return quiredGenre;
    }

    @Override
    public Genre insert(Genre genre) {
        if (genre.getId() <= 0) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    @Override
    public Optional<Genre> getById(long id) {
        Optional<Genre> genre = Optional.ofNullable(em.find(Genre.class, id));
        return genre;
    }

    @Override
    public List<Genre> getAll() {
        Query query = em.createQuery("select g from Genre g");
        return query.getResultList();
    }
}
