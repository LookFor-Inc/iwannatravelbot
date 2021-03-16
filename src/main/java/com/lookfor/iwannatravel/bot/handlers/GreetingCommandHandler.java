package com.lookfor.iwannatravel.bot.handlers;

import com.lookfor.iwannatravel.interfaces.RootCommandHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class GreetingCommandHandler implements RootCommandHandler<SendMessage> {
    @Override
    public SendMessage doParse(Update update) {
        Message message = getReceivedMessage(update);
        return SendMessage.builder()
                .chatId(String.valueOf(message.getChatId()))
                .parseMode(ParseMode.MARKDOWN)
                .text(
                        String.format(
                                "Hello, %s! I am *I Wanna Travel Bot*!🤖\n\n", message.getFrom().getUserName())
                                + "I can help you to keep yourself up-to-date about travel restrictions on your favorite"
                                + "destination country notifying you of any new updates on travel restrictions that are being released💪🏼")
                .build();
    }
}
