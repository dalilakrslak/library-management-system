package ba.unsa.etf.library.core.validation;

import ba.unsa.etf.library.core.exception.ValidationException;
import ba.unsa.etf.library.dao.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component("userValidation")
public class UserValidation {
    private final UserRepository userRepository;

    public void exists (Long id) {
        if (!userRepository.existsById(id)) {
            throw new ValidationException("User with given ID does not exist!");
        }
    }
}
