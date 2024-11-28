package ar.ramos.girsugps.telegrambot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;

@Component
@Profile("telegram")
public class LongPollingTelegramBot implements SpringLongPollingBot {
    private final String botToken;
    private final LongPollingUpdateConsumer updateConsumer;

    public LongPollingTelegramBot(
            @Value("${TELEGRAM_BOT_TOKEN}") String botToken,
            LongPollingConsumer updateConsumer
    ) {
        this.botToken = botToken;
        this.updateConsumer = updateConsumer;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return updateConsumer;
    }


}
