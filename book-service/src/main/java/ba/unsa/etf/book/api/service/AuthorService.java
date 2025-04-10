package ba.unsa.etf.book.api.service;

import ba.unsa.etf.book.api.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();

    Author findById(Long id);

    Author create(Author author);

    Author update(Author author);

    void delete(Long id);

    Page<Author> getAllAuthors(Pageable pageable);

    List<Author> createBatch(List<Author> authors);
}
