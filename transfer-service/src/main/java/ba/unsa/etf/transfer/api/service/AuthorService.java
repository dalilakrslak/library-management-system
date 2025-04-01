package ba.unsa.etf.transfer.api.service;

import ba.unsa.etf.transfer.api.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();

    Author findById(Long id);

    Author create(Author author);

    Author update(Author author);

    void delete(Long id);
}
