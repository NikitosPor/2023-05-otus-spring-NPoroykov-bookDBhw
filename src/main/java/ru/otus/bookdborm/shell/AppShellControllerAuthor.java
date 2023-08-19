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
                                    IOService ioService, ConversionService conversionService) {
        this.authorOperationsService = authorOperationsService;
        this.ioService = ioService;
        this.conversionService = conversionService;
    }

    @ShellMethod(value = "Cоздание нового автора в таблице AUTHORS", key = {"ac", "author creation"})
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

    @ShellMethod(value = "Просмотр автора в таблице AUTHORS по ID", key = {"as", "author search"})
    public String askForAuthorByName(long id) {
        Optional<Author> author = authorOperationsService.getById(id);
        return conversionService.convert(author, String.class);
    }

    @ShellMethod(value = "Узнать количество авторов в таблице AUTHORS", key = {"aa", "author amount"})
    public void askForAuthorAmount() {
        long numberOfAuthors = authorOperationsService.countOfAll();
        String numberOfAuthorsString = String.format("Количество авторов в таблице = %d", numberOfAuthors);
        ioService.outputString(numberOfAuthorsString);
    }

    @ShellMethod(value = "Показать всех авторов в таблице AUTHORS", key = {"al", "author list"})
    public void askForAllAuthors() {
        List<Author> listOfAuthors = authorOperationsService.getAll();
        for (Author author : listOfAuthors) {
            String authorString = conversionService.convert(author, String.class);
            ioService.outputString(authorString);
        }
    }

    @ShellMethod(value = "Просмотр автора в таблице AUTHORS по NAME", key = {"an", "author name"})
    public String askForAuthorByName() {
        ioService.outputString("Введите <Имя автора книги> и нажмите Enter");
        String authorName = ioService.readString();
        Optional<Author> author = authorOperationsService.getByName(authorName);
        return conversionService.convert(author, String.class);
    }

}
