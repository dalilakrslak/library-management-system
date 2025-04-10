package ba.unsa.etf.book.api.service;

import ba.unsa.etf.book.api.model.Book;
import ba.unsa.etf.book.api.model.BookVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookVersionService {
    List<BookVersion> findAll();

    BookVersion findById(String isbn);

    BookVersion create(BookVersion bookVersion);

    BookVersion update(BookVersion bookVersion);

    void delete(String isbn);

    Page<BookVersion> getAllBooks(Pageable pageable);

    List<BookVersion> createBatch(List<BookVersion> bookVersions);

    List<BookVersion> findBooksByTitle(String title);
}
