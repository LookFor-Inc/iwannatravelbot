package com.lookfor.iwannatravel.bot.handlers;

import com.lookfor.iwannatravel.interfaces.RootCommandHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class IncorrectCommandHandler implements RootCommandHandler<SendMessage> {
    @Override
    public SendMessage doParse(Update update) {
        Message message = getReceivedMessage(update);
        return SendMessage.builder()
                .chatId(String.valueOf(message.getChatId()))
                .parseMode(ParseMode.MARKDOWN)
                .text("""
                        I do not know this command😔
                                        
                        Here you can find all my *commands:*
                        /start - 👋
                        /help - 🆘
                        /from - 🛫
                        /to - 🛬
                        /info - ℹ️
                        /favorites - ⭐️
                        /remove - ❌
                        """
                )
                .build();
    }
}
