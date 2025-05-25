package ba.unsa.etf.library.core.validation;

import ba.unsa.etf.library.core.exception.ValidationException;
import ba.unsa.etf.library.dao.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component("roleValidation")
public class RoleValidation {
    private final RoleRepository roleRepository;

    public void exists (Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ValidationException("Role with given ID does not exist!");
        }
    }
}
