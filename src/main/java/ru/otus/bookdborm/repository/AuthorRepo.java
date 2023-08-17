package ru.otus.bookdborm.repository;

import ru.otus.bookdborm.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepo {

    long count();

    Author save(Author author);

    Optional<Author> getById(long id);

    List<Author> getAll();

    Optional<Author> getByName(String name);
}
