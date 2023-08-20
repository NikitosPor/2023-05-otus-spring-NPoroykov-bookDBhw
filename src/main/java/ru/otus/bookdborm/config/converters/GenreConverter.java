package ru.otus.bookdborm.config.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.bookdborm.domain.Genre;

@Component
public class GenreConverter implements Converter<Genre, String> {

    @Override
    public String convert(Genre genre) {
        String resultString;

        resultString = String.format("Жанр ID: %d, Название: %s", genre.getId(), genre.getTitle());
        return resultString;
    }

}
