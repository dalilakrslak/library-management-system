package ba.unsa.etf.transfer.api.service;

import ba.unsa.etf.transfer.api.model.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findAll();

    Genre findById(Long id);

    Genre create(Genre genre);

    Genre update(Genre genre);

    void delete(Long id);
}
