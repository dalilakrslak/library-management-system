package ba.unsa.etf.transfer.core.validation;

import ba.unsa.etf.transfer.core.exception.ValidationException;
import ba.unsa.etf.transfer.dao.repository.LibraryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component("libraryValidation")
public class LibraryValidation {
    private final LibraryRepository libraryRepository;

    public void exists (Long id) {
        if (!libraryRepository.existsById(id)) {
            throw new ValidationException("Library with given ID does not exist!");
        }
    }
}
