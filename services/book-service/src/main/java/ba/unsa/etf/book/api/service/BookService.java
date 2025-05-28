package ba.unsa.etf.book.api.service;

import ba.unsa.etf.book.api.model.Book;
import ba.unsa.etf.book.api.model.BookAvailability;
import ba.unsa.etf.book.api.model.BookVersion;
import ba.unsa.etf.book.dao.model.BookVersionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book findById(Long id);

    Book create(Book book);

    Book update(Book book);

    void delete(Long id);

    Page<Book> getAllBooks(Pageable pageable);

    List<Book> createBatch(List<Book> books);

    List<Book> findBooksByAuthor(String authorName);

    List<BookAvailability> getBookAvailability(Long bookId);

    List<BookVersion> getBookVersions(Long bookId);
}
