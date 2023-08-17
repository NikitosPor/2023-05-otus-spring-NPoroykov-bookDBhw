package ru.otus.bookdborm.service;

import ru.otus.bookdborm.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookOperationsService {

    Book create(String bookTitle, String bookAuthor, String bookGenre);

    void deleteById(long id);

    Optional<Book> getById(long id);

    long getNumberOfAll();

    List<Book> getAll();

    void updateTitleById(long id, String newTitle);

}
