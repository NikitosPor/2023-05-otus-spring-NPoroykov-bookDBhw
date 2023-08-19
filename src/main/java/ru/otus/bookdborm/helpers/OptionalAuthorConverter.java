package ru.otus.bookdborm.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.bookdborm.domain.Author;

import java.util.Optional;

@Component
public class OptionalAuthorConverter implements Converter<Optional<Author>, String> {

    @Override
    public String convert(Optional<Author> author) {
        String resultString;

        if (author.isEmpty()) resultString = "Автор не найден ((";
        else {
            resultString = String.format("Автор ID: %d, Имя: %s", author.get().getId(), author.get().getName());
        }
        return resultString;
    }
}
