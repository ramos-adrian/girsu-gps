package ar.ramos.girsugps.internal.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("dev")
@Component
public class AdminUserInitializer implements CommandLineRunner {
    private final JpaUserDetailsService userService;

    private final String ADMIN_USERNAME;
    private final String ADMIN_PASSWORD;

    public AdminUserInitializer(
            JpaUserDetailsService userService,
            @Value("${ADMIN_USERNAME}") String ADMIN_USERNAME,
            @Value("${ADMIN_PASSWORD}") String ADMIN_PASSWORD) {
        this.userService = userService;
        this.ADMIN_USERNAME = ADMIN_USERNAME;
        this.ADMIN_PASSWORD = ADMIN_PASSWORD;
    }

    @Override
    public void run(String... args) {
        try {
            userService.addUser(ADMIN_USERNAME, ADMIN_PASSWORD, "ROLE_ADMIN");
        } catch (DataIntegrityViolationException e) {
            log.info("Admin user already exists");
        }
    }
}
