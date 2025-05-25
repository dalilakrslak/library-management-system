package ba.unsa.etf.library.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Library implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the library")
    @JsonIgnore
    private Long id;

    @Schema(description = "Name of the library")
    private String name;

    @Schema(description = "Address of the library")
    private String address;

    @Schema(description = "Contact information of the library")
    private String contact;
}