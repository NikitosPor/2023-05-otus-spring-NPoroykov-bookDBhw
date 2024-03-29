package ru.otus.bookdborm.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.bookdborm.domain.Book;
import ru.otus.bookdborm.helpers.IOService;
import ru.otus.bookdborm.service.BookOperationsService;

import java.util.List;
import java.util.Optional;

@ShellComponent
public class AppShellControllerBook {

    private final IOService ioService;

    private final BookOperationsService bookOperationsService;

    public AppShellControllerBook(BookOperationsService bookOperationsService,
                                  IOService ioService) {
        this.bookOperationsService = bookOperationsService;
        this.ioService = ioService;
    }


    @ShellMethod(value = "Cоздание книги в таблице BOOKS", key = {"bc", "book creation"})
    public void askForBookCreation() {
        ioService.outputString("Введите <Название книги> и нажмите Enter");
        String bookTitle = ioService.readString();
        ioService.outputString("Введите <Имя Автора книги> и нажмите Enter");
        String bookAuthor = ioService.readString();
        ioService.outputString("Введите <Жанр книги> и нажмите Enter");
        String bookGenre = ioService.readString();

        Book createdBook = bookOperationsService.create(bookTitle, bookAuthor, bookGenre);

        String bookString = String.format("Книга создана ID: %d, Название: %s, Автор: %s, Жанр: %s",
                createdBook.getId(), createdBook.getTitle(), createdBook.getAuthor(), createdBook.getGenre());
        ioService.outputString(bookString);
    }

    @ShellMethod(value = "Удаление книги в таблице BOOKS по ID", key = {"bd", "book deletion"})
    public void askForBookDeletion(long id) {
        bookOperationsService.deleteById(id);
        String bookIdString = String.format("Книга c ID: %s удалена", id);
        ioService.outputString(bookIdString);
    }

    @ShellMethod(value = "Просмотр книги в таблице BOOKS по ID", key = {"bs", "book search"})
    public void askForBookById(long id) {
        Optional<Book> book = bookOperationsService.getById(id);

        book.ifPresentOrElse(
                (value) -> {
                    String bookString = String.format("Книга ID: %d, Название: %s, Автор: %s, Жанр: %s", value.getId(),
                            value.getTitle(), value.getAuthor().getName(), value.getGenre().getTitle());
                    ioService.outputString(bookString);
                },
                () -> {
                    ioService.outputString("Книга не найдена ((");
                }
        );
    }

    @ShellMethod(value = "Узнать количество книг в таблице BOOKS", key = {"ba", "book amount"})
    public void askForBookAmount() {
        long numberOfBooks = bookOperationsService.getNumberOfAll();
        String numberOfBooksString = String.format("Количество книг в таблице = %d", numberOfBooks);
        ioService.outputString(numberOfBooksString);
    }

    @ShellMethod(value = "Показать все книги в таблице BOOKS", key = {"bl", "book list"})
    public void askForAllBooks() {
        List<Book> listOfBooks = bookOperationsService.getAll();
        for (Book book : listOfBooks) {
            String bookString = String.format("Книга ID: %d, Название: %s, Автор: %s, Жанр: %s",
                    book.getId(), book.getTitle(), book.getAuthor().getName(), book.getGenre().getTitle());
            ioService.outputString(bookString);
        }
    }

    @ShellMethod(value = "Обновление книги в таблице BOOKS", key = {"bu", "book update"})
    public void updateBookById(long id) {
        Optional<Book> book = bookOperationsService.getById(id);
        book.ifPresentOrElse(
                (value) -> {
                    ioService.outputString("Введите новое <Название книги> и нажмите Enter");
                    String bookTitle = ioService.readString();
                    bookOperationsService.updateTitleById(id, bookTitle);
                },
                () -> {
                    ioService.outputString("Книга с таким id не найдена ((");
                }
        );
    }
}
