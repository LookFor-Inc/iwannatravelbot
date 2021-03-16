package com.lookfor.iwannatravel.bot;

import com.lookfor.iwannatravel.config.TelegramBot;
import com.lookfor.iwannatravel.interfaces.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collection;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class MessageSenderImp implements MessageSender {
    private final TelegramBot telegramBot;

    @Override
    public void sendToUser(Integer userId, String sourceText) throws TelegramApiException {
        telegramBot.execute(
                SendMessage.builder()
                .chatId(String.valueOf(userId))
                .text(sourceText)
                .build()
        );
    }

    @Override
    public void sendToUsers(Collection<Integer> usersId, String sourceText) throws TelegramApiException {
        for (Integer userId : usersId) {
            sendToUser(userId, sourceText);
        }
    }
}
