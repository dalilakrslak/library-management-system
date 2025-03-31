package ba.unsa.etf.transfer.api.service;

import ba.unsa.etf.transfer.api.model.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book findById(Long id);

    Book create(Book book);

    Book update(Book book);

    void delete(Long id);
}
