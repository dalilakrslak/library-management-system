package ba.unsa.etf.book.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class Genre implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the genre")
    @JsonIgnore
    private Long id;

    @Schema(description = "Name of the genre")
    private String name;
}
