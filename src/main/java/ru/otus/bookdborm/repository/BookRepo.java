package ru.otus.bookdborm.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.bookdborm.domain.Book;


public interface BookRepo extends CrudRepository<Book, Long> {

    @Modifying
    @Query("update Book b set b.title = :title where b.id = :id")
    void updateTitleById(@Param("id") long id, @Param("title") String title);


    @Query("select distinct b from Book b left join fetch b.genre left join fetch b.author")
    Iterable<Book> findAll();

}
