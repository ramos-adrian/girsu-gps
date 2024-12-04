package ar.ramos.girsugps.internal.auth;

import ar.ramos.girsugps.internal.user.IUserRepository;
import ar.ramos.girsugps.internal.user.UserService;
import ar.ramos.girsugps.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IUserRepository userRepository;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse loginWebBrowser(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );
        UserDetails user = userService.loadUserByUsername(loginRequest.username());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse registerWebBrowser(RegisterRequest registerRequest) {
        UserDetails user = register(registerRequest);
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    public UserDetails register(RegisterRequest registerRequest) {
        String username = registerRequest.username();

        if (userService.loadUserByUsername(username) != null) {
            throw new RuntimeException("User already exists");
        }

        String rawPassword = registerRequest.password();
        if (rawPassword == null || rawPassword.isEmpty()) {
            rawPassword = RandomStringUtils.randomAlphanumeric(10);
        }
        String encodedPassword = this.passwordEncoder.encode(rawPassword);
        return userService.addUser(username, encodedPassword, "ROLE_USER");
    }
}