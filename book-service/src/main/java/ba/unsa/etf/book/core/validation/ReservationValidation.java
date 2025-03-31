package ba.unsa.etf.book.core.validation;

import ba.unsa.etf.book.core.exception.ValidationException;
import ba.unsa.etf.book.dao.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component("reservationValidation")
public class ReservationValidation {
    private final ReservationRepository reservationRepository;

    public void exists (Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ValidationException("Reservation with given ID does not exist!");
        }
    }
}
