package ba.unsa.etf.book.api.model;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the reservation")
    private Long id;

    @Schema(description = "User ID")
    private Long userId;

    @Schema(description = "ISBN")
    private String bookVersion;

    @Schema(description = "Reservation date")
    private LocalDate reservationDate;
}
