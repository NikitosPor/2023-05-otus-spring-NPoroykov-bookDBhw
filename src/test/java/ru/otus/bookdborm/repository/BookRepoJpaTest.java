package ru.otus.bookdborm.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.bookdborm.domain.Author;
import ru.otus.bookdborm.domain.Book;
import ru.otus.bookdborm.domain.Genre;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
@DataJpaTest
class BookRepoJpaTest {

    private static final int EXPECTED_NUMBER_OF_BOOKS = 3;

    private static final long FIRST_BOOK_ID = 1L;

    private static final String BOOK_TITLE = "War and peace";

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен показывать количество книг в базе данных")
    @Test
    void countTest() {
        var bookCount = bookRepo.count();
        assertThat(bookCount).isEqualTo(EXPECTED_NUMBER_OF_BOOKS);
    }

    @DisplayName(" должен добавлять книгу в базу данных")
    @Test
    void saveTest() {
        var expectedBook = new Book(0L, "Anna Karenina",
                new Author(0, "Ivan Bunin"),
                new Genre(0, "Commedian"));
        Book book = bookRepo.save(expectedBook);
        var actualBook = em.find(Book.class, book.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName(" должен загружать информацию о нужной книге по её id из базы данных")
    @Test
    void getByIdTest() {
        val optionalActualBook = bookRepo.findById(FIRST_BOOK_ID);
        val expectedBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(optionalActualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName(" должен получать все книги из базы данных")
    @Test
    void getAllTest() {
        val books = bookRepo.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(b -> !Objects.equals(b.getTitle(), "") && b.getTitle().getBytes().length > 0)
                .allMatch(b -> !Objects.equals(b.getAuthor().getName(), "") && b.getAuthor().getName().getBytes().length > 0)
                .allMatch(b -> !Objects.equals(b.getGenre().getTitle(), "") && b.getGenre().getTitle().getBytes().length > 0);
    }

    @DisplayName(" должен удалять книгу из базы данных")
    @Test
    void deleteByIdTest() {
        assertThatCode(() -> bookRepo.findById(FIRST_BOOK_ID)).doesNotThrowAnyException();
        bookRepo.deleteById(FIRST_BOOK_ID);
        Book actualBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(actualBook).isNull();
    }

    @DisplayName(" должен обновлять название книги в базе данных")
    @Test
    void updateTitleByIdTest() {
        bookRepo.updateTitleById(FIRST_BOOK_ID, BOOK_TITLE);
        Book actualBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(actualBook.getTitle()).isEqualTo(BOOK_TITLE);
    }
}