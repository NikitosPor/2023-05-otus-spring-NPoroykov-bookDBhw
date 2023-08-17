package ru.otus.bookdborm.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.bookdborm.domain.Author;
import ru.otus.bookdborm.repository.AuthorRepo;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorOperationsServiceImpl implements AuthorOperationsService {

    private final AuthorRepo authorRepo;

    public AuthorOperationsServiceImpl(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

    @Transactional
    public Author create(Author author) {
        return authorRepo.save(author);
    }

    @Transactional(readOnly = true)
    public Optional<Author> getById(long id) {
        return authorRepo.findById(id);
    }

    @Transactional(readOnly = true)
    public long countOfAll() {
        return authorRepo.count();
    }

    @Transactional(readOnly = true)
    public List<Author> getAll() {
        return (List<Author>) authorRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Author> getByName(String name) {
        return authorRepo.findByName(name);
    }
}
