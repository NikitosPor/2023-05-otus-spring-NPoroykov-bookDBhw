package ru.otus.bookdborm.helpers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.bookdborm.domain.Author;

@Component
public class AuthorConverter implements Converter<Author, String> {

    @Override
    public String convert(Author author) {
        String resultString;

        resultString = String.format("Автор ID: %d, Имя: %s", author.getId(), author.getName());
        return resultString;
    }
}
