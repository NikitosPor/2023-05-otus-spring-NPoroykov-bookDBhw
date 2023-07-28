package ru.otus.bookdborm.service;

import ru.otus.bookdborm.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorOperationsService {

    Author create(Author name);

    Optional<Author> getById(long id);

    long countOfAll();

    List<Author> getAll();

    Optional<Author> getByName(String name);

}
