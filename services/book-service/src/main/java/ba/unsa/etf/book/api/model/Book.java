package ba.unsa.etf.book.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the book")
    @JsonIgnore
    private Long id;

    @Schema(description = "Title of the book")
    private String title;

    @Schema(description = "Description of the book")
    private String description;

    @Schema(description = "Number of pages of the book")
    private int pageCount;

    @Schema(description = "Year of the book's publication")
    private int publicationYear;

    @Schema(description = "Language of the book")
    private String language;

    @Schema(description = "Unique identifier of the author of the book")
    private Long authorId;

    @Schema(description = "Unique identifier of the genre of the book")
    private Long genreId;
}
