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

import static com.lookfor.iwannatravel.utils.TextMessageUtil.getRestOfTextMessageWithoutCommand;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCountryCommandHandler implements RootCommandHandler<SendMessage> {
    private final UserService userService;

    @Override
    public SendMessage doParse(Update update) {
        Message message = getReceivedMessage(update);
        String restOfTextMessage = getRestOfTextMessageWithoutCommand(message.getText());
        String responseMessage;
        try {
            userService.saveUserDepartureCountry(message.getFrom().getId(), restOfTextMessage);
            responseMessage =
                    """
                            Your country was successfully saved!😎
                            Use command /to <Country> to set the country you want to travel🏖️
                            """;
        } catch (CountryNotFoundException | UserNotFoundException exp) {
            log.error(exp.getMessage());
            responseMessage = exp.getMessage();
        }
        return SendMessage.builder()
                .chatId(String.valueOf(message.getChatId()))
                .text(responseMessage)
                .build();
    }
}
