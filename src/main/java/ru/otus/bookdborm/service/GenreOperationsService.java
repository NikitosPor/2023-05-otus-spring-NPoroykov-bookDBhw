package ru.otus.bookdborm.service;

import ru.otus.bookdborm.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreOperationsService {

    Genre create(Genre genre);

    Optional<Genre> getById(long id);

    long countOfAll();

    List<Genre> getAll();

    Optional<Genre> getByTitle(String title);
}
