package ru.otus.bookdborm.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.bookdborm.domain.Author;
import ru.otus.bookdborm.domain.Book;
import ru.otus.bookdborm.domain.Genre;
import ru.otus.bookdborm.repository.AuthorRepo;
import ru.otus.bookdborm.repository.BookRepo;
import ru.otus.bookdborm.repository.GenreRepo;

import java.util.List;
import java.util.Optional;

@Service
public class BookOperationsServiceImpl implements BookOperationsService {

    private final BookRepo bookRepo;

    private final AuthorRepo authorRepo;

    private final GenreRepo genreRepo;

    public BookOperationsServiceImpl(BookRepo bookRepo, AuthorRepo authorRepo, GenreRepo genreRepo) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
        this.genreRepo = genreRepo;
    }

    @Transactional
    public Book create(String bookTitle, String bookAuthor, String bookGenre) {
        Author author = authorRepo.findByName(bookAuthor)
                .orElseGet(() -> authorRepo.save(new Author(0, bookAuthor)));
        Genre genre = genreRepo.findByTitle(bookGenre)
                .orElseGet(() -> genreRepo.save(new Genre(0, bookGenre)));

        Book createdBook = new Book(0L, bookTitle, author, genre);
        return bookRepo.save(createdBook);
    }

    @Transactional
    public void deleteById(long id) {
        bookRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Book> getById(long id) {
        return bookRepo.findById(id);
    }

    @Transactional(readOnly = true)
    public long getNumberOfAll() {
        return bookRepo.count();
    }

    @Transactional(readOnly = true)
    public List<Book> getAll() {
        return bookRepo.findAll();
    }

    @Transactional
    @Override
    public void updateTitleById(long id, String newTitle) {
        bookRepo.updateTitleById(id, newTitle);
    }


}
