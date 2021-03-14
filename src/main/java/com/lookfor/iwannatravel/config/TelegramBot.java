package com.lookfor.iwannatravel.config;

import com.lookfor.iwannatravel.bot.Command;
import com.lookfor.iwannatravel.bot.handlers.IncorrectCommandHandler;
import com.lookfor.iwannatravel.exceptions.CommandNotFoundException;
import com.lookfor.iwannatravel.interfaces.RootCommandHandler;
import com.lookfor.iwannatravel.parsers.TelegramMessageParser;
import com.lookfor.iwannatravel.services.CommandService;
import com.lookfor.iwannatravel.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${telegram.bot.username}")
    private String botUsername;
    @Value("${telegram.bot.token}")
    private String botToken;
    private final ApplicationContext appContext;

    private final UserService userService;
    private final CommandService commandService;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        boolean editedMessage = update.hasEditedMessage();
        if (!update.hasMessage() && !editedMessage) {
            return;
        }
        Message message = editedMessage ? update.getEditedMessage() : update.getMessage();
        String messageText = message.getText();

        if (messageText.isEmpty()) {
            return;
        }

        // Update user's info
        userService.saveUpdates(message);

        RootCommandHandler<?> handler;
        try {
            Command command = commandService.findCommandInMessage(messageText);
            handler = (RootCommandHandler<?>) appContext.getBean(command.getHandlerBeanName());
        } catch (CommandNotFoundException exp) {
            handler = appContext.getBean(IncorrectCommandHandler.class);
        }

        TelegramMessageParser parser =
                new TelegramMessageParser(this, update, handler);

        // Start thread for parsing sent message
        parser.start();
    }
}
