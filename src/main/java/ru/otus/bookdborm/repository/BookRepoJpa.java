package ru.otus.bookdborm.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.EntityGraph;
import org.springframework.stereotype.Repository;
import ru.otus.bookdborm.domain.Book;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
public class BookRepoJpa implements BookRepo {

    @PersistenceContext
    private final EntityManager em;

    public BookRepoJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public long count() {
        Query query = em.createQuery("select count(b) from Book b");
        return (long) query.getSingleResult();
    }

    @Override
    public Book insert(Book book) {
        if (book.getId() <= 0) {
            em.persist(book);
        } else {
            em.merge(book);
        }
        return book;
    }

    @Override
    public Optional<Book> getById(long id) {
        Optional<Book> book = Optional.ofNullable(em.find(Book.class, id));
        return book;
    }

    @Override
    public List<Book> getAll() {
        EntityGraph<?> authorEntityGraph = em.getEntityGraph("books-author-entity-graph");
        TypedQuery<Book> query = em.createQuery("select distinct b from Book b left join fetch b.genre", Book.class);
        query.setHint(FETCH.getKey(), authorEntityGraph);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Book b where b.id= :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Optional<Book> getByTitle(String title) {
        return Optional.ofNullable(em.find(Book.class, title));
    }

    @Override
    public void updateTitleById(long id, String title) {
        Query query = em.createQuery("update Book b set b.title = :title where b.id = :id");
        query.setParameter("title", title);
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
