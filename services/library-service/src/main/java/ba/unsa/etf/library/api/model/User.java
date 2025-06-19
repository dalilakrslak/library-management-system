package ba.unsa.etf.library.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the user")
    private Long id;

    @Schema(description = "First name of the user")
    private String firstName;

    @Schema(description = "Last name of the user")
    private String lastName;

    @Schema(description = "Email address of the user")
    private String email;

    @Schema(description = "Phone number of the user")
    private String phone;

    @Schema(description = "Role ID")
    private Long roleId;

    @Schema(description = "Library ID")
    private Long libraryId;
}