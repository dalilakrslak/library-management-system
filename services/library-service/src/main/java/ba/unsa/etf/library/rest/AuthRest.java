package ba.unsa.etf.library.rest;

import ba.unsa.etf.library.api.model.JwtResponse;
import ba.unsa.etf.library.api.model.LoginRequest;
import ba.unsa.etf.library.api.model.RefreshTokenRequest;
import ba.unsa.etf.library.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthRest {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String email = request.getEmail();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        String token = jwtService.generateToken(email, role);
        String refreshToken = jwtService.generateRefreshToken(email);

        return new JwtResponse(token, refreshToken);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody RefreshTokenRequest request) {
        if (!jwtService.isTokenValid(request.getRefreshToken())) {
            throw new RuntimeException("Invalid refresh token");
        }

        String email = jwtService.extractUsername(request.getRefreshToken());
        String role = jwtService.extractRole(request.getRefreshToken());
        String newToken = jwtService.generateToken(email, role);

        return new JwtResponse(newToken, request.getRefreshToken());
    }

}
