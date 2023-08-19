package ru.otus.bookdborm.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.bookdborm.domain.Author;

import java.util.Optional;

public interface AuthorRepo extends CrudRepository<Author, Long> {

    @Query("select a from Author a where a.name = :name")
    Optional<Author> findByName(@Param("name") String name);

}
