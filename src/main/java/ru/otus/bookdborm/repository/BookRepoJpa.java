package ru.otus.bookdborm.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.bookdborm.domain.Book;

import java.util.List;
import java.util.Optional;

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
    public Book save(Book book) {
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
        TypedQuery<Book> query = em.createQuery("select distinct b from Book b " +
                "left join fetch b.genre " +
                "left join fetch b.author", Book.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Book removedBook = em.find(Book.class, id);
        em.remove(removedBook);
    }

    @Override
    public void updateTitleById(long id, String title) {
        Book updatedBook = em.find(Book.class, id);
        updatedBook.setTitle(title);
        this.save(updatedBook);
    }
}
