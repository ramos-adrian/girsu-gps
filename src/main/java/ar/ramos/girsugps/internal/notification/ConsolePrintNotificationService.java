package ar.ramos.girsugps.internal.notification;

import ar.ramos.girsugps.internal.user.IUserHomeRepository;
import ar.ramos.girsugps.internal.user.IUserRepository;
import ar.ramos.girsugps.internal.user.User;
import ar.ramos.girsugps.internal.user.UserHome;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Profile("dev")
public class ConsolePrintNotificationService implements INotificationService{

    private final IUserHomeRepository userHomeRepository;
    private final IUserRepository userRepository;

    public ConsolePrintNotificationService(IUserHomeRepository userHomeRepository, IUserRepository userRepository) {
        this.userHomeRepository = userHomeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void sendNotification(UserHome userHome, String message) {
        Optional<User> user = userRepository.findByHome(userHome);
        user.ifPresent(value -> System.out.println("Sending notification to user: " + value.getUsername() + " - " + message));
    }
}
