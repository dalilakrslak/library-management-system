package ba.unsa.etf.transfer.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class BookVersion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "ISBN of the book")
    private String isbn;

    @Schema(description = "Book ID")
    private Long bookId;

    @Schema(description = "Library ID")
    private Long libraryId;

    @Schema(description = "Is the book returned")
    private Boolean returned;

    @Schema(description = "Is the book reserved")
    private Boolean reserved;
}
