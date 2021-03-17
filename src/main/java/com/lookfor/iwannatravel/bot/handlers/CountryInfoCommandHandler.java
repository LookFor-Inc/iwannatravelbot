package com.lookfor.iwannatravel.bot.handlers;

import com.lookfor.iwannatravel.interfaces.RootCommandHandler;
import com.lookfor.iwannatravel.services.ParseScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.ExecutionException;

import static com.lookfor.iwannatravel.utils.TextMessageUtil.getRestOfTextMessageWithoutCommand;

@Slf4j
@Component
@RequiredArgsConstructor
public class CountryInfoCommandHandler implements RootCommandHandler<SendMessage> {
    private final ParseScheduler parseScheduler;

    @Override
    public SendMessage doParse(Update update) throws ExecutionException, InterruptedException, TelegramApiException {
        Message message = getReceivedMessage(update);
        int userId = message.getFrom().getId();
        String restOfTextMessage = getRestOfTextMessageWithoutCommand(message.getText());

        if (restOfTextMessage.isEmpty()) {
            return SendMessage.builder()
                    .chatId(String.valueOf(message.getChatId()))
                    .parseMode(ParseMode.MARKDOWN)
                    // TODO
                    .text("‼️*Empty country*‼️")
                    .build();
        }

        parseScheduler.sendInfoAboutCountry(userId, restOfTextMessage);
        return null;
    }
}
