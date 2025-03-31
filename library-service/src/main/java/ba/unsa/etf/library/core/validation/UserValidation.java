package ba.unsa.etf.library.core.validation;

import ba.unsa.etf.library.core.exception.ValidationException;
import ba.unsa.etf.library.dao.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@AllArgsConstructor
@Component("userValidation")
public class UserValidation {
    private final UserRepository userRepository;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public void exists (Long id) {
        if (!userRepository.existsById(id)) {
            throw new ValidationException("User with given ID does not exist!");
        }
    }

    public void validateEmail(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException("Invalid email format!");
        }
    }
}
