package ba.unsa.etf.book.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ReservationWithUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Reservation object")
    private Reservation reservation;

    @Schema(description = "User object")
    private User user;
}
