package com.lookfor.iwannatravel.config;

import com.lookfor.iwannatravel.handlers.CommandHandler;
import com.lookfor.iwannatravel.handlers.commands.GreetingCommand;
import com.lookfor.iwannatravel.parsers.TelegramMessageParser;
import com.lookfor.iwannatravel.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Collections;

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
        User user = message.getFrom();
        String messageText = message.getText();

        if (messageText == null || messageText.equals("")) {
            return;
        }

        // Update user's info
        userService.saveUpdates(message);

        log.info(String.format(
                "From @%s (%s): '%s'", user.getUserName(), user.getId(), messageText)
        );
        // TODO: detect the command that was sent via command handlers

        // TODO: remove hardcode with GreetingCommand bean
        CommandHandler<?> commandHandler = appContext.getBean(GreetingCommand.class);
        TelegramMessageParser parser =
                new TelegramMessageParser(this, update, commandHandler);

        // Start thread for parsing sent message
        parser.start();
    }
}
