package ru.otus.bookdborm.config.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.bookdborm.domain.Comment;

@Component
public class CommentConverter implements Converter<Comment, String> {

    @Override
    public String convert(Comment comment) {
        String resultString;

        resultString = String.format("Комментарий: %s, c ID: %d",
                comment.getComment(), comment.getId());
        return resultString;
    }
}
