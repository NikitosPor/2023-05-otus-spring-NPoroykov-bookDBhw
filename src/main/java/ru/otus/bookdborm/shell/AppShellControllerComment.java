package ru.otus.bookdborm.shell;

import org.springframework.core.convert.ConversionService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.bookdborm.domain.Comment;
import ru.otus.bookdborm.helpers.IOService;
import ru.otus.bookdborm.service.BookOperationsService;
import ru.otus.bookdborm.service.CommentOperationsService;

import java.util.List;
import java.util.Optional;


@ShellComponent
public class AppShellControllerComment {

    private final IOService ioService;

    private final ConversionService conversionService;

    private final CommentOperationsService commentOperationsService;

    private final BookOperationsService bookOperationsService;

    public AppShellControllerComment(IOService ioService,
                                     ConversionService conversionService,
                                     CommentOperationsService commentOperationsService,
                                     BookOperationsService bookOperationsService) {
        this.ioService = ioService;
        this.conversionService = conversionService;
        this.commentOperationsService = commentOperationsService;
        this.bookOperationsService = bookOperationsService;
    }


    @ShellMethod(value = "Cоздание комментария в таблице COMMENTS по id книги", key = {"cc", "comment creation"})
    public String askForCommentCreation(long bookId) {
        boolean isBookExist;
        isBookExist = bookOperationsService.getById(bookId).isPresent();
        if (!isBookExist) {
            return "Книги с таким ID не существует";
        } else {
            ioService.outputString("Введите комментарий к книге");
            String commentLine = ioService.readString();
            Comment comment = new Comment(0, bookOperationsService.getById(bookId).get(), commentLine);
            Comment createdComment = commentOperationsService.create(comment);

            return conversionService.convert(createdComment, String.class);
        }
    }

    @ShellMethod(value = "Обновление комментария в таблице Comments по ID", key = {"cu", "comment update"})
    public String askForCommentUpdate(long commentId) {
        boolean isCommentExist;
        commentId = Long.parseLong(ioService.readString());
        isCommentExist = commentOperationsService.getById(commentId).isPresent();
        if (!isCommentExist) {
            return "Комментария с таким ID не существует";
        } else {
            ioService.outputString("Введите новый комментарий к книге");
            String commentLine = ioService.readString();
            commentOperationsService.updateById(commentId, commentLine);

            return String.format("Комментарий c ID: %d обновлен", commentId);
        }
    }

    @ShellMethod(value = "Удаление комментария в таблице Comments по ID", key = {"cd", "comment deletion"})
    public String askForCommentDeletion(long id) {
        commentOperationsService.deleteById(id);
        String commentIdString = String.format("Комментарий c ID: %d удален", id);

        return commentIdString;
    }

    @ShellMethod(value = "Просмотр комментария в таблице Comments по ID", key = {"cs", "comment search"})
    public String askForCommentById(long id) {
        Optional<Comment> comment = commentOperationsService.getById(id);

        if (comment.isEmpty()) {
            return "Комментарий не найден ((";
        } else {
            return conversionService.convert(comment.get(), String.class);
        }
    }

    @ShellMethod(value = "Просмотр списка комментариев в таблице Comments по ID_Книги", key = {"cl", "comment list"})
    public void askForCommentsByBookId(long bookId) {
        List<Comment> commentList = commentOperationsService.getListByBookId(bookId);
        ioService.outputString("Список комментариев книги");
        for (Comment comment : commentList) {
            String commentString = conversionService.convert(comment, String.class);
            ioService.outputString(commentString);
        }
    }

}
