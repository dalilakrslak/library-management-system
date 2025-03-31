package ba.unsa.etf.book.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Loan implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the author")
    @JsonIgnore
    private Long id;

    @Schema(description = "User ID")
    private Long userId;

    @Schema(description = "ISBN")
    private String bookVersion;

    @Schema(description = "Loan date")
    private LocalDateTime loanDate;

    @Schema(description = "Due date")
    private LocalDateTime dueDate;

    @Schema(description = "Date when the book is returned")
    private LocalDateTime returnDate;
}
