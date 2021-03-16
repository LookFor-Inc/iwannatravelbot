package com.lookfor.iwannatravel.bot.handlers;

import com.lookfor.iwannatravel.exceptions.CountryNotFoundException;
import com.lookfor.iwannatravel.exceptions.UserNotFoundException;
import com.lookfor.iwannatravel.interfaces.RootCommandHandler;
import com.lookfor.iwannatravel.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FavoritesCommandHandler implements RootCommandHandler<SendMessage> {
    private final UserService userService;

    @Override
    public SendMessage doParse(Update update) {
        Message message = getReceivedMessage(update);
        StringBuilder sbResponse = new StringBuilder();
        try {
            List<String> arrCountries = userService.fetchUserArrivalCountries(message.getFrom().getId());
            sbResponse.append("All your favorite countries:\n");
            arrCountries.forEach(ac -> sbResponse.append(ac).append("\n"));
        } catch (CountryNotFoundException | UserNotFoundException exp) {
            log.error(exp.getMessage());
            sbResponse.append(exp.getMessage());
        }
        return SendMessage.builder()
                .chatId(String.valueOf(message.getChatId()))
                .text(sbResponse.toString())
                .build();
    }
}
