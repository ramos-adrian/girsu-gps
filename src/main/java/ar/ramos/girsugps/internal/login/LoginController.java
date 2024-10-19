package ar.ramos.girsugps.internal.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        try {
            request.login(loginRequest.username(), loginRequest.password());
            return ResponseEntity.ok().build();
        } catch (ServletException e) {
            return ResponseEntity.status(401).build();
        }

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        try {
            request.logout();
            return ResponseEntity.ok().build();
        } catch (ServletException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/userDetails")
    public ResponseEntity<LoginRequest> userDetails(HttpServletRequest request) {
        return ResponseEntity.ok(new LoginRequest(request.getRemoteUser(), null));
    }

    public record LoginRequest(String username, String password) {
    }

}
