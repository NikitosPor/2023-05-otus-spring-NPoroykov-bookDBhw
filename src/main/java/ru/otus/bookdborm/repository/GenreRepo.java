package ru.otus.bookdborm.repository;

import ru.otus.bookdborm.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepo {

    long count();

    Genre insert(Genre genre);

    Optional<Genre> getById(long id);

    List<Genre> getAll();

    Optional<Genre>getByTitle(String title);

}
