package ba.unsa.etf.library.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class BookVersion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "International Standard Book Number")
    private String isbn;

    @Schema(description = "ID of the library that owns the book version")
    @JsonIgnore
    private Long libraryId;

    @Schema(description = "Indicates if the book has been returned")
    private Boolean returned;

    @Schema(description = "Indicates if the book is reserved")
    private Boolean reserved;
}