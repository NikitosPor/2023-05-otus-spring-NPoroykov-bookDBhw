package ru.otus.bookdborm.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.bookdborm.domain.Genre;
import ru.otus.bookdborm.helpers.IOService;
import ru.otus.bookdborm.service.GenreOperationsService;

import java.util.List;
import java.util.Optional;

@ShellComponent
public class AppShellControllerGenre {

    private final IOService ioService;

    private final GenreOperationsService genreOperationsService;

    public AppShellControllerGenre(GenreOperationsService genreOperationsService,
                                   IOService ioService) {
        this.genreOperationsService = genreOperationsService;
        this.ioService = ioService;
    }


    @ShellMethod(value = "Cоздание нового жанра в таблице GENRES", key = {"gc", "genre creation"})
    public void askForGenreCreation() {
        ioService.outputString("Введите <Название жанра> и нажмите Enter");
        String genreTitle = ioService.readString();
        Genre genre = new Genre(0, genreTitle);

        Genre createdGenre = genreOperationsService.create(genre);
        if (createdGenre != null) {
            String genreString = String.format("Создан жанр ID: %d, Название: %s",
                    genre.getId(), genre.getTitle());
            ioService.outputString(genreString);
        }
    }

    @ShellMethod(value = "Просмотр жанра в таблице GENRES по ID", key = {"gs", "genre search"})
    public void askForGenreById(long id) {
        Optional<Genre> genre = genreOperationsService.getById(id);

        if (genre.isEmpty()) {
            ioService.outputString("Жанр не найден ((");
        } else {
            String genreIdString = String.format("Жанр ID: %d, Название: %s",
                    genre.get().getId(), genre.get().getTitle());
            ioService.outputString(genreIdString);
        }
    }

    @ShellMethod(value = "Узнать количество жанров в таблице GENRES", key = {"ga", "genre amount"})
    public void askForGenreAmount() {
        long numberOfGenres = genreOperationsService.countOfAll();
        String numberOfGenresString = String.format("Количество жанров в таблице = %d", numberOfGenres);
        ioService.outputString(numberOfGenresString);
    }

    @ShellMethod(value = "Показать все жанры в таблице GENRES", key = {"gl", "genre list"})
    public void askForAllGenres() {
        List<Genre> listOfGenres = genreOperationsService.getAll();
        for (Genre genre : listOfGenres) {
            String genreString = String.format("Жанр ID: %d, Название: %s", genre.getId(), genre.getTitle());
            ioService.outputString(genreString);
        }
    }

}
