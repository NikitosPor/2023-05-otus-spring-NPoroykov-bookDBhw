package ru.otus.bookdborm.shell;

import org.springframework.core.convert.ConversionService;
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

    private final ConversionService conversionService;

    private final BookOperationsService bookOperationsService;

    public AppShellControllerBook(BookOperationsService bookOperationsService,
                                  IOService ioService,
                                  ConversionService conversionService) {
        this.bookOperationsService = bookOperationsService;
        this.ioService = ioService;
        this.conversionService = conversionService;
    }


    @ShellMethod(value = "Cоздание книги", key = {"bc", "book creation"})
    public String askForBookCreation() {
        ioService.outputString("Введите <Название книги> и нажмите Enter");
        String bookTitle = ioService.readString();
        ioService.outputString("Введите <Имя Автора книги> и нажмите Enter");
        String bookAuthor = ioService.readString();
        ioService.outputString("Введите <Жанр книги> и нажмите Enter");
        String bookGenre = ioService.readString();

        Book createdBook = bookOperationsService.create(bookTitle, bookAuthor, bookGenre);

        return conversionService.convert(createdBook, String.class);
    }

    @ShellMethod(value = "Удаление книги по ID", key = {"bd", "book deletion"})
    public String askForBookDeletion(long id) {
        bookOperationsService.deleteById(id);
        String bookIdString = String.format("Книга c ID: %s удалена", id);

        return bookIdString;
    }

    @ShellMethod(value = "Просмотр книги по ID", key = {"bs", "book search"})
    public String askForBookById(long id) {
        Optional<Book> book = bookOperationsService.getById(id);

        if (book.isEmpty()) {
            return "Книга не найдена ((";
        } else {
            return conversionService.convert(book.get(), String.class);
        }
    }

    @ShellMethod(value = "Узнать количество книг", key = {"ba", "book amount"})
    public String askForBookAmount() {
        long numberOfBooks = bookOperationsService.getNumberOfAll();
        String numberOfBooksString = String.format("Количество книг в таблице = %d", numberOfBooks);

        return numberOfBooksString;
    }

    @ShellMethod(value = "Показать все книги", key = {"bl", "book list"})
    public void askForAllBooks() {
        List<Book> listOfBooks = bookOperationsService.getAll();
        for (Book book : listOfBooks) {
            String bookString = conversionService.convert(book, String.class);
            ioService.outputString(bookString);
        }
    }

    @ShellMethod(value = "Обновление книги", key = {"bu", "book update"})
    public String updateBookById(long id) {
        Optional<Book> book = bookOperationsService.getById(id);

        if (book.isEmpty()) {
            return "Книга не найдена ((";
        } else {
            return conversionService.convert(book.get(), String.class);
        }
    }
}
