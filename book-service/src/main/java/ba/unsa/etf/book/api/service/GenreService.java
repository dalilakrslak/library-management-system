package ba.unsa.etf.book.api.service;

import ba.unsa.etf.book.api.model.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findAll();

    Genre findById(Long id);

    Genre create(Genre genre);

    Genre update(Genre genre);

    void delete(Long id);
}
