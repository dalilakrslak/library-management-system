package ba.unsa.etf.transfer.core.validation;

import ba.unsa.etf.transfer.core.exception.ValidationException;
import ba.unsa.etf.transfer.dao.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component("bookValidation")
public class BookValidation {
    private final BookRepository bookRepository;

    public void exists (Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ValidationException("Book with given ID does not exist!");
        }
    }
}
