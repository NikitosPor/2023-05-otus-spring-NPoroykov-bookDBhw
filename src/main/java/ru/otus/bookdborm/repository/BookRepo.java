package ru.otus.bookdborm.repository;

import ru.otus.bookdborm.domain.Book;

import java.util.List;
import java.util.Optional;


public interface BookRepo {

    long count();

    Book insert(Book book);

    Optional<Book> getById(long id);

    List<Book> getAll();

    void deleteById(long id);

    void updateTitleById(long id, String newTitle);
}
