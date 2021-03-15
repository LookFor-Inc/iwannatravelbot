package com.lookfor.iwannatravel.bot.handlers;

import com.lookfor.iwannatravel.exceptions.CountryNotFoundException;
import com.lookfor.iwannatravel.interfaces.RootCommandHandler;
import com.lookfor.iwannatravel.models.Country;
import com.lookfor.iwannatravel.services.CountryService;
import com.lookfor.iwannatravel.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.lookfor.iwannatravel.utils.TextMessageUtil.getRestOfTextMessageWithoutCommand;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCountryCommandHandler implements RootCommandHandler<SendMessage> {
    private final UserService userService;
    private final CountryService countryService;

    @Override
    public SendMessage doParse(Update update) {
        Message message = getReceivedMessage(update);
        String restOfTextMessage = getRestOfTextMessageWithoutCommand(message.getText());
        // TODO: delete all trajectories when save country
        Country country;
        String responseMessage;
        try {
            country = countryService.getCountryByName(restOfTextMessage.toLowerCase());
            userService.saveUserCountry(message.getFrom().getId(), country);
            responseMessage = String.format("Saved %s to your favorites!👌", country.getEn());
        } catch (CountryNotFoundException exp) {
            log.error(exp.getMessage());
            responseMessage = String.format("Error in saving country %s", restOfTextMessage);
        }
        return SendMessage.builder()
                .chatId(String.valueOf(message.getChatId()))
                .text(responseMessage)
                .build();
    }
}
