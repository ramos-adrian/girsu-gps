package ar.ramos.girsugps.whatsappbot;

import ar.ramos.girsugps.internal.auth.AuthenticationService;
import ar.ramos.girsugps.internal.notification.INotificationService;
import ar.ramos.girsugps.internal.user.IUserRepository;
import ar.ramos.girsugps.internal.user.UserHome;
import com.whatsapp.api.domain.messages.Message;
import com.whatsapp.api.domain.messages.TextMessage;
import com.whatsapp.api.impl.WhatsappBusinessCloudApi;
import com.whatsapp.api.utils.Formatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Profile("whatsapp")
public class WhatsappNotificationService implements INotificationService {

    private final AuthenticationService authenticationService;
    private final IUserRepository userRepository;
    private final WhatsappBusinessCloudApi bot;
    private final String phoneId;

    public WhatsappNotificationService(
            AuthenticationService authenticationService,
            IUserRepository userRepository,
            WhatsappBusinessCloudApi bot,
            @Value("${WHATSAPP_PHONE_ID}")  String phoneId
    ) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
        this.bot = bot;
        this.phoneId = phoneId;
    }

    @Override
    public void sendNotification(UserHome userHome, String message) {
        Optional<UserDetails> userOptional = userRepository.findByHome(userHome);
        if (userOptional.isEmpty()) {
            return;
        }
        UserDetails user = userOptional.get();

        System.out.println("Sending message to " + user.getUsername() + ": " + message);

        Message wpMsg = Message.MessageBuilder.builder()
                .setTo(user.getUsername())
                .buildTextMessage(
                        new TextMessage()
                                .setBody(Formatter.bold("Hello world!") + "\nSome code here: \n" + Formatter.code("hello world code here"))
                                .setPreviewUrl(false)
                );

        bot.sendMessage(phoneId, wpMsg);
    }
}
