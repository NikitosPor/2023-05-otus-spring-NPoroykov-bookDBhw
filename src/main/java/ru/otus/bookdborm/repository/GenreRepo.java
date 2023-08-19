package ru.otus.bookdborm.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.bookdborm.domain.Genre;

import java.util.Optional;

public interface GenreRepo extends CrudRepository<Genre, Long> {

    @Query("select g from Genre g where g.title = :title")
    Optional<Genre> findByTitle(@Param("title") String title);

}
