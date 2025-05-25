package ba.unsa.etf.transfer.core.validation;

import ba.unsa.etf.transfer.core.exception.ValidationException;
import ba.unsa.etf.transfer.dao.repository.BookVersionRepository;
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
