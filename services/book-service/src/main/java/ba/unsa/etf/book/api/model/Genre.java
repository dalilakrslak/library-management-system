package ba.unsa.etf.book.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Genre implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the genre")
    private Long id;

    @Schema(description = "Name of the genre")
    private String name;
}
