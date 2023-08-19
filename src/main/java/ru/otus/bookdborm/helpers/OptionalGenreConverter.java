package ru.otus.bookdborm.helpers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.bookdborm.domain.Genre;

import java.util.Optional;

@Component
public class OptionalGenreConverter implements Converter<Optional<Genre>, String> {

    @Override
    public String convert(Optional<Genre> genre) {
        String resultString;

        if (genre.isEmpty()) resultString = "Жанр не найден ((";
        else {
            resultString = String.format("Жанр ID: %d, Название: %s", genre.get().getId(), genre.get().getTitle());
        }
        return resultString;
    }

}
