package ar.ramos.girsugps.internal.user;

import ar.ramos.girsugps.internal.auth.AuthenticationService;
import ar.ramos.girsugps.internal.auth.RegisterRequest;
import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Slf4j
@Component
public class AdminUserInitializer implements CommandLineRunner {
    private final IUserRepository iUserRepository;

    private final AuthenticationService authService;
    private final UserService userService;
    private final String ADMIN_USERNAME;
    private final String ADMIN_PASSWORD;

    public AdminUserInitializer(
            AuthenticationService authService,
            UserService userService,
            @Value("${ADMIN_USERNAME}") String ADMIN_USERNAME,
            @Value("${ADMIN_PASSWORD}") String ADMIN_PASSWORD,
            IUserRepository iUserRepository) {
        this.authService = authService;
        this.userService = userService;
        this.ADMIN_USERNAME = ADMIN_USERNAME;
        this.ADMIN_PASSWORD = ADMIN_PASSWORD;
        this.iUserRepository = iUserRepository;
    }

    @Override
    public void run(String... args) {
        try {
            RegisterRequest registerRequest = new RegisterRequest(ADMIN_USERNAME, ADMIN_PASSWORD);
            authService.register(registerRequest);
            User user = (User) userService.loadUserByUsername(ADMIN_USERNAME);
            user.setRoles("ROLE_ADMIN");
            iUserRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            log.info("Admin user already exists");
        }
    }
}
