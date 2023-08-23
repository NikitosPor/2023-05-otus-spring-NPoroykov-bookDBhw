package ru.otus.bookdborm.config.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.bookdborm.domain.Book;

@Component
public class BookConverter implements Converter<Book, String> {

    @Override
    public String convert(Book book) {
        String resultString;

        resultString = String.format("Книга создана ID: %d, Название: %s, Автор: %s, Жанр: %s",
                book.getId(), book.getTitle(), book.getAuthor().getName(), book.getGenre().getTitle());
        return resultString;
    }
}
