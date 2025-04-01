package ba.unsa.etf.book.api.model;

import ba.unsa.etf.book.dao.model.BookVersionEntity;
import ba.unsa.etf.book.dao.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the reservation")
    @JsonIgnore
    private Long id;

    @Schema(description = "User ID")
    private Long userId;

    @Schema(description = "ISBN")
    private String bookVersion;

    @Schema(description = "Reservation date")
    private LocalDateTime reservationDate;
}
