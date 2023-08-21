package ru.otus.bookdborm.shell;

import org.springframework.core.convert.ConversionService;
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

    private final ConversionService conversionService;

    private final GenreOperationsService genreOperationsService;

    public AppShellControllerGenre(GenreOperationsService genreOperationsService,
                                   IOService ioService,
                                   ConversionService conversionService) {
        this.genreOperationsService = genreOperationsService;
        this.ioService = ioService;
        this.conversionService = conversionService;
    }


    @ShellMethod(value = "Cоздание нового жанра", key = {"gc", "genre creation"})
    public String askForGenreCreation() {
        ioService.outputString("Введите <Название жанра> и нажмите Enter");
        String genreTitle = ioService.readString();
        Genre genre = new Genre(0, genreTitle);

        Genre createdGenre = genreOperationsService.create(genre);
        if (createdGenre != null) {
            return conversionService.convert(createdGenre, String.class);
        } else {
            return "Жанр не создан((";
        }
    }

    @ShellMethod(value = "Просмотр жанра по ID", key = {"gs", "genre search"})
    public String askForGenreById(long id) {
        Optional<Genre> genre = genreOperationsService.getById(id);

        if (genre.isEmpty()) {
            return "Жанр не найден ((";
        } else {
            return conversionService.convert(genre.get(), String.class);
        }
    }

    @ShellMethod(value = "Узнать количество жанров", key = {"ga", "genre amount"})
    public String askForGenreAmount() {
        long numberOfGenres = genreOperationsService.countOfAll();
        String numberOfGenresString = String.format("Количество жанров в таблице = %d", numberOfGenres);

        return numberOfGenresString;
    }

    @ShellMethod(value = "Показать все жанры", key = {"gl", "genre list"})
    public void askForAllGenres() {
        List<Genre> listOfGenres = genreOperationsService.getAll();
        for (Genre genre : listOfGenres) {
            String genreString = conversionService.convert(genre, String.class);
            ioService.outputString(genreString);
        }
    }

    @ShellMethod(value = "Просмотр жанра по TITLE", key = {"gn", "genre title"})
    public String askForGenreByTitle(String title) {
        Optional<Genre> genre = genreOperationsService.getByTitle(title);

        if (genre.isEmpty()) {
            return "Жанр не найден ((";
        } else {
            return conversionService.convert(genre.get(), String.class);
        }
    }

}
