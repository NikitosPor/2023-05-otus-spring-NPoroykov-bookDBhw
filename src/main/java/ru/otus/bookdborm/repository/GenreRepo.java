package ru.otus.bookdborm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.bookdborm.domain.Genre;

import java.util.Optional;

public interface GenreRepo extends CrudRepository<Genre, Long> {

    Optional<Genre> findByTitle(@Param("title") String title);

}
