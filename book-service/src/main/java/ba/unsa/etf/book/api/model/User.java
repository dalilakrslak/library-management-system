package ba.unsa.etf.book.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the user")
    @JsonIgnore
    private Long id;

    @Schema(description = "First name of the user")
    private String firstName;

    @Schema(description = "Last name of the user")
    private String lastName;

    @Schema(description = "Email of the user")
    private String email;

    @Schema(description = "Password of the user")
    private String password;

    @Schema(description = "Phone number of the user")
    private String phone;
}
