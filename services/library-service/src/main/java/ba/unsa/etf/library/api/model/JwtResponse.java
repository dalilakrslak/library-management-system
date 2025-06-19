package ba.unsa.etf.library.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String refreshToken;
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private Long libraryId;
    private String libraryName;
}
