package ru.otus.bookdborm.shell;

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

    private final CommentOperationsService commentOperationsService;

    private final BookOperationsService bookOperationsService;

    public AppShellControllerComment(IOService ioService,
                                     CommentOperationsService commentOperationsService,
                                     BookOperationsService bookOperationsService) {
        this.ioService = ioService;
        this.commentOperationsService = commentOperationsService;
        this.bookOperationsService = bookOperationsService;
    }


    @ShellMethod(value = "Cоздание комментария в таблице COMMENTS", key = {"cc", "comment creation"})
    public void askForCommentCreation() {
        long bookId;
        boolean isBookExist;
        do {
            ioService.outputString("Введите <ID_Книги> и нажмите Enter");
            bookId = Long.parseLong(ioService.readString());
            isBookExist = bookOperationsService.getById(bookId).isPresent();
            if (!isBookExist) {
                ioService.outputString("Книги с таким ID не существует");
            }
        } while (!isBookExist);
        ioService.outputString("Введите комментарий к книге");
        String commentLine = ioService.readString();
        Comment comment = new Comment(0, bookOperationsService.getById(bookId).get(), commentLine);
        Comment createdComment = commentOperationsService.create(comment);
        String commentString = String.format("Создан комментарий: %s, c ID: %d", comment.getComment(), comment.getId());
        ioService.outputString(commentString);
    }

    @ShellMethod(value = "Обновление комментария в таблице Comments по ID", key = {"cu", "comment update"})
    public void askForCommentUpdate() {
        long commentId;
        boolean isCommentExist;
        do {
            ioService.outputString("Введите <ID_Комментария> и нажмите Enter");
            commentId = Long.parseLong(ioService.readString());
            isCommentExist = commentOperationsService.getById(commentId).isPresent();
            if (!isCommentExist) {
                ioService.outputString("Комментария с таким ID не существует");
            }
        } while (!isCommentExist);
        ioService.outputString("Введите новый комментарий к книге");
        String commentLine = ioService.readString();
        commentOperationsService.updateById(commentId, commentLine);
        String commentString = String.format("Комментарий c ID: %d обновлен", commentId);
    }

    @ShellMethod(value = "Удаление комментария в таблице Comments по ID", key = {"cd", "comment deletion"})
    public void askForCommentDeletion(long id) {
        commentOperationsService.deleteById(id);
        String commentIdString = String.format("Комментарий c ID: %d удален", id);
        ioService.outputString(commentIdString);
    }

    @ShellMethod(value = "Просмотр комментария в таблице Comments по ID", key = {"cs", "comment search"})
    public void askForCommentById(long id) {
        Optional<Comment> comment = commentOperationsService.getById(id);
        boolean isCommentExist = comment.isPresent();
        if (!isCommentExist) {
            ioService.outputString("Комментария с таким ID не существует");
        } else {
            String commentString = String.format("Комментарий: %s, c ID: %d",
                    comment.get().getComment(), comment.get().getId());
            ioService.outputString(commentString);
        }
    }

    @ShellMethod(value = "Просмотр списка комментариев в таблице Comments по ID_Книги", key = {"cl", "comment list"})
    public void askForCommentsByBookId(long bookId) {
        List<Comment> commentList = commentOperationsService.getListByBookId(bookId);
        ioService.outputString("Список комментариев книги");
        for (Comment comment : commentList) {
            String commentString = String.format("Комментарий: %s, c ID: %d", comment.getComment(), comment.getId());
            ioService.outputString(commentString);
        }
    }

}
