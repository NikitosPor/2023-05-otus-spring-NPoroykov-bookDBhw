package ru.otus.bookdborm.shell;

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

    private final AuthorOperationsService authorOperationsService;

    public AppShellControllerAuthor(AuthorOperationsService authorOperationsService,
                                    IOService ioService) {
        this.authorOperationsService = authorOperationsService;
        this.ioService = ioService;
    }

    @ShellMethod(value = "Cоздание нового автора в таблице AUTHORS", key = {"ac", "author creation"})
    public void askForAuthorCreation() {
        ioService.outputString("Введите <Имя автора книги> и нажмите Enter");
        String authorName = ioService.readString();
        Author author = new Author(0, authorName);
        Author createdAuthor = authorOperationsService.create(author);

        if (createdAuthor != null) {
            String authorString = String.format("Создан автор ID: %d, Имя: %s",
                    createdAuthor.getId(), createdAuthor.getName());
            ioService.outputString(authorString);
        }
    }

    @ShellMethod(value = "Просмотр автора в таблице AUTHORS по ID", key = {"as", "author search"})
    public void askForAuthorById(long id) {
        Optional<Author> author = authorOperationsService.getById(id);
        if (author.isEmpty()) {
            ioService.outputString("Автор не найден ((");
        } else {
            String authorString = String.format("Автор ID: %d, Имя: %s", author.get().getId(), author.get().getName());
            ioService.outputString(authorString);
        }
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
            String authorString = String.format("Автор ID: %d, Имя: %s", author.getId(), author.getName());
            ioService.outputString(authorString);
        }
    }

    @ShellMethod(value = "Просмотр автора в таблице AUTHORS по NAME", key = {"an", "author name"})
    public void askForAuthorById() {
        ioService.outputString("Введите <Имя автора книги> и нажмите Enter");
        String authorName = ioService.readString();
        Optional<Author> author = authorOperationsService.getByName(authorName);
        if (author.isEmpty()) {
            ioService.outputString("Автор не найден ((");
        } else {
            String authorString = String.format("Автор ID: %d, Имя: %s", author.get().getId(), author.get().getName());
            ioService.outputString(authorString);
        }
    }

}
