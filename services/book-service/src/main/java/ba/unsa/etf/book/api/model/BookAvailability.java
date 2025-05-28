package ba.unsa.etf.book.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class BookAvailability implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Library ID")
    private Long libraryId;

    @Schema(description = "Library name")
    private String libraryName;

    @Schema(description = "Number of available copies in the library")
    private Long availableCount;

    @Schema(description = "Number of reserved copies in the library")
    private Long reservedCount;

    @Schema(description = "Number of checked out copies in the library")
    private Long checkedOutCount;

    @Schema(description = "Number of copies in transfer from the library")
    private Long transferCount;

    @Schema(description = "Total number of copies of the book in the library")
    private Long totalCount;

    public BookAvailability() {
        this.totalCount = 0L;
        this.availableCount = 0L;
        this.reservedCount = 0L;
        this.checkedOutCount = 0L;
        this.transferCount = 0L;
    }

}
