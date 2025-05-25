package ba.unsa.etf.library.core.validation;

import ba.unsa.etf.library.core.exception.ValidationException;
import ba.unsa.etf.library.dao.repository.LibraryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component("libraryValidation")
public class LibraryValidation {
    private final LibraryRepository libraryRepository;

    public void exists(Long id) {
        if (!libraryRepository.existsById(id)) {
            throw new ValidationException("Library with the given ID does not exist!");
        }
    }
}
