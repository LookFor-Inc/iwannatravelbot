package com.lookfor.iwannatravel.handlers.commands;

import com.lookfor.iwannatravel.handlers.CommandHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class GreetingCommand implements CommandHandler<SendMessage> {

    @Override
    public SendMessage doParse(Update update) {
        Message message = getReceivedMessage(update);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText(
                String.format("Hello, %s! My name is I Wanna Travel Bot!🤖", message.getFrom().getUserName())
        );
        return sendMessage;
    }
}
