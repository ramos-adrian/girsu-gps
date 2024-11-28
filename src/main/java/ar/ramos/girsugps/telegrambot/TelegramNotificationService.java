package ar.ramos.girsugps.telegrambot;

import ar.ramos.girsugps.internal.notification.INotificationService;
import ar.ramos.girsugps.internal.user.IUserRepository;
import ar.ramos.girsugps.internal.user.User;
import ar.ramos.girsugps.internal.user.UserHome;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.Optional;

import static org.springframework.security.crypto.encrypt.Encryptors.text;

@Service
@Profile("telegram")
public class TelegramNotificationService implements INotificationService {
    private final TelegramClient telegramClient;
    private final IUserRepository userRepository;

    public TelegramNotificationService(
            LongPollingTelegramBot telegramBot, IUserRepository userRepository
    ) {
        this.userRepository = userRepository;
        telegramClient = new OkHttpTelegramClient(telegramBot.getBotToken());
    }

    @Override
    public void sendNotification(UserHome userHome, String message) {
        Optional<UserDetails> userOptional = userRepository.findByHome(userHome);
        if (userOptional.isEmpty()) {
            return;
        }
        UserDetails user = userOptional.get();

        System.out.println("Sending message to " + user.getUsername() + ": " + message);

        SendMessage sendMessage = SendMessage.builder()
                .chatId(user.getUsername())
                .text(message)
                .build();

        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println("Failed to send message to " + user.getUsername());
        }
    }
}
