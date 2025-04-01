package ba.unsa.etf.transfer.core.validation;

import ba.unsa.etf.transfer.api.model.Author;
import ba.unsa.etf.transfer.core.exception.ValidationException;
import ba.unsa.etf.transfer.dao.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component("authorValidation")
public class AuthorValidation {
    private final AuthorRepository authorRepository;

    public void validateCreate(Author author) {
        validateName(author);

        if (authorRepository.existsByFirstNameAndLastName(author.getFirstName(), author.getLastName())) {
            throw new ValidationException("An author with the same name already exists!");
        }
    }

    public void validateUpdate(Author author) {
        exists(author.getId());
        validateName(author);
    }

    private void validateName(Author author) {
        if (author.getFirstName().isEmpty() || author.getLastName().isEmpty()) {
            throw new ValidationException("First name or last name can't be empty!");
        }
    }

    public void exists (Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ValidationException("Author with given ID does not exist!");
        }
    }
}
