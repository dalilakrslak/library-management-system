package ba.unsa.etf.book.core.validation;

import ba.unsa.etf.book.core.exception.ValidationException;
import ba.unsa.etf.book.dao.repository.GenreRepository;
import ba.unsa.etf.book.dao.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component("loanValidation")
public class LoanValidation {
    private final LoanRepository loanRepository;

    public void exists (Long id) {
        if (!loanRepository.existsById(id)) {
            throw new ValidationException("Loan with given ID does not exist!");
        }
    }
}
