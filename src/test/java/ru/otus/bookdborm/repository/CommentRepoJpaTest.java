package ru.otus.bookdborm.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.bookdborm.domain.Book;
import ru.otus.bookdborm.domain.Comment;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями ")
@DataJpaTest
class CommentRepoJpaTest {

    private static final int EXPECTED_NUMBER_OF_COMMENTS = 4;

    private static final long SECOND_BOOK_ID = 2L;

    private static final long FIRST_COMMENT_ID = 1L;

    private static final String COMMENT_TITLE = "The greatest poem ever appeared in the world!";

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен показывать количество комментариев в базе данных")
    @Test
    void countTest() {
        var commentCount = commentRepo.count();
        assertThat(commentCount).isEqualTo(EXPECTED_NUMBER_OF_COMMENTS);
    }

    @DisplayName(" должен добавлять комментарий в базу данных")
    @Test
    void saveTest() {
        var bookForComment = em.find(Book.class, SECOND_BOOK_ID);
        var expectedComment = new Comment(0L, bookForComment, COMMENT_TITLE);
        Comment comment = commentRepo.save(expectedComment);
        var actualComment = em.find(Comment.class, comment.getId());
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName(" должен загружать информацию о нужной книге по её id из базы данных")
    @Test
    void getByIdTest() {
        val optionalActualComment = commentRepo.findById(FIRST_COMMENT_ID);
        val expectedComment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(optionalActualComment).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName(" должен получать все комментарии по id книги из базы данных")
    @Test
    void getListByBookIdTest() {
        val comments = commentRepo.findAllByBookId(SECOND_BOOK_ID);
        assertThat(comments).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENTS)
                .allMatch(c -> !Objects.equals(c.getComment(), "") && c.getComment().getBytes().length > 0);
    }

    @DisplayName(" должен удалять комментарий из базы данных")
    @Test
    void deleteByIdTest() {
        assertThatCode(() -> em.find(Comment.class, FIRST_COMMENT_ID)).doesNotThrowAnyException();
        commentRepo.deleteById(FIRST_COMMENT_ID);
        Comment actualComment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(actualComment).isNull();
    }

    @DisplayName(" должен обновлять комментарий в базе данных")
    @Test
    void updateByIdTest() {
        commentRepo.updateById(FIRST_COMMENT_ID, COMMENT_TITLE);
        Comment actualComment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(actualComment.getComment()).isEqualTo(COMMENT_TITLE);
    }
}