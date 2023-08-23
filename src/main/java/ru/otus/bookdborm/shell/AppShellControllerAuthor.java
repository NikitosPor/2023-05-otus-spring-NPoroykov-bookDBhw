package ru.otus.bookdborm.shell;

import org.springframework.core.convert.ConversionService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.bookdborm.domain.Author;
import ru.otus.bookdborm.helpers.IOService;
import ru.otus.bookdborm.service.AuthorOperationsService;

import java.util.List;
import java.util.Optional;

@ShellComponent
public class AppShellControllerAuthor {

    private final IOService ioService;

    private final ConversionService conversionService;

    private final AuthorOperationsService authorOperationsService;

    public AppShellControllerAuthor(AuthorOperationsService authorOperationsService,
                                    IOService ioService,
                                    ConversionService conversionService) {
        this.authorOperationsService = authorOperationsService;
        this.ioService = ioService;
        this.conversionService = conversionService;
    }

    @ShellMethod(value = "Cоздание нового автора", key = {"ac", "author creation"})
    public String askForAuthorCreation() {
        ioService.outputString("Введите <Имя автора книги> и нажмите Enter");
        String authorName = ioService.readString();
        Author author = new Author(0, authorName);
        Author createdAuthor = authorOperationsService.create(author);

        if (createdAuthor != null) {
            return conversionService.convert(createdAuthor, String.class);
        } else {
            return "Автор не создан((";
        }
    }

    @ShellMethod(value = "Просмотр автора по ID", key = {"as", "author search"})
    public String askForAuthorById(long id) {
        Optional<Author> author = authorOperationsService.getById(id);

        if (author.isEmpty()) {
            return "Автор не найден ((";
        } else {
            return conversionService.convert(author.get(), String.class);
        }
    }

    @ShellMethod(value = "Узнать количество авторов", key = {"aa", "author amount"})
    public String askForAuthorAmount() {
        long numberOfAuthors = authorOperationsService.countOfAll();
        String numberOfAuthorsString = String.format("Количество авторов = %d", numberOfAuthors);

        return numberOfAuthorsString;
    }

    @ShellMethod(value = "Показать всех авторов", key = {"al", "author list"})
    public void askForAllAuthors() {
        List<Author> listOfAuthors = authorOperationsService.getAll();
        for (Author author : listOfAuthors) {
            String authorString = conversionService.convert(author, String.class);
            ioService.outputString(authorString);
        }
    }

    @ShellMethod(value = "Просмотр автора по NAME", key = {"an", "author name"})
    public String askForAuthorByName() {
        ioService.outputString("Введите <Имя автора книги> и нажмите Enter");
        String authorName = ioService.readString();
        Optional<Author> author = authorOperationsService.getByName(authorName);

        if (author.isEmpty()) {
            return "Автор не найден ((";
        } else {
            return conversionService.convert(author.get(), String.class);
        }
    }

}
