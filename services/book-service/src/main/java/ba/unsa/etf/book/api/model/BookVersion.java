package ba.unsa.etf.book.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class BookVersion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "ISBN of the book")
    private String isbn;

    @Schema(description = "Is the book checked out")
    private Boolean isCheckedOut;

    @Schema(description = "Is the book reserved")
    private Boolean isReserved;

    @Schema(description = "Book ID")
    private Long bookId;

    @Schema(description = "Library ID")
    private Long libraryId;
}
