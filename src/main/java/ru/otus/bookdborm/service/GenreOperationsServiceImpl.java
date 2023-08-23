package ru.otus.bookdborm.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.bookdborm.domain.Genre;
import ru.otus.bookdborm.repository.GenreRepo;

import java.util.List;
import java.util.Optional;

@Service
public class GenreOperationsServiceImpl implements GenreOperationsService {

    private final GenreRepo genreRepo;

    public GenreOperationsServiceImpl(GenreRepo genreRepo) {
        this.genreRepo = genreRepo;
    }

    @Transactional
    public Genre create(Genre genre) {
        return genreRepo.save(genre);
    }

    @Transactional(readOnly = true)
    public Optional<Genre> getById(long id) {
        return genreRepo.findById(id);
    }

    @Transactional(readOnly = true)
    public long countOfAll() {
        return genreRepo.count();
    }

    @Transactional(readOnly = true)
    public List<Genre> getAll() {
        return (List<Genre>) genreRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Genre> getByTitle(String title) {
        return genreRepo.findByTitle(title);
    }
}
