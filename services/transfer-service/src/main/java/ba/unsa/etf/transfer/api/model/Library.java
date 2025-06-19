package ba.unsa.etf.transfer.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Library {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the library")
    private Long id;

    @Schema(description = "Name of the library")
    private String name;

    @Schema(description = "Address of the library")
    private String address;

    @Schema(description = "Contact of the library")
    private String contact;
}
