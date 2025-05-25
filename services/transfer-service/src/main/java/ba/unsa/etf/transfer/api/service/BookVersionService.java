package ba.unsa.etf.transfer.api.service;

import ba.unsa.etf.transfer.api.model.BookVersion;

import java.util.List;

public interface BookVersionService {
    List<BookVersion> findAll();

    BookVersion findById(String isbn);

    BookVersion create(BookVersion bookVersion);

    BookVersion update(BookVersion bookVersion);

    void delete(String isbn);
}