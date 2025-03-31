package ba.unsa.etf.book.core.validation;

import ba.unsa.etf.book.core.exception.ValidationException;
import ba.unsa.etf.book.dao.repository.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component("genreValidation")
public class GenreValidation {
    private final GenreRepository genreRepository;

    public void exists (Long id) {
        if (!genreRepository.existsById(id)) {
            throw new ValidationException("Genre with given ID does not exist!");
        }
    }
}
