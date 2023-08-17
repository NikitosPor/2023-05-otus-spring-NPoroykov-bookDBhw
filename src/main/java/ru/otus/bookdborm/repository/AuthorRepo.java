package ru.otus.bookdborm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.bookdborm.domain.Author;

import java.util.Optional;

public interface AuthorRepo extends CrudRepository<Author, Long> {
    Optional<Author> findByName(String name);
}
