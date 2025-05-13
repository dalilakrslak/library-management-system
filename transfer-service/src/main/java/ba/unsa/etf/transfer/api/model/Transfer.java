package ba.unsa.etf.transfer.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Transfer {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the library")
    @JsonIgnore
    private Long id;

    @Schema(description = "Book Version ISBN")
    private Long bookVersionISBN;

    @Schema(description = "ID of library that gives books")
    private Long libraryFrom;

    @Schema(description = "ID of library that takes books")
    private Long libraryTo;

    @Schema(description = "Date of transfer")
    private LocalDate transferDate;
}
