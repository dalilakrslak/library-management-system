package ba.unsa.etf.book.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationWithUser {
    private Reservation reservation;
    private User user;
}
