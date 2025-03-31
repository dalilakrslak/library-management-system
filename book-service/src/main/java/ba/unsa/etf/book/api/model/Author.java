package ba.unsa.etf.book.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Author implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the author")
    @JsonIgnore
    private Long id;

    @Schema(description = "First name of the author")
    private String firstName;

    @Schema(description = "Last name of the author")
    private String lastName;

    @Schema(description = "Biography of the author")
    private String biography;
}
