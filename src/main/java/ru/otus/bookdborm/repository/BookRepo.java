package ru.otus.bookdborm.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.bookdborm.domain.Book;

import java.util.List;


public interface BookRepo extends CrudRepository<Book, Long> {

    @Modifying
    @Query("update Book b set b.title = :title where b.id = :id")
    void updateTitleById(@Param("id") long id, @Param("title") String title);

    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();

}
