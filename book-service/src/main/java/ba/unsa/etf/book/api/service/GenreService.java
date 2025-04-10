package ba.unsa.etf.book.api.service;

import ba.unsa.etf.book.api.model.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenreService {
    List<Genre> findAll();

    Genre findById(Long id);

    Genre create(Genre genre);

    Genre update(Genre genre);

    void delete(Long id);

    Page<Genre> getAllGenres(Pageable pageable);

    List<Genre> createBatch(List<Genre> genres);

    List<Genre> findByName(String name);
}
