package ba.unsa.etf.library.core.validation;

import ba.unsa.etf.library.core.exception.ValidationException;
import ba.unsa.etf.library.dao.repository.BookVersionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component("bookVersionValidation")
public class BookVersionValidation {
    private final BookVersionRepository bookVersionRepository;

    public void exists (String isbn) {
        if (!bookVersionRepository.existsById(isbn)) {
            throw new ValidationException("Book version with given ISBN does not exist!");
        }
    }
}
