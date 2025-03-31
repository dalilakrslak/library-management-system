package ba.unsa.etf.transfer.core.validation;

import ba.unsa.etf.transfer.core.exception.ValidationException;
import ba.unsa.etf.transfer.dao.repository.TransferRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component("transferValidation")
public class TransferValidation {
    private final TransferRepository transferRepository;

    public void exists (Long id) {
        if (!transferRepository.existsById(id)) {
            throw new ValidationException("Transfer with given ID does not exist!");
        }
    }
}
