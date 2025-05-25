package ba.unsa.etf.library.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the role")
    @JsonIgnore
    private Long id;

    @Schema(description = "Name of the role")
    private String name;
}