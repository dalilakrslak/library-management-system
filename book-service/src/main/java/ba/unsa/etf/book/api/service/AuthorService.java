package ba.unsa.etf.book.api.service;

import ba.unsa.etf.book.api.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();

    Author findById(Long id);

    Author create(Author author);

    Author update(Author author);

    void delete(Long id);
}
