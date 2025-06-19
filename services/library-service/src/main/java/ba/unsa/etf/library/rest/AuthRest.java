package ba.unsa.etf.library.rest;

import ba.unsa.etf.library.api.model.JwtResponse;
import ba.unsa.etf.library.api.model.LoginRequest;
import ba.unsa.etf.library.api.model.RefreshTokenRequest;
import ba.unsa.etf.library.dao.model.UserEntity;
import ba.unsa.etf.library.dao.repository.UserRepository;
import ba.unsa.etf.security_core.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthRest {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String email = request.getEmail();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        String token = jwtService.generateToken(email, role);
        String refreshToken = jwtService.generateRefreshToken(email);

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long libraryId = user.getLibrary() != null ? user.getLibrary().getId() : null;
        String libraryName = user.getLibrary() != null ? user.getLibrary().getName() : null;

        return new JwtResponse(token, refreshToken, user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole().getName(), libraryId, libraryName);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody RefreshTokenRequest request) {
        if (!jwtService.isTokenValid(request.getRefreshToken())) {
            throw new RuntimeException("Invalid refresh token");
        }

        String email = jwtService.extractUsername(request.getRefreshToken());
        String role = jwtService.extractRole(request.getRefreshToken());
        String newToken = jwtService.generateToken(email, role);

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new JwtResponse(
                newToken,
                request.getRefreshToken(),
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole().getName(),
                user.getLibrary() != null ? user.getLibrary().getId() : null,
                user.getLibrary() != null ? user.getLibrary().getName() : null
        );

    }

}
