package ar.ramos.girsugps.telegrambot;

import ar.ramos.girsugps.internal.place.PlaceService;
import ar.ramos.girsugps.internal.user.UserHome;
import ar.ramos.girsugps.internal.user.UserService;
import com.google.maps.model.LatLng;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
@Profile("telegram")
public class LongPollingConsumer implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;
    private final UserService userService;
    private final PlaceService placeService;

    public LongPollingConsumer(@Value("${TELEGRAM_BOT_TOKEN}") String botToken,
                               UserService userService,
                               PlaceService placeService) {
        this.telegramClient = new OkHttpTelegramClient(botToken);
        this.userService = userService;
        this.placeService = placeService;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasLocation()) updateUserHomeLocation(update);
    }

    private void updateUserHomeLocation(Update update) {
        double latitude = update.getMessage().getLocation().getLatitude();
        double longitude = update.getMessage().getLocation().getLongitude();
        String chatId = update.getMessage().getChatId().toString();
        // TODO Secure this
        String rawPassword = update.getMessage().getChatId().toString();

        UserDetails user = userService.loadUserByUsername(chatId);
        if (user == null) user = userService.addUser(chatId, rawPassword, "ROLE_USER");

        String placeId = placeService.getPlaceIdFromLatLng(new LatLng(latitude, longitude));

        UserHome userHome = new UserHome();
        userHome.setLatitude(latitude);
        userHome.setLongitude(longitude);
        userHome.setPlaceId(placeId);

        userService.setHome(chatId, userHome);

        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("Ubicación guardada")
                .build();

        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
