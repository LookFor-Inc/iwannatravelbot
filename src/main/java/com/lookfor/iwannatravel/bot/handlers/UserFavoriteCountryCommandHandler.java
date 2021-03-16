package com.lookfor.iwannatravel.bot.handlers;

import com.lookfor.iwannatravel.exceptions.CountryNotFoundException;
import com.lookfor.iwannatravel.exceptions.IncorrectRequestException;
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
public class UserFavoriteCountryCommandHandler implements RootCommandHandler<SendMessage> {
    private final UserService userService;

    @Override
    public SendMessage doParse(Update update) {
        Message message = getReceivedMessage(update);
        String restOfTextMessage = getRestOfTextMessageWithoutCommand(message.getText());
        String responseMessage;
        try {
            userService.saveUserArrivalCountry(message.getFrom().getId(), restOfTextMessage);
            responseMessage = String.format(
                    "Country %s was saved to your favorites!👌",
                    restOfTextMessage.substring(0, 1).toUpperCase() + restOfTextMessage.substring(1)
            );
        } catch (CountryNotFoundException | UserNotFoundException | IncorrectRequestException exp) {
            log.error(exp.getMessage());
            responseMessage = String.format(
                    "Error in saving country %s\n%s",
                    restOfTextMessage, exp.getMessage()
            );
        }
        return SendMessage.builder()
                .chatId(String.valueOf(message.getChatId()))
                .text(responseMessage)
                .build();
    }
}
