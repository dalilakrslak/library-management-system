package ba.unsa.etf.book.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Loan implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the author")
    @JsonIgnore
    private Long id;

    @Schema(description = "User ID")
    @JsonIgnore
    private Long userId;

    @Schema(description = "ISBN")
    private String bookVersion;

    @Schema(description = "Loan date")
    private LocalDate loanDate;

    @Schema(description = "Due date")
    private LocalDate dueDate;

    @Schema(description = "Date when the book is returned")
    private LocalDate returnDate;
}
