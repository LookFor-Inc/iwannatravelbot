package com.lookfor.iwannatravel.config;

import com.lookfor.iwannatravel.handlers.CommandHandler;
import com.lookfor.iwannatravel.handlers.commands.GreetingCommand;
import com.lookfor.iwannatravel.models.User;
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
        Message message = update.getMessage();
        String messageText = message.getText();
        // TODO: check for edited messages
        if (messageText == null || messageText.equals("")) {
            return;
        }

        // Get existing user or create a new one
        User user = checkUserInfo(message);

        // TODO: detect the command that was sent via command handlers
        log.info(String.format(
                "From @%s (%s): '%s'", user.getUsername(), user.getTelegramUserId(), messageText)
        );

        // TODO: remove hardcode with GreetingCommand bean
        CommandHandler<?> commandHandler = appContext.getBean(GreetingCommand.class);
        TelegramMessageParser parser = new TelegramMessageParser(this, update, commandHandler);

        // Start thread for parsing sent message
        parser.start();
    }

    private User checkUserInfo(Message message) {
        String username = message.getFrom().getUserName();
        if (username == null) {
            username = message.getFrom().getFirstName();
        }

        Integer userId = message.getFrom().getId();
        User user = userService.fetchByTelegramUserId(userId);

        if (user == null) {
            user = User.builder()
                    .telegramUserId(userId)
                    .username(username)
                    .countries(Collections.emptySet())
                    .build();
            userService.save(user);
        }

        return user;
    }
}
